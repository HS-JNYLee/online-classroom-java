package MainStudScreen;

import ClassRoom.SelectImageButton;
import User.Roles;
import User.Student;
import Utils.Theme;

import javax.swing.*;
import java.awt.*;

public class ProfileInfoPanel {

    private Student student;
    private JPanel padding;

    public ProfileInfoPanel(Student student){
        this.student = student;
    }

    public JPanel buildPanel(){
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Theme.Yellow);

        SelectImageButton circleIcon = new SelectImageButton();
        circleIcon.setImage(student.getProfileImage().getImage());
        circleIcon.setHeight(30);
        circleIcon.setWidth(30);
        circleIcon.getButton().setEnabled(false);

        profilePanel.add(circleIcon.getButton());

        JLabel nameLabel = new JLabel("이름 : " + student.getName());
        profilePanel.add(nameLabel);

        JLabel idLabel = new JLabel("학번 : " + student.getId());
        profilePanel.add(idLabel);

        JLabel teamNumLabel = new JLabel("모둠 : 모둠#" + student.getTeamNum());
        profilePanel.add(teamNumLabel);

        String teamRole;
        if(student.getRole()== Roles.TEAM_LEADER){
            teamRole = "팀장";
        } else {
            teamRole = "팀원";
        }

        JLabel teamRoleLabel = new JLabel("역할 : " +teamRole);
        profilePanel.add(teamRoleLabel);

        this.padding = new JPanel();
        padding.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        padding.setBackground(Theme.Yellow);
        padding.add(profilePanel);

        return padding;
    }

    public void setVisibility(boolean status){
        this.padding.setVisible(status);
    }

    public Student getStudent(){ return this.student; }

}
