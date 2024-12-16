package ClassRoom.Group;

import Utils.RoundedPane;
import Utils.Theme;

import javax.swing.*;
import java.awt.*;

public class MultiGroup extends JFrame {
    public MultiGroup() {
/*        setTitle("MultiGroup UI Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);
        buildGUI();

        setVisible(true);*/
        group1 = new Group();
        group2 = new Group();
        group3 = new Group();
        group4 = new Group();
    }

    private final Boolean[][] attendanceStudents = {
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
    };

    public JPanel participate(int index) {
        attendanceStudents[index / 4][index % 4] = true;
        // 전체 갱신
        revalidate();
        repaint();

        return buildGUI();
    }

    public JPanel absent(int index) {
        attendanceStudents[index / 4][index % 4] = false;
        // 전체 갱신
        revalidate();
        repaint();

        return buildGUI();
    }

    private Group getGroupByIndex(int index) {
        return switch (index / 4) {
            case 0 -> group1;
            case 1 -> group2;
            case 2 -> group3;
            case 3 -> group4;
            default -> null;
        };
    }

    Group group1;
    Group group2;
    Group group3;
    Group group4;

    public JPanel buildGUI() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setBackground(Theme.Ultramarine);

        group1.setAttendanceStudents(attendanceStudents[0]);
        JPanel group1Panel = group1.buildGUI();
        group1Panel.setBackground(Theme.Ultramarine);

        group2.setAttendanceStudents(attendanceStudents[1]);
        JPanel group2Panel = group2.buildGUI();
        group2Panel.setBackground(Theme.Ultramarine);
        group3.setAttendanceStudents(attendanceStudents[2]);
        JPanel group3Panel = group3.buildGUI();
        group3Panel.setBackground(Theme.Ultramarine);
        group4.setAttendanceStudents(attendanceStudents[3]);
        JPanel group4Panel = group4.buildGUI();
        group4Panel.setBackground(Theme.Ultramarine);

        panel.add(group1Panel);
        panel.add(group2Panel);
        panel.add(group3Panel);
        panel.add(group4Panel);
        panel.setPreferredSize(new Dimension(364, 364));

        RoundedPane roundedPane = new RoundedPane();
        roundedPane.setBackground(Theme.Ultramarine);
        roundedPane.setContentPane(panel);
        roundedPane.setPreferredSize(new Dimension(364, 364));

        JPanel wrappedPanel = new JPanel();
        wrappedPanel.add(roundedPane);
        wrappedPanel.setPreferredSize(new Dimension(364, 364));
        add(wrappedPanel);
        return panel;
    }

    public static void main(String[] args) {
        MultiGroup group = new MultiGroup();
    }
}
