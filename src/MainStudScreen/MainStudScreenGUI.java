package MainStudScreen;

import ClassRoom.MainScreenGUI;
import User.User;
import User.Student;
import User.Professor;
import User.Roles;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainStudScreenGUI extends JFrame {

    private JButton micBtn;
    private JButton soundBtn;
    private JButton screenShareBtn;
    private JButton chatBtn;
    private JPanel screenPanel;
    private JButton exitBtn;
    private JPanel chatPanel;
    private JTextField chatTextField;
    private JScrollPane chatScroller; // 채팅방의 스크롤 Panel
    private JPanel chatCommunityPanel;

    private boolean is_mic_on = false;
    private boolean is_sound_on = true;
    private boolean is_screen_share_possible = true;
    private boolean is_chat_on = true;

    // 교수가 팀 활동을 시작했을 경우 활성화
    private boolean teamActivityStatus = true;

    // 임시 user data
    Student LoginStudent = new Student("1", "김재호", new ImageIcon("./assets/icons/user_icon.png"), Roles.TEAM_LEADER);


    // 구현 예정 함수들
    // 메시지 송신
    public void sendMsg(){ }

    // 메시지 수신 (Thread)
    public void receiveMsg(){ }

    // 교수가 화면 공유를 시작했을 경우 화면 수신 (Thread)
    public void receiveScreen(){ }

    // 화면 소리, 교수님, 다른 학생들 마이크 소리 수신 (Thread)
    public void receiveSound(){ }

    // 현재 로그인 된 학생의 마이크 소리 송신 (Thread)
    public void sendMicVoice(){ }

    // Team 활동 시작 유무 수신 (Thread)
    public void receiveTeamActivityStatus(){ }

    // Team 활동을 시작했고 User가 팀장인 경우 화면 송신 가능 (Thread)
    public void sendScreen(){ }




    // User 정보 필요
    private MainStudScreenGUI(){
        setTitle("Class Student Main");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);

        JPanel padding = new JPanel(new BorderLayout());
        padding.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        setContentPane(padding);

        getContentPane().setBackground(new Color(43, 61, 81));

        buildGUI();

        setVisible(true);
    }

    private void buildGUI(){
        this.add(BorderLayout.NORTH, createExitBtn());
        this.add(BorderLayout.SOUTH, createCntlPanel());
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

        ret.add(exitBtn);

        //TODO 나가기 설계 Login? main?

        return ret;
    }

    private JPanel createCntlPanel(){
        JPanel btnsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50,30));

        // 안에 포함된 컴포넌트 구성
        btnsPanel.add(createMicBtn()); // 마이크 버튼
        btnsPanel.add(createSoundBtn()); // 소리 버튼
        if(teamActivityStatus && LoginStudent.getRole() == Roles.TEAM_LEADER) btnsPanel.add(createScreenShareButton()); // 팀장인 경우만 화면 공우 가능
        btnsPanel.add(createChatBtn()); // 채팅 버튼


        // CntrlPanel(현재)의 스타일 지정 (padding, margin, radius...etc)
        //Padding
        JPanel ctrlPanelPadding = new JPanel();
        ctrlPanelPadding.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));
        ctrlPanelPadding.add(btnsPanel);
        ctrlPanelPadding.setBackground(new Color(203, 203, 206));

        //Margin
        JPanel ctrlPanelMargin = new JPanel();
        ctrlPanelMargin.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        ctrlPanelMargin.setOpaque(false);
        btnsPanel.setBackground(new Color(203, 203, 206));

        ctrlPanelMargin.add(ctrlPanelPadding);

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

                    teamActivityStatus = true;


                }
                else{ // 마이크가 꺼져있을 때 눌렀을 경우 (Mic ON)
                    is_mic_on = true;
                    micBtn.setIcon(new ImageIcon("./assets/icons/mic_on.png"));

                    Icon micIcon = micBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);

                    teamActivityStatus = false;

                    //TODO sendMicVoice (Thread)
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

    private JButton createScreenShareButton(){
        this.screenShareBtn = new JButton("",new ImageIcon("./assets/icons/screen_share_on.png"));
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
                    screenShareBtn.setIcon(new ImageIcon("./assets/icons/screen_share_off.png"));

                    Icon micIcon = screenShareBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);


                }
                else{ // 화면 공유중인 경우 (Screen Share OFF)
                    is_screen_share_possible = true;
                    screenShareBtn.setIcon(new ImageIcon("./assets/icons/screen_share_on.png"));

                    Icon micIcon = screenShareBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);

                    //TODO sendScreen() (Thread)
                }
            }
        });

        return screenShareBtn;
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

        screenPanel.setBackground(new Color(39, 81, 171));

        // 현재 로그인한 사용자의 Profile 표시
        JPanel profilePanelMargin = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;

        profilePanelMargin.add(createUserProfilePanel(), gbc);

        screenPanel.add(BorderLayout.CENTER, profilePanelMargin);

        //채팅방 추가
        screenPanel.add(BorderLayout.EAST, createChatRoomPanel());

        return screenPanel;
    }

    class RoundedBorder implements Border {
        private int arcWidth;
        private int arcHeight;
        private Color borderColor;
        private int thickness;

        public RoundedBorder(int arcWidth, int arcHeight, Color borderColor, int thickness) {
            this.arcWidth = arcWidth;
            this.arcHeight = arcHeight;
            this.borderColor = borderColor;
            this.thickness = thickness;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness, thickness, thickness, thickness);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 테두리 색상 및 두께 설정
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(thickness));

            // 둥근 사각형 테두리 그리기
            g2.drawRoundRect(x, y, width - 1, height - 1, arcWidth, arcHeight);
        }
    }

    private JPanel createUserProfilePanel(){
        JPanel profilePanel = new JPanel(new GridLayout(2,1,0,50));
        profilePanel.setBackground(new Color(27, 116, 231));
        profilePanel.setPreferredSize(new Dimension(300,300));

        // User Profile 이미지
        JLabel userImageLabel = new JLabel();
        ImageIcon icon = LoginStudent.getProfileImage();
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 원하는 크기로 조정
        userImageLabel.setHorizontalAlignment(JLabel.CENTER); // 가로 중앙 정렬
        userImageLabel.setVerticalAlignment(JLabel.CENTER); // 세로 중앙 정렬
        userImageLabel.setIcon(new ImageIcon(img));
        userImageLabel.setOpaque(true);
        userImageLabel.setBackground(new Color(27, 116, 231));
        profilePanel.add(userImageLabel);

        //User 이름
        JLabel userName = new JLabel(LoginStudent.getName());
        userName.setHorizontalAlignment(JLabel.CENTER); // 가로 중앙 정렬
        userName.setVerticalAlignment(JLabel.CENTER); // 세로 중앙 정렬
        userName.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
        userName.setOpaque(true);
        userName.setBackground(new Color(27, 116, 231));
        profilePanel.add(userName);

        // user 이름, Profile 이미지를 감싸는 Padding
        JPanel padding = new JPanel();
        padding.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        padding.setBackground(new Color(39, 81, 171));

        padding.add(profilePanel);

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

                chatCommunityPanel.add(addMessage(msg, LoginStudent));

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

                sendMsg(); // 메시지 송신
            }
        });

        // 메시지 입력 TextField
        this.chatTextField = new JTextField();
        this.chatTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = chatTextField.getText();
                chatTextField.setText("");

                chatCommunityPanel.add(addMessage(msg, LoginStudent));

                chatCommunityPanel.add(addMessage(msg, new Professor("1", "김교수", new ImageIcon("./assets/icons/user_icon.png"))));

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

                sendMsg(); // 메시지 송신
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
        ImageIcon profileImage;
        String id;
        String name;

        // 송신된 메시지는 우측에, 수신된 메시지는 좌측에
        JPanel msgGroupPanel = new JPanel();
        msgGroupPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        msgGroupPanel.setBackground(Color.white);
        msgGroupPanel.setLayout(new BoxLayout(msgGroupPanel, BoxLayout.X_AXIS));

        if (user instanceof Student && ((Student) user) == LoginStudent) {
            // 송신자의 정보 (우측 정렬)
            id = ((Student) user).getId();
            name = ((Student) user).getName();
            profileImage = ((Student) user).getProfileImage();

            JLabel msgText = new JLabel(msg);
            JLabel msgProfile = new JLabel(profileImage);

            // 우측 정렬을 위한 공간 추가
            msgGroupPanel.add(Box.createHorizontalGlue());
            msgGroupPanel.add(msgText);
            msgGroupPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Profile Image와 택스트 사이의 공백
            msgGroupPanel.add(msgProfile);

//            msgGroupPanel.setBackground(Color.blue);
        } else {
            // 수신자의 정보 (좌측 정렬)
            id = user.getId();
            name = user.getName();
            profileImage = user.getProfileImage();

            JLabel msgProfile = new JLabel(profileImage);
            JLabel msgText = new JLabel(msg);

            // 좌측 정렬
            msgGroupPanel.add(msgProfile);
            msgGroupPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Profile Image와 택스트 사이의 공백
            msgGroupPanel.add(msgText);
            msgGroupPanel.add(Box.createHorizontalGlue());

//            msgGroupPanel.setBackground(Color.red);
        }

        // 부모 패널 업데이트
        chatCommunityPanel.revalidate();
        chatCommunityPanel.repaint();

        return msgGroupPanel;
    }


    public static void main(String[] args) {
        new MainStudScreenGUI();
    }
}

