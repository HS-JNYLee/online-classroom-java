package ClassRoom;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;

public class WithTalk extends JFrame {
    private JTextField t_input;
    private JTextField t_userID, t_hostAddr, t_portNum;
    private JTextPane t_display;
    private DefaultStyledDocument document;
    private JButton b_connect, b_disconnect, b_send, b_exit, b_select;

    private String serverAddress;
    private int serverPort;

    private Socket socket;
    private ObjectOutputStream out;
    private Reader in;
    private BufferedOutputStream bos;

    private String uid;

    private Thread receiveThread = null;

    private int frameHeight = 390;
    private int frameWidth = 510;
    private int inputPanelHeight = 30;

    WithTalk(String serverAddress, int serverPort) {
        super("WithTalk");
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        buildGUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameWidth, frameHeight);
        setVisible(true);
    }

    public void buildGUI() {
        add(createDisplayPanel(), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 0)); // 입력 & 제어 한 패널로 묶음
        bottomPanel.add(createInputPanel());
        bottomPanel.add(createInfoPanel());
        bottomPanel.add(createControlPanel());

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 텍스트 화면 패널
    public JPanel createDisplayPanel() {
        JPanel p = new JPanel(new BorderLayout());
        document = new DefaultStyledDocument();
        t_display = new JTextPane(document);
        t_display.setEditable(false); // 편집 불가

        p.add(new JScrollPane(t_display), BorderLayout.CENTER);

        return p;
    }

    // 입력 패널
    public JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        int t_input_width = 410;
        int b_connect_width = 90;

        t_input = new JTextField(30);
        t_input.setSize(t_input_width, inputPanelHeight);

        b_send = new JButton("보내기");
        b_send.setEnabled(false);
        // 엔터키로 문자 전송
        t_input.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        // [보내기] 버튼으로 문자 전송
        b_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        b_send.setSize(b_connect_width, inputPanelHeight);

        b_select = new JButton("선택하기");
        b_select.addActionListener(new ActionListener() {
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

                t_input.setText(chooser.getSelectedFile().getAbsolutePath());
                sendImage();
            }
        });

        // 배치 미리보기
        // [입력창] [보내기]
        inputPanel.add(t_input, BorderLayout.CENTER);
        JPanel p_button = new JPanel(new GridLayout(1, 0));
        p_button.add(b_select);
        p_button.add(b_send);
        inputPanel.add(p_button, BorderLayout.EAST);

        t_input.setEnabled(false);
        b_select.setEnabled(false);
        b_send.setEnabled(false);
        return inputPanel;
    }

    private JPanel createInfoPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        t_userID = new JTextField(7);
        t_hostAddr = new JTextField(12);
        t_portNum = new JTextField(5);

        t_userID.setText("guest" + getLocalAddr().split("\\.")[3]);
        t_hostAddr.setText(this.serverAddress);
        t_portNum.setText(String.valueOf(this.serverPort));

        t_portNum.setHorizontalAlignment(JTextField.CENTER);

        p.add(new JLabel("아이디: "));
        p.add(t_userID);

        p.add(new JLabel("서버주소: "));
        p.add(t_hostAddr);

        p.add(new JLabel("포트번호: "));
        p.add(t_portNum);

        return p;
    }

    // 동작 제어 패널
    public JPanel createControlPanel() {
        b_connect = new JButton("접속하기");
        b_connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverAddress = t_hostAddr.getText();
                serverPort = Integer.parseInt(t_portNum.getText());
                try {
                    connectToServer();
                    sendUserID();
                } catch (UnknownHostException e1) {
                    printDisplay("서버 주소와 포트번호를 확인하세요: " + e1.getMessage());
                    return;
                } catch (IOException ex) {
                    printDisplay("서버와의 연결 오류: " + ex.getMessage());
                    return;
                }
                b_connect.setEnabled(false); // 연결하기 비활성화
                b_disconnect.setEnabled(true); // 접속끊기 활성화

                t_input.setEnabled(true);
                b_send.setEnabled(true);
                b_select.setEnabled(true);
                b_exit.setEnabled(false); // 종료하기 비활성화

                t_userID.setEditable(false);
                t_hostAddr.setEditable(false);
                t_portNum.setEditable(false);
            }
        });

        b_disconnect = new JButton("접속 끊기");
        // [접속 끊기] 버튼으로 연결 해제
        b_disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnect();
            }
        });

        b_exit = new JButton("종료하기");
        b_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 3));
        // 배치 미리보기
        // [접속하기] [접속끊기] [종료하기]

        controlPanel.add(b_connect);
        controlPanel.add(b_disconnect);
        controlPanel.add(b_exit);

        b_connect.setEnabled(true); // 연결하기 활성화
        b_disconnect.setEnabled(false); // 접속끊기 비활성화
        b_exit.setEnabled(true); // 종료하기 활성화

        return controlPanel;
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
        t_input.setText("");
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
        send(new ChatMsg(uid, ChatMsg.MODE_LOGOUT));
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
            System.err.println("클라이언트 일반 전송 오류> " + e.getMessage());
        }
    }

    private void sendMessage() {
        String message = t_input.getText();
        if (message.isEmpty()) return;

        send(new ChatMsg(uid, ChatMsg.MODE_TX_STRING, message));
        t_input.setText("");
    }

    private void sendUserID() {
        uid = t_userID.getText();

        send(new ChatMsg(uid, ChatMsg.MODE_LOGIN));

        t_input.setText("");
    }

    private void sendImage() {
        String filename = t_input.getText().strip();
        if (filename.isEmpty()) return;
        File file = new File(filename);
        if (!file.exists()) {
            printDisplay(">> 파일이 존재하지 않습니다: " + filename);
            return;
        }

        ImageIcon icon = new ImageIcon(filename);
        send(new ChatMsg(uid, ChatMsg.MODE_TX_IMAGE, file.getName(), icon));
        t_input.setText("");
    }

    public static void main(String[] args) {
        new WithTalk("127.0.0.1", 8080);
    }
}
