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

    public JPanel buildGUI(TailRoundedPane.TailPosition position) {
        JPanel panel = new JPanel();

        ImageIcon attendanceIcon = new ImageIcon("assets/icons/attendance_student.png");
        Image attendanceIconImage = attendanceIcon.getImage();
        attendanceIconImage = attendanceIconImage.getScaledInstance(69, 69, Image.SCALE_SMOOTH);

        attendanceIcon.setImage(attendanceIconImage);
        ImageIcon absentIcon = new ImageIcon("assets/icons/attendance_student.png");
        Image absentIconImage = absentIcon.getImage();
        absentIconImage = absentIconImage.getScaledInstance(69, 69, Image.SCALE_SMOOTH);
        absentIcon.setImage(absentIconImage);

        JLabel label = new JLabel(attendanceIcon);
        label.setPreferredSize(new Dimension(69, 69));
        label.setBackground(Theme.Darkblue);

        TailRoundedPane roundedPane = new TailRoundedPane(position);
        roundedPane.setContentPane(label);
        roundedPane.setBackground(Theme.Blue);
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
