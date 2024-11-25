package ClassRoom;

import User.Professor;
import User.Student;
import User.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JPanel ChatPanelMargin;
    private JTable studentJTable;
    private JScrollPane StudentTableJScrollPanel;
    private JPanel ChatPanel;
    private JTextField chatTextField;
    private JButton sendBtn;
    private JPanel MessageSendControlPanel;
    private JScrollPane ChatComuPanel;

    private boolean is_mic_on = false;
    private boolean is_sound_on = true;
    private boolean is_screen_share_possible = true;
    private boolean is_team_action_possible = true;
    private boolean is_chat_on = true;

    public MainScreenGUI(){
        setContentPane(MainScreenPanel);
        setTitle("ClassRoom Main");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
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
                else{ // 마이크가 꺼져있을 때 눌렀을 경우
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
                    //TODO 팀활동 시작
                }
                else{ // 채팅화면이 꺼져 있는 경우
                    is_chat_on = true;
                    chatBtn.setIcon(new ImageIcon("./assets/icons/chat_on.png"));

                    Icon micIcon = chatBtn.getIcon();

                    Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
                    ((ImageIcon) micIcon).setImage(resize);
                    // TODO 팀활동 종료
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

        this.ChatPanelMargin = new JPanel(new BorderLayout());

        this.ChatPanel = new JPanel(new BorderLayout());
        ChatPanel.setBackground(Color.red);

        JPanel ttmp = new JPanel(new GridLayout(20,1));

        for (int i = 1; i <= 20; i++) {
            ttmp.add(new JLabel("Label " + i));
        }

        this.ChatPanel.add(ttmp,BorderLayout.CENTER);

        this.ChatComuPanel = new JScrollPane(ttmp);

        this.MessageSendControlPanel = new JPanel(new BorderLayout());

        this.sendBtn = new JButton("보내기");
        this.chatTextField = new JTextField();

        ChatComuPanel.setPreferredSize(new Dimension(200,200));
        ChatPanelMargin.setPreferredSize(new Dimension(200,200));
        ChatPanel.setPreferredSize(new Dimension(200,200));

        this.ChatPanelMargin.add(ChatComuPanel, BorderLayout.CENTER);

        this.MessageSendControlPanel.add(sendBtn , BorderLayout.CENTER);
        this.MessageSendControlPanel.add(chatTextField , BorderLayout.EAST);

        this.ChatPanel.add(MessageSendControlPanel, BorderLayout.SOUTH);
    }

    private JPanel addMessage(String msg, User user){

        ImageIcon profileImage;
        String id;
        String name;
        JPanel msgGroupPanel = new JPanel(new FlowLayout());

        if(user instanceof Student){
            id = ((Student)user).getId();
            name = ((Student)user).getName();
            profileImage = ((Student)user).getProfileImage();

//            JPanel

            msgGroupPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        }else if(user instanceof Professor){
            id = ((Professor)user).getId();
            name = ((Professor)user).getName();
            profileImage = ((Professor)user).getProfileImage();

            msgGroupPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        }

        return msgGroupPanel;
    }
}
