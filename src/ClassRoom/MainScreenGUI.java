package ClassRoom;

import javax.swing.*;
import java.awt.*;

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
    private JPanel ChatPanel;
    private JTable studentJTable;
    private JScrollPane StudentTableJScrollPanel;

    private boolean is_team_action_on = false;
    // 해당 부분이 참이면 참여학색, 학생표, 채팅방을 설정 값에 따라 다시 그리기
    private boolean is_screen_share_on = false;

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
    }


    private void createMicButton() {
        this.micBtn = new JButton("",new ImageIcon("./assets/icons/mic_on.png"));
        Icon micIcon = this.micBtn.getIcon();

        Image resize = ((ImageIcon) micIcon).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ((ImageIcon) micIcon).setImage(resize);

        micBtn.setBackground(new Color(0, 0, 0, 0));
        micBtn.setOpaque(false);
        micBtn.setBorderPainted(false);

        micBtn.setPreferredSize(new Dimension(resize.getWidth(null), resize.getHeight(null)));
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
    }

    private void createExitButton(){
        this.exitBtn = new JButton("나가기");

        exitBtn.setBackground(new Color(250, 91, 87));
        exitBtn.setBorder(BorderFactory.createEmptyBorder());

        exitBtn.setPreferredSize(new Dimension(100, 30));

        exitBtn.setForeground(Color.white);
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
                {"최유진", 202312349, 3, "팀원"}
        };

        this.studentJTable = new JTable(data,col);

        // TODO 값 수정 막기

        this.StudentTableJScrollPanel = new JScrollPane(studentJTable);
        add(StudentTableJScrollPanel, BorderLayout.CENTER);



    }
}
