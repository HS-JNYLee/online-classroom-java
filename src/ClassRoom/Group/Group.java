package ClassRoom.Group;

import Utils.RoundedPane;
import Utils.TailRoundedPane;
import Utils.Theme;

import javax.swing.*;
import java.awt.*;

public class Group extends JFrame {
    Group() {
/*        setTitle("Group UI Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);
        buildGUI();

        setVisible(true);*/
    }

    public JPanel buildGUI() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setBackground(Theme.Darkblue);

        Single single1 = new Single();
        JPanel single1Panel = single1.buildGUI(TailRoundedPane.TailPosition.BOTTOM_RIGHT);
        single1Panel.setBackground(Theme.Darkblue);

        Single single2 = new Single();
        JPanel single2Panel = single2.buildGUI(TailRoundedPane.TailPosition.BOTTOM_LEFT);
        single2Panel.setBackground(Theme.Darkblue);

        Single single3 = new Single();
        JPanel single3Panel = single3.buildGUI(TailRoundedPane.TailPosition.TOP_RIGHT);
        single3Panel.setBackground(Theme.Darkblue);

        Single single4 = new Single();
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

    public static void main(String[] args) {
        Group group = new Group();
    }
}
