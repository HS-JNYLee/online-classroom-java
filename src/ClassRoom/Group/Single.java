package ClassRoom.Group;

import Utils.TailRoundedPane;
import Utils.Theme;

import javax.swing.*;
import java.awt.*;

public class Single extends JFrame {
    Single() {
/*        setTitle("Single UI Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);
        buildGUI(TailRoundedPane.TailPosition.TOP_LEFT);

        setVisible(true);*/
    }

    private Boolean attendanceStudent;

    public void setAttendanceStudent(Boolean attendanceStudent) {
        this.attendanceStudent = attendanceStudent;
        // UI 갱신
        SwingUtilities.invokeLater(() -> {
            getContentPane().removeAll();
            buildGUI(tailPosition);
            revalidate();
            repaint();
        });
    }
    private TailRoundedPane.TailPosition tailPosition;
    public JPanel buildGUI(TailRoundedPane.TailPosition position) {
        this.tailPosition = position;
        JPanel panel = new JPanel();

        ImageIcon icon;
        TailRoundedPane roundedPane = new TailRoundedPane(position);
        if (attendanceStudent) {
            ImageIcon attendanceIcon = new ImageIcon("assets/icons/attendance_student.png");
            Image attendanceIconImage = attendanceIcon.getImage();
            attendanceIconImage = attendanceIconImage.getScaledInstance(69, 69, Image.SCALE_SMOOTH);
            icon = new ImageIcon(attendanceIconImage);
        } else {
            ImageIcon absentIcon = new ImageIcon("assets/icons/absent_student.png");
            Image absentIconImage = absentIcon.getImage();
            absentIconImage = absentIconImage.getScaledInstance(69, 69, Image.SCALE_SMOOTH);
            icon = new ImageIcon(absentIconImage);
        }

        JLabel label = new JLabel(icon);
        label.setPreferredSize(new Dimension(69, 69));
        label.setBackground(Theme.Darkblue);

        roundedPane.setContentPane(label);
        if (attendanceStudent) {
            roundedPane.setBackground(Theme.Blue);
        } else {
            roundedPane.setBackground(Theme.Grey);
        }
        roundedPane.setPreferredSize(new Dimension(72, 72));

        panel.add(roundedPane);
        panel.setBackground(Theme.Darkblue);
        add(panel);
        return panel;
    }

    public static void main(String[] args) {
        Single group = new Single();
    }
}
