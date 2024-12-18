package MainStudScreen;

import ClassRoom.ChatMsg;
import Threads.SendMicSoundThread;
import Threads.SendScreenThread;
import User.User;
import User.Student;
import User.Professor;
import User.Roles;
import Utils.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static User.User.roleToString;

public class MainStudScreenGUI extends JFrame {
    private final CommunicationCallbacks communicationCallbacks;
    private SendMicSoundThread sendMicSoundThread;
    private SendScreenThread sendScreenThread;

    private JButton micBtn;
    private JButton soundBtn;
    private JButton chatBtn;
    private JPanel screenPanel;
    private JButton exitBtn;
    private JPanel chatPanel;
    private JTextField chatTextField;
    private JScrollPane chatScroller; // 채팅방의 스크롤 Panel
    private JPanel chatCommunityPanel;// 해당부분의 setVisibility를 사용하여 숨김 보이기 관리

    private boolean is_mic_on = false;
    private boolean is_sound_on = true;
    private boolean is_chat_on = true;

    // 임시 user data
    Student LoginStudent = new Student("1", "김재호", new ImageIcon("./assets/icons/user_icon.png"), Roles.TEAM_LEADER);


    // 구현 예정 함수들
    // 메시지 송신
    private void sendMsg(ChatMsg chatMsg){
        this.communicationCallbacks.send(chatMsg);
    }

    // 메시지 수신 (Thread)
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

    // 화면 소리, 교수님, 다른 학생들 마이크 소리 수신 (Thread)
    public void receiveSound(ChatMsg chatMsg){
        try {
            if (!is_sound_on){ // 마이크가 꺼져 있는 경우 소리 수신 X
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
//                System.out.println("수신 읽은 데이터 : " + soundData.length);
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

    // 현재 로그인 된 학생의 마이크 소리 송신 (Thread)
    private void sendMicVoice(ChatMsg chatMsg){
        this.communicationCallbacks.send(chatMsg);
    }

    // Team 활동 시작 유무 수신 (Thread) 팀원들 정보 전달 받음
    public void receiveTeamActivityStatus(){ }


    // User 정보 필요
    public MainStudScreenGUI(CommunicationCallbacks communicationCallbacks, Student user){
        setTitle("Student Class Main");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);

        JPanel padding = new JPanel(new BorderLayout());
        padding.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        setContentPane(padding);

        getContentPane().setBackground(Theme.Ultramarine);

        this.communicationCallbacks = communicationCallbacks;
        this.LoginStudent = user;

        buildGUI();

        setVisible(true);
    }

    private void buildGUI(){
        this.add(BorderLayout.NORTH, createExitBtn());
        this.add(BorderLayout.SOUTH, createCtrlPanel());
        this.add(BorderLayout.CENTER, createScreenPanel());
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


        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExitModalForUser.showModalDialog(MainStudScreenGUI.this);
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
        this.micBtn = new JButton("",new ImageIcon("./assets/icons/mic_off.png"));
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
                    micBtn.setIcon(new ImageIcon("./assets/icons/mic_off.png"));

                    Icon micIcon = micBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);

                    sendMicSoundThread.stopThread();
                }
                else{ // 마이크가 꺼져있을 때 눌렀을 경우 (Mic ON)
                    is_mic_on = true;
                    micBtn.setIcon(new ImageIcon("./assets/icons/mic_on.png"));

                    Icon micIcon = micBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);

                    sendMicSoundThread = new SendMicSoundThread(LoginStudent,(chatMsg)->sendMicVoice(chatMsg));
                    sendMicSoundThread.start();
                }
            }
        });

        return micBtn;
    }

    private JButton createSoundBtn(){
        this.soundBtn = new JButton("",new ImageIcon("./assets/icons/sound_on.png"));
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
                    soundBtn.setIcon(new ImageIcon("./assets/icons/sound_off.png"));

                    Icon micIcon = soundBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                }
                else{ // 소리 버튼이 꺼져있을 때 눌렀을 경우 (Sound ON)
                    is_sound_on = true;
                    soundBtn.setIcon(new ImageIcon("./assets/icons/sound_on.png"));

                    Icon micIcon = soundBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                }
            }
        });

        return soundBtn;
    }

    private JButton createChatBtn(){
        this.chatBtn = new JButton("",new ImageIcon("./assets/icons/chat_on.png"));
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
                    chatBtn.setIcon(new ImageIcon("./assets/icons/chat_off.png"));

                    Icon micIcon = chatBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                    chatPanel.setVisible(false);
                }
                else{ // 채팅화면이 꺼져 있는 경우
                    is_chat_on = true;
                    chatBtn.setIcon(new ImageIcon("./assets/icons/chat_on.png"));

                    Icon micIcon = chatBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                    chatPanel.setVisible(true);
                }
            }
        });

        return chatBtn;
    }

    // 사용자의 프로필 화면, 채팅방을 가지는 Panel 설계
    private JPanel createScreenPanel(){
        // 팀 활동을 시작한 이후에는 하단에 팀원들의 정보를 표시하기 위해 borderLayout으로 설정,
        // 더 나아가 화면 필기를 할때는 Center에 그림판을 위치시키기만 하면됨(?)
        this.screenPanel = new JPanel(new BorderLayout());

        // 현재 로그인한 사용자의 Profile 표시
        JPanel profilePanelMargin = new JPanel(new GridBagLayout());
        profilePanelMargin.setBackground(new Color(39, 81, 171));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        profilePanelMargin.add(createUserProfilePanel(), gbc);

        screenPanel.add(BorderLayout.CENTER, profilePanelMargin);

        //채팅방 추가
        screenPanel.add(BorderLayout.EAST, createChatRoomPanel());

        return screenPanel;
    }

    private JPanel createUserProfilePanel(){
        JPanel profilePanel = new JPanel(new GridLayout(2,1,0,0));
        profilePanel.setBackground(new Color(27, 116, 231));

        // User Profile 이미지
        JLabel userImageLabel = new JLabel();
        ImageIcon icon = LoginStudent.getProfileImage();
        Image img = icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH); // 원하는 크기로 조정
        userImageLabel.setHorizontalAlignment(JLabel.CENTER); // 가로 중앙 정렬
        userImageLabel.setVerticalAlignment(JLabel.CENTER); // 세로 중앙 정렬
        userImageLabel.setIcon(new ImageIcon(img));
        userImageLabel.setOpaque(true);
        userImageLabel.setBackground(new Color(27, 116, 231));
        userImageLabel.setBorder(BorderFactory.createEmptyBorder(20,50,20,50)); // 프로필 이미지 주변에 Padding 추가

        profilePanel.add(userImageLabel);

        //User 이름
        JLabel userName = new JLabel(LoginStudent.getName());
        userName.setHorizontalAlignment(JLabel.CENTER); // 가로 중앙 정렬
        userName.setVerticalAlignment(JLabel.CENTER); // 세로 중앙 정렬
        userName.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        userName.setOpaque(true);
        userName.setBackground(new Color(27, 116, 231));
        profilePanel.add(userName);

        // user 이름, Profile 이미지, 팀원 프로필들을 감싸는 Panel
        JPanel userAndTeamPanel = new JPanel(new BorderLayout());
        userAndTeamPanel.setBackground(new Color(39, 81, 171));

        RoundedPane profileImageNameRounded = new RoundedPane();
        profileImageNameRounded.setBackgroundColor(Theme.Darkblue);
        profileImageNameRounded.setContentPane(profilePanel);

        userAndTeamPanel.add(profileImageNameRounded, BorderLayout.CENTER);

        JPanel tmpEast = new JPanel();
        tmpEast.setBorder(BorderFactory.createEmptyBorder(50,30,50,30));
        tmpEast.setBackground(new Color(39, 81, 171));

        JPanel tmpWest = new JPanel();
        tmpWest.setBorder(BorderFactory.createEmptyBorder(50,30,50,30));
        tmpWest.setBackground(new Color(39, 81, 171));
//        tmpWest.setBackground(Color.red);

        userAndTeamPanel.add(tmpEast, BorderLayout.EAST);
        userAndTeamPanel.add(tmpWest, BorderLayout.WEST);

        return userAndTeamPanel;
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

                ChatMsg sendChatMsg = new ChatMsg(LoginStudent.getId() ,LoginStudent.getName(), LoginStudent.getProfileImage() ,roleToString(LoginStudent.getRole()) ,ChatMsg.MODE_USERINFO_MSG, msg);

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

                ChatMsg sendChatMsg = new ChatMsg(LoginStudent.getId() ,LoginStudent.getName(), LoginStudent.getProfileImage() ,roleToString(LoginStudent.getRole()) ,ChatMsg.MODE_USERINFO_MSG, msg);

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

        return chatPanel;
    }

    private JPanel addMessage(String msg, User user) {
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

        if (user instanceof Student && ((Student) user).getId().equals(LoginStudent.getId())) { // 수신자의 정보 (우측 정렬)

            // 우측 정렬
            msgGroupPanel.add(Box.createHorizontalGlue());
            msgGroupPanel.add(msgText);
            msgGroupPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Profile Image와 택스트 사이의 공백
            msgGroupPanel.add(msgProfile);

//            msgGroupPanel.setBackground(Color.blue);
        } else { // 송신자의 정보 (좌측 정렬)

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


    public static void main(String[] args) {
        CommunicationCallbacks test = new CommunicationCallbacks() {
            @Override
            public void send(ChatMsg chatMsg) {
                System.out.println("Apple");
            }
        };
        new MainStudScreenGUI(test, new Student(Icons.userIcon));
    }
}

