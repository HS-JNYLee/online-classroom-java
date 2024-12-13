package ClassRoom;

import User.User;
import Utils.RoundedPane;
import Utils.RoundedShadowPane;
import Utils.Theme;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.*;
import java.net.*;
import java.util.Map;

public class WithTalk extends JFrame {
    private JTextField t_id, t_input_name;
    private JTextField t_userID, t_hostAddr, t_portNum;
    private JTextPane t_display;
    private DefaultStyledDocument document;
    private JButton b_connect, b_disconnect, b_enter, b_exit, b_select;
    private JComboBox<String> cb;
    private String serverAddress;
    private int serverPort;

    private Socket socket;
    private ObjectOutputStream out;
    private Reader in;
    private BufferedOutputStream bos;

    private String uId;
    private String uName;
    private String uType;
    private Thread receiveThread = null;
    private String uFileName;
    private int frameHeight = 390;
    private int frameWidth = 510;

    WithTalk(String serverAddress, int serverPort) {
        super("WithTalk");
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        buildGUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameWidth, frameHeight);
        setVisible(true);

        try {
            connectToServer();
            sendUserID();
        } catch (UnknownHostException e1) {
            printDisplay("서버 주소와 포트번호를 확인하세요: " + e1.getMessage());
        } catch (IOException ex) {
            printDisplay("서버와의 연결 오류: " + ex.getMessage());
        }
    }

    public void buildGUI() {
        // 이미지 선택 버튼
        SelectImageButton selectImageButton = new SelectImageButton();
        selectImageButton.getButton().addActionListener(new ActionListener() {
            JFileChooser chooser = new JFileChooser();

            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & GIF & PNG Images",
                        "jpg", "gif", "png"
                );
                chooser.setFileFilter(filter);

                int ret = chooser.showOpenDialog(WithTalk.this);
                if(ret != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(WithTalk.this, "파일을 선택하지 않았습니다.");
                    return;
                }
                uFileName = chooser.getSelectedFile().getAbsolutePath();
                File file = new File(uFileName);
                if (!file.exists()) {
                    printDisplay(">> 파일이 존재하지 않습니다: " + uFileName);
                }
                ImageIcon icon = new ImageIcon(uFileName);
                Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                selectImageButton.setImage(scaledImage);
                selectImageButton.getButton().repaint();
            }
        });

        // 이미지 선택 버튼 wrapper 위치 조정 및 배경 설정
        JPanel imageButtonWrapper = new JPanel();
        imageButtonWrapper.setLayout(new GridBagLayout());
        imageButtonWrapper.setBackground(Theme.Ultramarine);
        imageButtonWrapper.add(selectImageButton.getButton()); // 이미지 선택 버튼 추가
        // ---------- 이미지 선택 버튼 끝

        // 둥근 그림진 사각형 패널 생성
        RoundedShadowPane roundedShadowPane = new RoundedShadowPane();

        // 입력 패널 모음 wrapper
        JPanel inputPanelWrapper = new JPanel();
        inputPanelWrapper.setBackground(Theme.Grey);
        inputPanelWrapper.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        inputPanelWrapper.add(createInputPanel(), BorderLayout.CENTER); // 입력 패널 모음 추가
        
        roundedShadowPane.setContentPane(inputPanelWrapper); // 둥근 사각형 팬에 input wrapper 추가
        // ---------- 입력 패널 wrapper 끝

        // 전체 패널 [이미지 선택 버튼+입력 패널 모음]
        JPanel contentPanel = new JPanel(new BorderLayout());

        contentPanel.setBackground(Theme.Ultramarine);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        contentPanel.add(imageButtonWrapper, BorderLayout.CENTER); // 이미지 버튼 배치 (중앙)
        contentPanel.add(roundedShadowPane, BorderLayout.SOUTH); // 둥근 사각형 팬 배치 (남쪽)
        // ---------- 전체 패널 끝

        add(contentPanel, BorderLayout.CENTER); // 전체 패널 프레임에 추가
    }

    public Font setUnderline(Font font) {
        // https://stackoverflow.com/questions/15892844/underlined-jlabel
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        return font.deriveFont(attributes);
    }

    public void initLabelStyle(JLabel label) {
        label.setForeground(Theme.Blue);
        label.setFont(setUnderline(label.getFont()));
    }

    public JPanel createInputWrapperPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(Theme.Grey);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
        return inputPanel;
    }

    public void addInputPanelContent(JPanel panel, Component componentText, Component componentInput) {
        panel.add(componentText, BorderLayout.WEST);
        panel.add(componentInput, BorderLayout.CENTER);
    }

    // 입력 패널
    public JPanel createInputPanel() {
        // 구분(선택) JLabel+ComboBox
        JLabel l_type = new JLabel("구분");
        initLabelStyle(l_type);
        String[] types = { "학생", "교수"};
        cb = new JComboBox<String>(types);
        cb.setAlignmentX(Component.CENTER_ALIGNMENT);
        cb.setSize(100, l_type.getPreferredSize().height);

        JPanel typePanel = createInputWrapperPanel();
        addInputPanelContent(typePanel, l_type, cb);
        // ---------- 구분(선택) 끝

        // 학번/교번(입력) JLabel+JTextField
        JLabel l_id = new JLabel("학번/교번");
        initLabelStyle(l_id);
        t_id = new JTextField(15);

        JPanel idPanel = createInputWrapperPanel();
        addInputPanelContent(idPanel, l_id, t_id);
        // ---------- 학번/교번(입력) 끝

        // 이름(입력) JLabel+JTextField
        JLabel l_name = new JLabel("이름");
        initLabelStyle(l_name);
        t_input_name = new JTextField(15);

        JPanel namePanel = createInputWrapperPanel();
        addInputPanelContent(namePanel, l_name, t_input_name);
        // ---------- 이름(입력) 끝

        // 입장하기(클릭) JButton
        b_enter = new JButton("입장하기");
        b_enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // sendMessage();
                sendUserLogin();
            }
        });
        b_enter.setBackground(Theme.Red);
        b_enter.setForeground(Theme.White);
        b_enter.setBorderPainted(false);
        RoundedPane roundedShadowPane = new RoundedPane();
        roundedShadowPane.setContentPane(b_enter);
        roundedShadowPane.setBackgroundColor(Theme.Grey);
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(roundedShadowPane, BorderLayout.CENTER);
        // ---------- 입장하기(클릭) 끝

        // 입력 패널 배치
        JPanel panel = new JPanel(new GridLayout(4, 0));
        panel.setBackground(Theme.Ultramarine);
        panel.add(typePanel);   // 구분
        panel.add(idPanel);     // 학번/교번
        panel.add(namePanel);   // 이름
        panel.add(buttonPanel); // 입장하기
        // ---------- 입력 패널 배치 끝

        return panel;
    }

    public void printDisplay(String msg) {
        int len = t_display.getDocument().getLength();
        try {
            document.insertString(len, msg + "\n", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        t_display.setCaretPosition(len);
    }

    public void printDisplay(ImageIcon icon) {
        t_display.setCaretPosition(t_display.getDocument().getLength());
        if(icon.getIconWidth() > 400) {
            Image img = icon.getImage();
            Image chanegImg = img.getScaledInstance(400, -1, Image.SCALE_SMOOTH);
            icon = new ImageIcon(chanegImg);
        }
        t_display.insertIcon(icon);
        printDisplay("");
        t_id.setText("");
    }

    private String getLocalAddr() {
        InetAddress local = null;
        String addr = "";
        try {
            local = InetAddress.getLocalHost();
            addr = local.getHostAddress();
            System.out.println(addr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return addr;
    }

    // 서버와 접속하는 함수
    public void connectToServer() throws IOException {
        try {
            socket = new Socket();
            SocketAddress sa = new InetSocketAddress(serverAddress, serverPort);
            socket.connect(sa, 3000);

            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            receiveThread = new Thread(new Runnable() {
                private ObjectInputStream in;
                private void receiveMessage() {
                    try {
                        ChatMsg inMsg = (ChatMsg) in.readObject();
                        if (inMsg == null) {
                            disconnect();
                            printDisplay("서버 연결 끊김");
                            return;
                        }

                        switch (inMsg.mode) {
                            case ChatMsg.MODE_TX_STRING :
                                printDisplay(inMsg.userID + ": " + inMsg.message);
                                break;
                            case ChatMsg.MODE_TX_IMAGE:
                                printDisplay(inMsg.userID + ": " + inMsg.message);
                                printDisplay(inMsg.image);
                                break;
                            case ChatMsg.MODE_TX_ACCESS:
                                printDisplay(inMsg.userID + ": " + inMsg.message);
                                dispose();
                                new MainScreenGUI();
                                break;
                            case ChatMsg.MODE_TX_DENIED:
                                printDisplay(inMsg.userID + ": " + inMsg.message);
                                break;
                        }
                    } catch (IOException ex) {
                        System.err.println("연결을 종료했습니다.");
                    } catch (ClassNotFoundException ex) {
                        printDisplay("잘못된 객체가 전달되었습니다.");
                    }
                }
                @Override
                public void run() {
                    try {
                        in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                    } catch (IOException ex) {
                        printDisplay("입력 스트림이 열리지 않음");
                    }
                    while (receiveThread == Thread.currentThread()) {
                        receiveMessage();
                    }
                }
            });
            receiveThread.start();
        } catch (UnknownHostException ex) {
            System.err.println("알 수 없는 서버 > "+ex.getMessage());
        } catch (IOException ex) {
            System.err.println("클라이언트 연결 오류 > " + ex.getMessage());
        }
    }

    // 서버와 접속을 종료하는 함수
    public void disconnect() {
        /* 소켓 닫기 */
        send(new ChatMsg(uId, ChatMsg.MODE_LOGOUT));
        try {
            receiveThread = null;
            socket.close();
            b_connect.setEnabled(true); // 연결하기 활성화
            b_disconnect.setEnabled(false); // 연결 끊기 비활성화
            b_exit.setEnabled(true); // 종료하기 활성화
        } catch (IOException ex) {
            System.err.println("클라이언트 닫기 오류> "+ex.getMessage());
            System.exit(-1);
        }
    }

    private void send(ChatMsg msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            System.err.println("클라이언트 일반 전송 오류> " + e);
        }
    }

    private void sendUserID() {
        uId = "guest" + getLocalAddr().split("\\.")[3];

        send(new ChatMsg(uId, ChatMsg.MODE_CONNECT));

        t_id.setText("");
    }

    private void sendUserLogin() {
        uId = t_id.getText();
        uName = t_input_name.getText();
        uType = cb.getSelectedItem().toString();
        User user = new User();
        user.setId(uId); // 학번/교번
        user.setName(uName); // 이름

        // 프로필 사진
        File file = new File(uFileName);
        if (!file.exists()) {
            printDisplay(">> 파일이 존재하지 않습니다: " + uFileName);
            return;
        }
        ImageIcon icon = new ImageIcon(uFileName);

        send(new ChatMsg(uId, ChatMsg.MODE_LOGIN, file.getName(), icon, uId, uName, uType));
    }

    public static void main(String[] args) {
        new WithTalk("127.0.0.1", 8080);
    }
}
