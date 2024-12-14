package ClassRoom.Group;

import Utils.RoundedPane;
import Utils.TailRoundedPane;
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
    }

    public JPanel buildGUI() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setBackground(Theme.Ultramarine);

        Group group1 = new Group();
        JPanel group1Panel = group1.buildGUI();
        group1Panel.setBackground(Theme.Ultramarine);

        Group group2 = new Group();
        JPanel group2Panel = group2.buildGUI();
        group2Panel.setBackground(Theme.Ultramarine);
        Group group3 = new Group();
        JPanel group3Panel = group3.buildGUI();
        group3Panel.setBackground(Theme.Ultramarine);
        Group group4 = new Group();
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
