package ClassRoom.Group;

import Utils.RoundedPane;
import Utils.Theme;

import javax.swing.*;
import java.awt.*;

public class MultiGroup extends JFrame {
    public MultiGroup() {}

    private final Boolean[][] attendanceStudents = {
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
    };

    public void participate(int index) {
        attendanceStudents[index / 4][index % 4] = true;
        revalidate();
        repaint();
        buildGUI();
    }

    public void absent(int index) {
        attendanceStudents[index / 4][index % 4] = false;
        revalidate();
        repaint();
        buildGUI();
    }

    Group group1 = new Group();
    Group group2 = new Group();
    Group group3 = new Group();
    Group group4 = new Group();

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
        roundedPane.setContentPane(panel);
        roundedPane.setBackground(Theme.Ultramarine);
        roundedPane.setPreferredSize(new Dimension(364, 364));

        JPanel wrappedPanel = new JPanel();
        wrappedPanel.add(roundedPane);
        wrappedPanel.setPreferredSize(new Dimension(364, 364));

        add(wrappedPanel);
        return panel;
    }
}
