package ClassRoom;
import ClassRoom.Group.MultiGroup;
import MainStudScreen.CommunicationCallbacks;
import Threads.SendMicSoundThread;
import Threads.SendScreenThread;
import User.*;
import Utils.Icons;
import Utils.RoundedPane;
import Utils.RoundedShadowPane;
import Utils.Theme;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static User.User.roleToString;

public class MainProfScreenGUI extends JFrame {
    private CommunicationCallbacks communicationCallbacks;
    private SendMicSoundThread sendMicSoundThread;
    private SendScreenThread sendScreenThread;

    private JButton exitBtn;
    private JButton micBtn;
    private JButton soundBtn;
    private JButton screenShareBtn;
    private JButton chatBtn;
    private JTable studentJTable;
    private JScrollPane studentTableJScrollPanel;

    private JTextField chatTextField;
    private JScrollPane chatScroller;

    private JPanel chatPanel;
    private JPanel variousScreenPanel;
    private JPanel chatCommunityPanel;
    private JPanel chatroomPanelPadding;
    private JPanel joinedStudentPanel;

    private boolean is_mic_on = false;
    private boolean is_sound_on = true;
    private boolean is_screen_share_possible = true;
    private boolean is_chat_on = true;

    private Professor LoginProf;

    private void sendMicVoice(ChatMsg chatMsg) { this.communicationCallbacks.send(chatMsg); }

    // 화면 소리, 교수님, 다른 학생들 마이크 소리 수신 (Thread)
    public void receiveSound(ChatMsg chatMsg){
        try {
            System.out.println("소리옴~~~~~~~~~~~~~~~~~~~~~~~");
            if (!is_sound_on){ // 마이크가 꺼져 있는 경우 소리 수신 X
                System.out.println("소리옴~~~~~~~~~~~~~~~~~~~그러나 수신 X");
                return;
            }
            // 1. 오디오 포맷 설정 (서버와 동일하게 설정)
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            SourceDataLine line = AudioSystem.getSourceDataLine(format);
            line.open(format);
            line.start(); // 오디오 출력을 위한 라인 시작

            // 2. ChatMsg에서 바이트 배열 가져오기
            byte[] soundData = chatMsg.getMicSound(); // ChatMsg에 추가된 getter 메서드로 데이터 접근

            // 3. 가져온 데이터를 SourceDataLine에 전달
            if (soundData != null && soundData.length > 0) {
                System.out.println("수신 읽은 데이터 : " + soundData.length);
                line.write(soundData, 0, soundData.length); // 오디오 출력
            }

            line.drain(); // 버퍼에 남은 데이터 재생 완료
            line.close(); // 라인 닫기

        } catch (LineUnavailableException e) {
            System.err.println("오디오 장치를 사용할 수 없습니다.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendScreenStartEvent(ChatMsg chatMsg){ this.communicationCallbacks.send(chatMsg); }

    private void sendScreenEndEvent(ChatMsg chatMsg) { this.communicationCallbacks.send(chatMsg); }

    private void sendScreen(ChatMsg chatMsg){
        System.out.println("화면 보냄");
        this.communicationCallbacks.send(chatMsg);
    }

    private void sendMsg(ChatMsg chatMsg){ this.communicationCallbacks.send(chatMsg); }

    public void receiveMsg(ChatMsg receivedChatMsg){
        String roleString = receivedChatMsg.getuType();
        String id = receivedChatMsg.getuId();
        String name = receivedChatMsg.getuName();
        ImageIcon imageIcon = receivedChatMsg.getImage();

        User receivedUser;

        System.out.println(receivedChatMsg.getuId());

        if(roleString.equals("교수")){
            receivedUser = new Professor(id, name, imageIcon);
        } else {
            receivedUser = new Student(id,name, imageIcon);
        }

        addMessage(receivedChatMsg.getMessage(), receivedUser);
    }

    public MainProfScreenGUI(CommunicationCallbacks communicationCallbacks, Professor user){
        setTitle("Class Student Main");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 700);
        setLocationRelativeTo(null);

        JPanel padding = new JPanel(new BorderLayout());
        padding.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        setContentPane(padding);

        getContentPane().setBackground(Theme.Ultramarine);

        buildGUI();

        this.communicationCallbacks = communicationCallbacks;
        LoginProf = user;
        LoginProf.setRole(Roles.PROFESSOR);
        System.out.println("교수화면 : " + LoginProf.getId() + LoginProf.getName());

        setVisible(true);
    }

    private void buildGUI(){
        this.add(BorderLayout.NORTH, createExitBtn());
        this.add(BorderLayout.SOUTH, createCtrlPanel());
        this.add(BorderLayout.CENTER, createVariousScreenPanel());
    }

    private JPanel createExitBtn(){
        JPanel ret = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ret.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        ret.setOpaque(false);

        this.exitBtn = new JButton("나가기");
        exitBtn.setBackground(new Color(250, 91, 87));
        exitBtn.setPreferredSize(new Dimension(100, 30));
        exitBtn.setForeground(Color.white);
        exitBtn.setOpaque(false);
        exitBtn.setBorderPainted(false);


        //TODO 나가기 설계 Login? main?
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        RoundedShadowPane btnShadow = new RoundedShadowPane();
        btnShadow.setContentPane(exitBtn);

        ret.add(btnShadow);

        return ret;
    }

    private JPanel createCtrlPanel(){
        JPanel btnsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80,5));
        btnsPanel.setBackground(new Color(203, 203, 206));

        // 안에 포함된 컴포넌트 구성
        btnsPanel.add(createMicBtn()); // 마이크 버튼
        btnsPanel.add(createSoundBtn()); // 소리 버튼
        btnsPanel.add(createScreenShareButton());// 화면 공유 버튼
        btnsPanel.add(createChatBtn()); // 채팅 버튼


        // CntrlPanel(현재)의 스타일 지정 (padding, margin, radius...etc)
        //Padding
        JPanel ctrlPanelPadding = new JPanel();
        ctrlPanelPadding.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));
        ctrlPanelPadding.add(btnsPanel);
        ctrlPanelPadding.setBackground(new Color(203, 203, 206));

        RoundedShadowPane ctrlRounded = new RoundedShadowPane();
        ctrlRounded.setContentPane(ctrlPanelPadding);

        //Margin
        JPanel ctrlPanelMargin = new JPanel();
        ctrlPanelMargin.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        ctrlPanelMargin.setOpaque(false);
//        ctrlPanelMargin.setBackground(Color.red);

        ctrlPanelMargin.add(ctrlRounded);

        return ctrlPanelMargin;
    }

    private JButton createMicBtn(){
        this.micBtn = new JButton("", Icons.micOffIcon);
        Icon micIcon = this.micBtn.getIcon();

        Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
        ((ImageIcon) micIcon).setImage(resize);

        micBtn.setBackground(new Color(0, 0, 0, 0));
        micBtn.setOpaque(false);
        micBtn.setBorderPainted(false);

        micBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(is_mic_on){ // 마이크가 켜져있을 때 눌렀을 경우 (Mic OFF)
                    is_mic_on = false;
                    micBtn.setIcon(Icons.micOffIcon);

                    Icon micIcon = micBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);

                    sendMicSoundThread.stopThread();
                }
                else{ // 마이크가 꺼져있을 때 눌렀을 경우 (Mic ON)
                    is_mic_on = true;
                    micBtn.setIcon(Icons.micOnIcon);

                    Icon micIcon = micBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);

                    sendMicSoundThread = new SendMicSoundThread(LoginProf,(chatMsg)->sendMicVoice(chatMsg));
                    sendMicSoundThread.start();
                }
            }
        });

        return micBtn;
    }

    private JButton createSoundBtn(){
        this.soundBtn = new JButton("",Icons.soundOnIcon);
        Icon micIcon = this.soundBtn.getIcon();

        Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
        ((ImageIcon) micIcon).setImage(resize);

        soundBtn.setBackground(new Color(0, 0, 0, 0));
        soundBtn.setOpaque(false);
        soundBtn.setBorderPainted(false);

        soundBtn.setPreferredSize(new Dimension(resize.getWidth(null), resize.getHeight(null)));

        soundBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(is_sound_on){ // 소리 버튼이 켜져있을 때 눌렀을 경우 (Sound OFF)
                    is_sound_on = false;
                    soundBtn.setIcon(Icons.soundOffIcon);

                    Icon soundBtnIcon = soundBtn.getIcon();

                    Image resize = ((ImageIcon) soundBtnIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) soundBtnIcon).setImage(resize);
                }
                else{ // 소리 버튼이 꺼져있을 때 눌렀을 경우 (Sound ON)
                    is_sound_on = true;
                    soundBtn.setIcon(Icons.soundOnIcon);

                    Icon soundBtnIcon = soundBtn.getIcon();

                    Image resize = ((ImageIcon) soundBtnIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) soundBtnIcon).setImage(resize);
                }
            }
        });

        return soundBtn;
    }


    private JButton createScreenShareButton(){
        this.screenShareBtn = new JButton("",Icons.screenShareOnIcon);
        Icon micIcon = this.screenShareBtn.getIcon();

        Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
        ((ImageIcon) micIcon).setImage(resize);

        screenShareBtn.setBackground(new Color(0, 0, 0, 0));
        screenShareBtn.setOpaque(false);
        screenShareBtn.setBorderPainted(false);

        screenShareBtn.setPreferredSize(new Dimension(resize.getWidth(null), resize.getHeight(null)));

        screenShareBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(is_screen_share_possible){ // 화면 공유 시작 가능 (Screen Share ON)
                    is_screen_share_possible = false;
                    screenShareBtn.setIcon(Icons.screenShareOffIcon);

                    Icon screenShareBtnIcon = screenShareBtn.getIcon();

                    Image resize = ((ImageIcon) screenShareBtnIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) screenShareBtnIcon).setImage(resize);

                    // 화면 공유 시작했음을 알림
                    ChatMsg screenShareStart = new ChatMsg(LoginProf.getId(), User.roleToString(Roles.PROFESSOR), ChatMsg.MODE_SCREEN_SHARE_START);
                    sendScreenStartEvent(screenShareStart);

                    sendScreenThread = new SendScreenThread(LoginProf,(chatMsg)->sendScreen(chatMsg));
                    sendScreenThread.start();
                }
                else{ // 화면 공유중인 경우 (Screen Share OFF)
                    is_screen_share_possible = true;
                    screenShareBtn.setIcon(Icons.screenShareOnIcon);

                    Icon screenShareBtnIcon = screenShareBtn.getIcon();

                    Image resize = ((ImageIcon) screenShareBtnIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) screenShareBtnIcon).setImage(resize);

                    sendScreenThread.stopThread();

                    ChatMsg screenShareEnded = new ChatMsg(LoginProf.getId(), User.roleToString(Roles.PROFESSOR), ChatMsg.MODE_SCREEN_SHARE_END);
                    sendScreenEndEvent(screenShareEnded);
                }
            }
        });

        return screenShareBtn;
    }

    private JButton createChatBtn(){
        this.chatBtn = new JButton("",Icons.chatOnIcon);
        Icon micIcon = this.chatBtn.getIcon();

        Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
        ((ImageIcon) micIcon).setImage(resize);

        chatBtn.setBackground(new Color(0, 0, 0, 0));
        chatBtn.setOpaque(false);
        chatBtn.setBorderPainted(false);

        chatBtn.setPreferredSize(new Dimension(resize.getWidth(null), resize.getHeight(null)));

        chatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(is_chat_on){ // 채팅화면이 켜져있는 경우
                    is_chat_on = false;
                    chatBtn.setIcon(Icons.chatOffIcon);

                    Icon chatBtnIcon = chatBtn.getIcon();

                    Image resize = ((ImageIcon) chatBtnIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) chatBtnIcon).setImage(resize);
                    chatroomPanelPadding.setVisible(false);
                }
                else{ // 채팅화면이 꺼져 있는 경우
                    is_chat_on = true;
                    chatBtn.setIcon(Icons.chatOnIcon);

                    Icon chatBtnIcon = chatBtn.getIcon();

                    Image resize = ((ImageIcon) chatBtnIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) chatBtnIcon).setImage(resize);
                    chatroomPanelPadding.setVisible(true);
                }
            }
        });

        return chatBtn;
    }

    private JPanel createVariousScreenPanel(){

        this.variousScreenPanel = new JPanel(new FlowLayout());
        variousScreenPanel.setBackground(Theme.Blue); // 겉 색상

        JPanel wrapper = new JPanel(new FlowLayout());
        wrapper.setBackground(Theme.Blue); // 감싸는 색상
        wrapper.add(createJoinedStudentPanel()); // 접속 학생 Panel
        wrapper.add(createStudentTablePanel()); // 학생 JTable Panel
        wrapper.add(createChatRoomPanel());


        variousScreenPanel.add(wrapper);

        RoundedPane tmp = new RoundedPane();
        tmp.setContentPane(variousScreenPanel);

        return tmp;

    }

    private MultiGroup multiGroup;
    private JPanel createJoinedStudentPanel(){
        this.joinedStudentPanel = new JPanel();
        joinedStudentPanel.setBackground(Theme.Ultramarine);
        joinedStudentPanel.setPreferredSize(new Dimension(384,384));

        multiGroup = new MultiGroup();

        JPanel padding = new JPanel(new GridLayout());
        padding.setPreferredSize(new Dimension(384,384));
        padding.setBackground(Theme.Ultramarine);
        padding.add(joinedStudentPanel);

        RoundedPane tmp = new RoundedPane();
        tmp.setContentPane(joinedStudentPanel);
        tmp.setBackgroundColor(Theme.Blue);

        return tmp;
    }
    static DefaultTableModel model;
    private JPanel createStudentTablePanel(){

        String[] col = new String[]{"이름", "학번","모둠번호","역할"};

        Object[][] data = {
                {"김재호", 202312345, 1, "팀장", 0},
                {"이수민", 202312346, 1, "팀원", 2},
                {"박지훈", 202312347, 2, "팀원", 4},
                {"정하은", 202312348, 2, "팀장", 5},
                {"정하은", 202312348, 2, "팀장", 6},
                {"정하은", 202312348, 2, "팀장", 8},
                {"정하은", 202312348, 2, "팀장", 10},
                {"정하은", 202312348, 2, "팀장", 12},
                {"정하은", 202312348, 2, "팀장", 14},
                {"정하은", 202312348, 2, "팀장", 15},
        };

        model = new DefaultTableModel(data, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // 모둠번호와 역할은 수정 가능
                if(column==2 || column==3) return true;
                else return false;
            }

            @Override
            public void addRow(Object[] rowData) {
                if(rowData[4] != null) {
                multiGroup.participate((Integer) rowData[4]);

                joinedStudentPanel.removeAll();
                joinedStudentPanel.add(multiGroup.buildGUI());
                joinedStudentPanel.revalidate();
                joinedStudentPanel.repaint();
                }
            }

            @Override
            public void setValueAt(Object value, int row, int column) {
                super.setValueAt(value, row, column); // 내부 데이터 업데이트

                System.out.println("Updated data: " + value + " at [" + row + ", " + column + "]");

                for(var i : data){
                    for(var j : i){
                        System.out.println(j);
                    }
                }
            }
        };

        this.studentJTable = new JTable(model);

        JTableHeader header = studentJTable.getTableHeader();
        header.setBackground(new Color(44, 55, 76));
        header.setForeground(Color.WHITE);

        this.studentTableJScrollPanel = new JScrollPane(studentJTable);

        JPanel padding = new JPanel(new GridLayout());
        padding.setPreferredSize(new Dimension(384,384));
        padding.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        padding.setBackground(Theme.Blue);
        padding.add(studentTableJScrollPanel);

        return padding;
    }

    private JPanel createChatRoomPanel(){
        this.chatPanel = new JPanel(new BorderLayout());
        chatPanel.setPreferredSize(new Dimension(300,500));

        // sendBtn과 sendTextField를 가짐
        JPanel messageSendControlPanel = new JPanel(new BorderLayout());

        // 전송 버튼
        JButton sendBtn = new JButton("보내기");
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = chatTextField.getText();
                chatTextField.setText("");

                // 스크롤 바를 자동으로 제일 밑으로 이동
                JScrollBar scrollBar = chatScroller.getVerticalScrollBar();
                scrollBar.setValue(scrollBar.getMaximum());

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JScrollBar scrollBar = chatScroller.getVerticalScrollBar();
                        scrollBar.setValue(scrollBar.getMaximum());
                    }
                });

                ChatMsg sendChatMsg = new ChatMsg(LoginProf.getId() ,LoginProf.getName(), LoginProf.getProfileImage() ,roleToString(LoginProf.getRole()) ,ChatMsg.MODE_USERINFO_MSG, msg);

                sendMsg(sendChatMsg); // 메시지 송신
            }
        });

        // 메시지 입력 TextField
        this.chatTextField = new JTextField();
        this.chatTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = chatTextField.getText();
                chatTextField.setText("");

                // 스크롤 바를 자동으로 제일 밑으로 이동
                JScrollBar scrollBar = chatScroller.getVerticalScrollBar();
                scrollBar.setValue(scrollBar.getMaximum());

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JScrollBar scrollBar = chatScroller.getVerticalScrollBar();
                        scrollBar.setValue(scrollBar.getMaximum());
                    }
                });

                ChatMsg sendChatMsg = new ChatMsg(LoginProf.getId() ,LoginProf.getName(), LoginProf.getProfileImage() ,roleToString(LoginProf.getRole()) ,ChatMsg.MODE_USERINFO_MSG, msg);

                sendMsg(sendChatMsg); // 메시지 송신
            }
        });

        messageSendControlPanel.add(chatTextField, BorderLayout.CENTER);
        messageSendControlPanel.add(sendBtn, BorderLayout.EAST);

        chatPanel.add(messageSendControlPanel, BorderLayout.SOUTH);

        // 송신된, 수신된 메시지가 그려지는 Panel
        this.chatCommunityPanel = new JPanel();
        chatCommunityPanel.setBackground(Color.white);
        chatCommunityPanel.setLayout(new BoxLayout(chatCommunityPanel, BoxLayout.Y_AXIS));

        this.chatScroller = new JScrollPane(chatCommunityPanel);

        chatPanel.add(chatScroller, BorderLayout.CENTER);

        chatroomPanelPadding = new JPanel(new GridLayout());
        chatroomPanelPadding.setPreferredSize(new Dimension(384,384));
        chatroomPanelPadding.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        chatroomPanelPadding.setBackground(Theme.Blue);
        chatroomPanelPadding.add(chatPanel);

        return chatroomPanelPadding;
    }

    private JPanel addMessage(String msg, User user){

        String id = user.getId();
        String name = user.getName();
        ImageIcon profileImage = user.getProfileImage();
        Image resize = profileImage.getImage().getScaledInstance(25,25, Image.SCALE_SMOOTH);
        ((ImageIcon) profileImage).setImage(resize);

        // 송신된 메시지는 우측에, 수신된 메시지는 좌측에
        JPanel msgGroupPanel = new JPanel();
        msgGroupPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        msgGroupPanel.setBackground(Color.white);
        msgGroupPanel.setLayout(new BoxLayout(msgGroupPanel, BoxLayout.X_AXIS));

        JLabel msgText = new JLabel(msg);
        JLabel msgProfile = new JLabel(profileImage);

        if (user instanceof Professor && ((Professor) user).getId().equals(LoginProf.getId())) {

            // 우측 정렬
            msgGroupPanel.add(Box.createHorizontalGlue());
            msgGroupPanel.add(msgText);
            msgGroupPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Profile Image와 택스트 사이의 공백
            msgGroupPanel.add(msgProfile);

//            msgGroupPanel.setBackground(Color.blue);
        } else {

            // 좌측 정렬
            msgGroupPanel.add(msgProfile);
            msgGroupPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Profile Image와 택스트 사이의 공백
            msgGroupPanel.add(msgText);
            msgGroupPanel.add(Box.createHorizontalGlue());

//            msgGroupPanel.setBackground(Color.red);
        }

        chatCommunityPanel.add(msgGroupPanel);

        // 부모 패널 업데이트
        chatCommunityPanel.revalidate();
        chatCommunityPanel.repaint();

        JScrollBar scrollBar = chatScroller.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JScrollBar scrollBar = chatScroller.getVerticalScrollBar();
                scrollBar.setValue(scrollBar.getMaximum());
            }
        });

        return msgGroupPanel;
    }

    public void attendanceStudent(Object[] student){
        model.addRow(student);
    }
    public static void main(String[] args) {
//        new MainProfScreenGUI();
    }


}
