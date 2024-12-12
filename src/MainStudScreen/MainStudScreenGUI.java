package MainStudScreen;

import User.User;
import User.Professor;
import User.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainStudScreenGUI extends JFrame {

    private JButton micBtn;
    private JButton soundBtn;
    private JButton chatBtn;
    private JPanel screenPanel;
    private JButton exitBtn;
    private JPanel chatPanel;

    private boolean is_mic_on = false;
    private boolean is_sound_on = true;
    private boolean is_chat_on = true;

    // 임시 user data
    User tmpUser = new User("1", new ImageIcon("./assets/icons/user_icon.png"), "김재호");

    // User 정보 필요
    private MainStudScreenGUI(){
        setTitle("Class Student Main");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1500, 800);
        setLocationRelativeTo(null);

        JPanel padding = new JPanel(new BorderLayout());
        padding.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        setContentPane(padding);

        getContentPane().setBackground(new Color(43, 61, 81));

        createGUI();

        setVisible(true);
    }

    private void createGUI(){
        this.add(BorderLayout.NORTH, createExitBtn());
        this.add(BorderLayout.SOUTH, createCntlPanel());
        this.add(BorderLayout.CENTER, createScreenPanel());
    }

    private JPanel createExitBtn(){
        JPanel ret = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ret.setOpaque(false);

        this.exitBtn = new JButton("나가기");
        exitBtn.setBackground(Color.red);

        ret.add(exitBtn);

        //TODO 나가기 설계 Login? main?

        return  ret;
    }

    private JPanel createCntlPanel(){
        JPanel btnsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 200,30));

        // 안에 포함된 컴포넌트 구성
        btnsPanel.add(createMicBtn());
        btnsPanel.add(createSoundBtn());
        btnsPanel.add(createChatBtn());


        // CntrlPanel(현재)의 스타일 지정 (padding, margin, radius...etc)
        //Padding
        JPanel ctrlPanelPadding = new JPanel();
        ctrlPanelPadding.setBorder(BorderFactory.createEmptyBorder(0,100,0,100));
        ctrlPanelPadding.add(btnsPanel);
        ctrlPanelPadding.setBackground(new Color(203, 203, 206));

        //Margin
        JPanel ctrlPanelMargin = new JPanel();
        ctrlPanelMargin.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
        ctrlPanelMargin.setOpaque(false);
        btnsPanel.setBackground(new Color(203, 203, 206));

        ctrlPanelMargin.add(ctrlPanelPadding);

        return ctrlPanelMargin;
    }

    private JButton createMicBtn(){
        this.micBtn = new JButton("",new ImageIcon("./assets/icons/mic_off.png"));
        Icon micIcon = this.micBtn.getIcon();

        Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ((ImageIcon) micIcon).setImage(resize);

        micBtn.setBackground(new Color(0, 0, 0, 0));
        micBtn.setOpaque(false);
        micBtn.setBorderPainted(false);

        micBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(is_mic_on){ // 마이크가 켜져있을 때 눌렀을 경우
                    is_mic_on = false;
                    micBtn.setIcon(new ImageIcon("./assets/icons/mic_off.png"));

                    Icon micIcon = micBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                }
                else{ // 마이크가 꺼져있을 때 눌렀을 경우
                    is_mic_on = true;
                    micBtn.setIcon(new ImageIcon("./assets/icons/mic_on.png"));

                    Icon micIcon = micBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                }
            }
        });

        return micBtn;
    }

    private JButton createSoundBtn(){
        this.soundBtn = new JButton("",new ImageIcon("./assets/icons/sound_on.png"));
        Icon micIcon = this.soundBtn.getIcon();

        Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ((ImageIcon) micIcon).setImage(resize);

        soundBtn.setBackground(new Color(0, 0, 0, 0));
        soundBtn.setOpaque(false);
        soundBtn.setBorderPainted(false);

        soundBtn.setPreferredSize(new Dimension(resize.getWidth(null), resize.getHeight(null)));

        soundBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(is_sound_on){ // 소리 버튼이 켜져있을 때 눌렀을 경우
                    is_sound_on = false;
                    soundBtn.setIcon(new ImageIcon("./assets/icons/sound_off.png"));

                    Icon micIcon = soundBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                }
                else{ // 마이크가 꺼져있을 때 눌렀을 경우
                    is_sound_on = true;
                    soundBtn.setIcon(new ImageIcon("./assets/icons/sound_on.png"));

                    Icon micIcon = soundBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                }
            }
        });

        return soundBtn;
    }

    private JButton createChatBtn(){
        this.chatBtn = new JButton("",new ImageIcon("./assets/icons/chat_on.png"));
        Icon micIcon = this.chatBtn.getIcon();

        Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
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

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                    chatPanel.setVisible(false);
                }
                else{ // 채팅화면이 꺼져 있는 경우
                    is_chat_on = true;
                    chatBtn.setIcon(new ImageIcon("./assets/icons/chat_on.png"));

                    Icon micIcon = chatBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                    chatPanel.setVisible(true);
                }
            }
        });

        return chatBtn;
    }

    // 사용자의 프로필 화면, 팅방을 가지는 Panel 설계
    private JPanel createScreenPanel(){
        // 팀 활동을 시작한 이후에는 하단에 팀원들의 정보를 표시하기 위해 borderLayout으로 설정,
        // 더 나아가 화면 필기를 할때는 Center에 그림판을 위치시키기만 하면됨(?)
        this.screenPanel = new JPanel(new BorderLayout());

        screenPanel.setBackground(new Color(39, 81, 171));

        // 현재 로그인한 사용자의 Profile 표시
        screenPanel.add(BorderLayout.CENTER, createUserProfilePanel());

        //채팅방 추가
//        screenPanel.add(BorderLayout.EAST, createChatRoomPanel());

        return screenPanel;
    }

    private JPanel createUserProfilePanel(){
        JPanel profilePanel = new JPanel(new GridLayout(2,1,0,50));
        profilePanel.setBackground(new Color(27, 116, 231));
        profilePanel.setPreferredSize(new Dimension(300,300));

        // User Profile 이미지
        JLabel userImageLabel = new JLabel();
        ImageIcon icon = tmpUser.getProfileImage();
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 원하는 크기로 조정
        userImageLabel.setHorizontalAlignment(JLabel.CENTER); // 가로 중앙 정렬
        userImageLabel.setVerticalAlignment(JLabel.CENTER); // 세로 중앙 정렬
        userImageLabel.setIcon(new ImageIcon(img));
        userImageLabel.setOpaque(true);
        userImageLabel.setBackground(new Color(27, 116, 231));
        profilePanel.add(userImageLabel);

        //User 이름
        JLabel userName = new JLabel(tmpUser.getName());
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



    private JPanel addMessage(String msg, User user){

        ImageIcon profileImage;
        String id;
        String name;
        JPanel msgGroupPanel = new JPanel(new FlowLayout());

        if (user instanceof Student) {
            id = ((Student) user).getId();
            name = ((Student) user).getName();
            profileImage = ((Student) user).getProfileImage();

            // 프로필 이미지 레이블 생성
            JLabel msgProfile = new JLabel(profileImage);

            // 메시지 텍스트 레이블 생성
            JLabel msgText = new JLabel(msg);

            // 이미지와 텍스트의 배치를 위한 설정
            msgGroupPanel.setAlignmentX(FlowLayout.RIGHT);

            // 메시지 텍스트와 이미지를 각각 패널에 추가
            msgGroupPanel.add(msgProfile);
            msgGroupPanel.add(msgText);  // 이제 메시지 텍스트도 추가

            msgGroupPanel.setBackground(Color.blue);

        } else if (user instanceof Professor) {
            id = ((Professor) user).getId();
            name = ((Professor) user).getName();
            profileImage = ((Professor) user).getProfileImage();

            // 프로필 이미지 레이블 생성
            JLabel msgProfile = new JLabel(profileImage);

            // 메시지 텍스트 레이블 생성
            JLabel msgText = new JLabel(msg);

            // 메시지 텍스트와 이미지를 각각 패널에 추가
            msgGroupPanel.setAlignmentX(FlowLayout.LEFT);

            // 텍스트와 이미지를 추가
            msgGroupPanel.add(msgProfile);
            msgGroupPanel.add(msgText);

            msgGroupPanel.setBackground(Color.red);  // 배경색 설정 (선택 사항)
        }

        chatPanel.revalidate();
        chatPanel.repaint();

        return msgGroupPanel;
    }


    public static void main(String[] args) {
        new MainStudScreenGUI();
    }
}

