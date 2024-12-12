package ClassRoom;

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

public class MainScreenGUI extends JFrame {

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
    private JPanel ChatPanel;
    private JTextField chatTextField;
    private JButton sendBtn;
    private JPanel MessageSendControlPanel;
    private JPanel ChatComuPanel;
    private ArrayList<JPanel> TeamPanels;

    //TODO ClassRoom class 만들어서 삽입

    private boolean is_mic_on = false;
    private boolean is_sound_on = true;
    private boolean is_screen_share_possible = true;
    private boolean is_team_action_possible = true;
    private boolean is_chat_on = true;

    public MainScreenGUI(){
        setContentPane(MainScreenPanel);
        setTitle("ClassRoom Main");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1500, 800);
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
                //TODO 메시지 보내기

                String msg = chatTextField.getText();
                chatTextField.setText("");

                ChatComuPanel.add(addMessage(msg, new Professor()));
            }
        });

        this.chatTextField = new JTextField();
        this.chatTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = chatTextField.getText();
                chatTextField.setText("");

                ChatComuPanel.add(addMessage(msg, new Professor()));
            }
        });

        this.MessageSendControlPanel.add(chatTextField, BorderLayout.CENTER);
        this.MessageSendControlPanel.add(sendBtn, BorderLayout.EAST);

        this.ChatPanel.add(MessageSendControlPanel, BorderLayout.SOUTH);

        this.ChatComuPanel = new JPanel();

        ChatComuPanel.setLayout(new BoxLayout(ChatComuPanel, BoxLayout.Y_AXIS));

        // 임시 데이터
        int i;
        for(i=0;i<40;i++){
            ChatComuPanel.add(addMessage("I'm a student", new Student("132", "KJH", new ImageIcon("./assets/icons/user_icon.png"))));
            ChatComuPanel.add(addMessage("I'm a professor", new Professor()));
        }


        this.ChatPanel.add(new JScrollPane(ChatComuPanel), BorderLayout.CENTER);
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

        ChatPanel.revalidate();
        ChatPanel.repaint();

        return msgGroupPanel;
    }
    public static void main(String[] args) {
        new MainScreenGUI();
    }
}


