package ClassRoom.Group;

import Utils.RoundedPane;
import Utils.TailRoundedPane;
import Utils.Theme;

import javax.swing.*;
import java.awt.*;

public class Group extends JFrame {
    Group() {}

    private Boolean[] attendanceStudents = new Boolean[4];

    public void setAttendanceStudents(Boolean[] attendanceStudents) {
        this.attendanceStudents = attendanceStudents;

        revalidate();
        repaint();
        buildGUI();
    }

    public JPanel buildGUI() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setBackground(Theme.Darkblue);

        Single single1 = new Single();
        single1.setAttendanceStudent(attendanceStudents[0]);
        JPanel single1Panel = single1.buildGUI(TailRoundedPane.TailPosition.BOTTOM_RIGHT);
        single1Panel.setBackground(Theme.Darkblue);

        Single single2 = new Single();
        single2.setAttendanceStudent(attendanceStudents[1]);
        JPanel single2Panel = single2.buildGUI(TailRoundedPane.TailPosition.BOTTOM_LEFT);
        single2Panel.setBackground(Theme.Darkblue);

        Single single3 = new Single();
        single3.setAttendanceStudent(attendanceStudents[2]);
        JPanel single3Panel = single3.buildGUI(TailRoundedPane.TailPosition.TOP_RIGHT);
        single3Panel.setBackground(Theme.Darkblue);

        Single single4 = new Single();
        single4.setAttendanceStudent(attendanceStudents[3]);
        JPanel single4Panel = single4.buildGUI(TailRoundedPane.TailPosition.TOP_LEFT);
        single4Panel.setBackground(Theme.Darkblue);

        panel.add(single1Panel);
        panel.add(single2Panel);
        panel.add(single3Panel);
        panel.add(single4Panel);
        panel.setPreferredSize(new Dimension(171, 171));

        RoundedPane roundedPane = new RoundedPane();
        roundedPane.setBackground(Theme.Darkblue);
        roundedPane.setContentPane(panel);
        roundedPane.setPreferredSize(new Dimension(171, 171));

        JPanel wrappedPanel = new JPanel();
        wrappedPanel.add(roundedPane);
        wrappedPanel.setPreferredSize(new Dimension(171, 171));

        add(wrappedPanel);
        return wrappedPanel;
    }
}
