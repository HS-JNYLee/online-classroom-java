package ClassRoom.Group;

import Utils.TailRoundedPane;
import Utils.Theme;

import javax.swing.*;
import java.awt.*;

public class Single extends JFrame {
    Single() {}

    private Boolean attendanceStudent;

    public void setAttendanceStudent(Boolean attendanceStudent) {
        this.attendanceStudent = attendanceStudent;

        getContentPane().removeAll();
        buildGUI(tailPosition);
        revalidate();
        repaint();
    }

    private TailRoundedPane.TailPosition tailPosition;

    public JPanel buildGUI(TailRoundedPane.TailPosition position) {
        this.tailPosition = position;

        ImageIcon icon;
        if (attendanceStudent) {
            String attendIconFilePath = "assets/icons/attendance_student.png";
            ImageIcon attendanceIcon = new ImageIcon(attendIconFilePath);
            Image attendanceIconImage = attendanceIcon.getImage();
            attendanceIconImage = attendanceIconImage.getScaledInstance(69, 69, Image.SCALE_SMOOTH);
            icon = new ImageIcon(attendanceIconImage);
        } else {
            String absentIconFilePath = "assets/icons/absent_student.png";
            ImageIcon absentIcon = new ImageIcon(absentIconFilePath);
            Image absentIconImage = absentIcon.getImage();
            absentIconImage = absentIconImage.getScaledInstance(69, 69, Image.SCALE_SMOOTH);
            icon = new ImageIcon(absentIconImage);
        }

        JLabel label = new JLabel(icon);
        label.setPreferredSize(new Dimension(69, 69));
        label.setBackground(Theme.Darkblue);

        TailRoundedPane roundedPane = new TailRoundedPane(position);
        roundedPane.setContentPane(label);
        if (attendanceStudent) {
            roundedPane.setBackground(Theme.Blue);
        } else {
            roundedPane.setBackground(Theme.Grey);
        }
        roundedPane.setPreferredSize(new Dimension(72, 72));

        JPanel panel = new JPanel();
        panel.add(roundedPane);
        panel.setBackground(Theme.Darkblue);

        add(panel);
        return panel;
    }
}
