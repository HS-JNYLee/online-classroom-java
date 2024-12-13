package ClassRoom;

import Utils.Icons;
import Utils.RoundedPane;
import Utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LectureScreenGUI extends JFrame {
    private int screenWidth = 912;
    private int screenHeight = 513;
    LectureScreenGUI() {
        super("Lecture Screen");

        buildGUI();

        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void buildGUI() {
        JPanel palettePanel = getPalettePanel();
        JPanel controlPanel = getControlPanel();
        JPanel buttonPanel = getButtonPanel();
        JPanel screenPanel = getScreenPanel();
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(156, screenHeight));
        emptyPanel.setBackground(Theme.Ultramarine);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(emptyPanel, BorderLayout.EAST);
        contentPane.add(palettePanel, BorderLayout.WEST);
        contentPane.add(controlPanel, BorderLayout.SOUTH);
        contentPane.add(screenPanel, BorderLayout.CENTER);
        contentPane.setBackground(Theme.Ultramarine);
        add(contentPane);
    }

    public JPanel getScreenPanel() {
        JPanel screenPanel = new JPanel(new BorderLayout());
        RoundedPane roundedPane = new RoundedPane();
        VideoPanel videoPanel = new VideoPanel();
        BookmarkSlider bookmarkSlider = new BookmarkSlider(videoPanel);
        new Thread(() -> simulateVideoFrames(videoPanel, bookmarkSlider)).start();
        screenPanel.add(videoPanel, BorderLayout.CENTER);
        screenPanel.add(bookmarkSlider, BorderLayout.SOUTH);

        roundedPane.setContentPane(screenPanel);
        roundedPane.setBackground(Theme.Blue);

        return roundedPane;
    }

    public JButton createIconButton(ImageIcon icon) {
        JButton iconButton = new JButton();
        iconButton.setBackground(Theme.White);
        iconButton.setBorderPainted(false);
        iconButton.setIcon(icon);
        return iconButton;
    }

    public JPanel getPalettePanel() {
        JPanel palettePanel = new JPanel();
        palettePanel.setLayout(new GridLayout(6, 1, 10, 10));
        palettePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        palettePanel.setBackground(Theme.White);
        palettePanel.setPreferredSize(new Dimension(156, screenHeight));

        JButton b1 = createIconButton(Icons.bookmarkInactiveIcon);
        JButton b2 = createIconButton(Icons.eraserInactiveIcon);
        JButton b3 = createIconButton(Icons.penInactiveIcon);
        JButton b4 = createIconButton(Icons.redPaletteIcon);
        JButton b5 = createIconButton(Icons.greenPaletteIcon);
        JButton b6 = createIconButton(Icons.bluePaletteIcon);

        palettePanel.add(b1);
        palettePanel.add(b2);
        palettePanel.add(b3);
        palettePanel.add(b4);
        palettePanel.add(b5);
        palettePanel.add(b6);

        RoundedPane roundedPane = new RoundedPane();
        roundedPane.setContentPane(palettePanel);
        roundedPane.setBackground(Theme.White);
        roundedPane.setPreferredSize(new Dimension(43, 240));
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Theme.Ultramarine);
        wrapper.setPreferredSize(new Dimension(156, screenHeight));
        wrapper.add(roundedPane);
        return wrapper;
    }
    public JPanel getControlPanel() {
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBackground(Theme.Ultramarine);
        JLabel label = new JLabel("Control Panel");
        label.setForeground(Color.WHITE);
        label.setSize(screenWidth, screenHeight);
        controlPanel.add(label);
        controlPanel.setPreferredSize(new Dimension(screenWidth, 65));
        return controlPanel;
    }
    public JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Theme.Ultramarine);
        buttonPanel.setPreferredSize(new Dimension(screenWidth, 32));
        return buttonPanel;
    }

    public static void main(String[] args) {
        LectureScreenGUI gui = new LectureScreenGUI();
    }

    private static void simulateVideoFrames(VideoPanel videoPanel, BookmarkSlider bookmarkSlider) {
        try {
            int frameCount = 0;
            while (true) {
                BufferedImage frame = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = frame.createGraphics();
                g.setColor(new Color((frameCount * 10) % 255, (frameCount * 5) % 255, (frameCount * 3) % 255));
                g.fillRect(0, 0, 800, 600);
                g.setColor(Color.WHITE);
                g.drawString("Frame: " + frameCount, 350, 300);
                g.dispose();

                videoPanel.updateFrame(frame);
                bookmarkSlider.addFrame(frame);

                frameCount++;
                Thread.sleep(1000); // Simulate 10 FPS
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
