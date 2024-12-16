package ClassRoom;

import MainStudScreen.CommunicationCallbacks;
import User.Professor;
import User.Student;
import User.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import static User.User.roleToString;

public class MainScreenGUI extends JFrame {
    private CommunicationCallbacks communicationCallbacks;

    private JButton micBtn;
    private JButton soundBtn;
    private JButton screenShareBtn;
    private JButton teamMakeBtn;
    private JButton chatBtn;
    private JPanel MainScreenPanel;
    private JButton exitBtn;
    private JPanel ControllerPanel;
    private JPanel ControllerPanelMargin;
    private JPanel ExitButtonPanel;
    private JPanel VariousPanel;
    private JPanel TeamStudentPanel;
    private JPanel StudentTablePanel;
    private JTable studentJTable;
    private JScrollPane StudentTableJScrollPanel;
    private JScrollPane chatScroller;
    private JPanel ChatPanel;
    private JTextField chatTextField;
    private JButton sendBtn;
    private JPanel MessageSendControlPanel;
    private JPanel ChatComuPanel;
    private ArrayList<JPanel> TeamPanels;

    private Professor LoginProf;

    private boolean is_mic_on = false;
    private boolean is_sound_on = true;
    private boolean is_screen_share_possible = true;
    private boolean is_team_action_possible = true;
    private boolean is_chat_on = true;

    private void sendMsg(ChatMsg chatMsg){
        this.communicationCallbacks.send(chatMsg);
    }

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

    public MainScreenGUI(CommunicationCallbacks communicationCallbacks, Professor user){
        setContentPane(MainScreenPanel);
        setTitle("ClassRoom Main");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1500, 800);

        this.communicationCallbacks = communicationCallbacks;
        this.LoginProf = user;
        System.out.println("교수화면 : " + LoginProf.getId() + LoginProf.getName());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUIComponents() {
        createMicButton();
        createSoundButton();
        createScreenShareButton();
        createTeamMakeButton();
        createChatButton();
        createExitButton();
        createStudentPanel();
        createChatRoomPanel();
        createTeamStudentPanel();
    }


    private void createMicButton() {
        this.micBtn = new JButton("",new ImageIcon("./assets/icons/mic_off.png"));
        Icon micIcon = this.micBtn.getIcon();

        Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ((ImageIcon) micIcon).setImage(resize);

        micBtn.setBackground(new Color(0, 0, 0, 0));
        micBtn.setOpaque(false);
        micBtn.setBorderPainted(false);

        micBtn.setPreferredSize(new Dimension(resize.getWidth(null), resize.getHeight(null)));

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
    }

    private void createSoundButton(){
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
                else{ // 소리가 꺼져있을 때 눌렀을 경우
                    is_sound_on = true;
                    soundBtn.setIcon(new ImageIcon("./assets/icons/sound_on.png"));

                    Icon micIcon = soundBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                }
            }
        });
    }

    private void createScreenShareButton(){
        this.screenShareBtn = new JButton("",new ImageIcon("./assets/icons/screen_share_on.png"));
        Icon micIcon = this.screenShareBtn.getIcon();

        Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ((ImageIcon) micIcon).setImage(resize);

        screenShareBtn.setBackground(new Color(0, 0, 0, 0));
        screenShareBtn.setOpaque(false);
        screenShareBtn.setBorderPainted(false);

        screenShareBtn.setPreferredSize(new Dimension(resize.getWidth(null), resize.getHeight(null)));

        screenShareBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(is_screen_share_possible){ // 화면 공유 시작 가능
                    is_screen_share_possible = false;
                    screenShareBtn.setIcon(new ImageIcon("./assets/icons/screen_share_off.png"));

                    Icon micIcon = screenShareBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                    //TODO 공유 시작
                }
                else{ // 화면 공유중인 경우
                    is_screen_share_possible = true;
                    screenShareBtn.setIcon(new ImageIcon("./assets/icons/screen_share_on.png"));

                    Icon micIcon = screenShareBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                    //TODO 공유 종료
                }
            }
        });
    }

    private void createTeamMakeButton(){
        this.teamMakeBtn = new JButton("",new ImageIcon("./assets/icons/team_make_on.png"));
        Icon micIcon = this.teamMakeBtn.getIcon();

        Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ((ImageIcon) micIcon).setImage(resize);

        teamMakeBtn.setBackground(new Color(0, 0, 0, 0));
        teamMakeBtn.setOpaque(false);
        teamMakeBtn.setBorderPainted(false);

        teamMakeBtn.setPreferredSize(new Dimension(resize.getWidth(null), resize.getHeight(null)));
        teamMakeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(is_team_action_possible){ // 팀활동 시작이 가능한 경우
                    is_team_action_possible = false;
                    teamMakeBtn.setIcon(new ImageIcon("./assets/icons/team_make_off.png"));

                    Icon micIcon = teamMakeBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                    //TODO 팀활동 시작
                }
                else{ // 팀활동 중인 경우
                    is_team_action_possible = true;
                    teamMakeBtn.setIcon(new ImageIcon("./assets/icons/team_make_on.png"));

                    Icon micIcon = teamMakeBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                    // TODO 팀활동 종료
                }
            }
        });
    }

    private void createChatButton(){
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
                    ChatPanel.setVisible(false);
                }
                else{ // 채팅화면이 꺼져 있는 경우
                    is_chat_on = true;
                    chatBtn.setIcon(new ImageIcon("./assets/icons/chat_on.png"));

                    Icon micIcon = chatBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                    ChatPanel.setVisible(true);
                }
            }
        });
    }

    private void createExitButton(){
        this.exitBtn = new JButton("나가기");

        exitBtn.setBackground(new Color(250, 91, 87));
        exitBtn.setBorder(BorderFactory.createEmptyBorder());

        exitBtn.setPreferredSize(new Dimension(100, 30));

        exitBtn.setForeground(Color.white);
        // TODO 통신 종료
    }

    private void createTeamStudentPanel(){
        this.TeamStudentPanel = new JPanel(new GridLayout(2,2));

        TeamStudentPanel.setPreferredSize(new Dimension(500,500));

        TeamStudentPanel.setBackground(Color.blue);

        this.TeamPanels = new ArrayList<JPanel>(4);

        ArrayList<Color> colors = new ArrayList<>(Arrays.asList(
                Color.BLUE,
                Color.RED,
                Color.GREEN,
                Color.YELLOW
        ));

        int i;
        int j;
        for(i=0;i<4;i++){
            JPanel teamPanel = new JPanel(new GridLayout(2,2));
            for(j=0;j<4;j++){ // TODO j<classRoom && j<4의 학생수 보다 작다면... 추가
                if(j==3){
                    JLabel userPanel = new JLabel();
                    ImageIcon icon = new ImageIcon("./assets/user_icon.png");
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // 원하는 크기로 조정
                    userPanel.setIcon(new ImageIcon(img));
                    teamPanel.add(userPanel);
                    continue;
                }

                JLabel userPanel = new JLabel();
                ImageIcon icon = new ImageIcon("./assets/non_join_user.png");
                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // 원하는 크기로 조정
                userPanel.setIcon(new ImageIcon(img));
                teamPanel.add(userPanel);
            }
            teamPanel.setBackground(Color.MAGENTA);
            TeamPanels.add(teamPanel);
        }



        int index = 0;
        for(JPanel team : TeamPanels){
            team.setBackground(colors.get(index % colors.size()));
            TeamStudentPanel.add(team);
            index++;
        }

    }

    private void createStudentPanel(){

        String[] col = new String[]{"이름", "학번","모둠번호","역할"};

        Object[][] data = {
                {"김재호", 202312345, 1, "팀장"},
                {"이수민", 202312346, 1, "팀원"},
                {"박지훈", 202312347, 2, "팀원"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"정하은", 202312348, 2, "팀장"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"},
                {"최유진", 202312349, 3, "팀원"}
        };

        DefaultTableModel model = new DefaultTableModel(data, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.studentJTable = new JTable(model);

        JTableHeader header = studentJTable.getTableHeader();
        header.setBackground(new Color(44, 55, 76));
        header.setForeground(Color.WHITE);

        this.StudentTableJScrollPanel = new JScrollPane(studentJTable);
        add(StudentTableJScrollPanel, BorderLayout.CENTER);

    }

    private void createChatRoomPanel(){

        this.ChatPanel = new JPanel(new BorderLayout());

        this.ChatPanel.setPreferredSize(new Dimension(300,500));

        this.MessageSendControlPanel = new JPanel(new BorderLayout());

        this.sendBtn = new JButton("보내기");
        this.sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = chatTextField.getText();
                chatTextField.setText("");

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

        this.chatTextField = new JTextField();
        this.chatTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = chatTextField.getText();
                chatTextField.setText("");

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

        this.MessageSendControlPanel.add(chatTextField, BorderLayout.CENTER);
        this.MessageSendControlPanel.add(sendBtn, BorderLayout.EAST);

        this.ChatPanel.add(MessageSendControlPanel, BorderLayout.SOUTH);

        this.ChatComuPanel = new JPanel();
        ChatComuPanel.setBackground(Color.WHITE);
        ChatComuPanel.setLayout(new BoxLayout(ChatComuPanel, BoxLayout.Y_AXIS));

        this.chatScroller = new JScrollPane(ChatComuPanel);
        this.ChatPanel.add(chatScroller, BorderLayout.CENTER);
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

        ChatComuPanel.add(msgGroupPanel);

        // 부모 패널 업데이트
        ChatComuPanel.revalidate();
        ChatComuPanel.repaint();

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
//        new MainScreenGUI();
    }
}


