package ClassRoom;

import Utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LectureScreenGUI extends JFrame {
    private final int screenWidth = 960;
    private final int screenHeight = 540;
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
        emptyPanel.setPreferredSize(new Dimension(164, screenHeight));
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

    public JPanel getPalettePanel() {
        JPanel palettePanel = new JPanel();
        palettePanel.setLayout(new GridLayout(6, 1, 10, 10));
        palettePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        palettePanel.setBackground(Theme.White);
        palettePanel.setPreferredSize(new Dimension(164, screenHeight));

        PaletteButton b1 = new PaletteToggleButton(Icons.bookmarkInactiveIcon, Icons.bookmarkActiveIcon);
        PaletteButton b2 = new PaletteToggleButton(Icons.eraserInactiveIcon, Icons.eraserActiveIcon);
        PaletteButton b3 = new PaletteToggleButton(Icons.penInactiveIcon, Icons.penActiveIcon);
        PaletteButton b4 = new PaletteColorButton(Icons.redPaletteIcon);
        PaletteButton b5 = new PaletteColorButton(Icons.greenPaletteIcon);
        PaletteButton b6 = new PaletteColorButton(Icons.bluePaletteIcon);

        palettePanel.add(b1);
        palettePanel.add(b2);
        palettePanel.add(b3);
        palettePanel.add(b4);
        palettePanel.add(b5);
        palettePanel.add(b6);

        RoundedPane roundedPane = new RoundedPane();
        roundedPane.setContentPane(palettePanel);
        roundedPane.setBackground(Theme.White);
        roundedPane.setPreferredSize(new Dimension(45, 253));
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Theme.Ultramarine);
        wrapper.setPreferredSize(new Dimension(164, screenHeight));
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
        controlPanel.setPreferredSize(new Dimension(screenWidth, 68));
        return controlPanel;
    }

    public JPanel getButtonPanel() {
        JPanel buttonWrapper = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Theme.Red);
        PaletteButton exitButton = new PaletteColorButton(Icons.exitIcon);
        exitButton.setPreferredSize(new Dimension(30, 31));
        buttonPanel.add(exitButton, BorderLayout.WEST);
        JLabel label = new JLabel("나가기");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Theme.White);
        label.setBackground(Theme.Red);
        buttonPanel.add(label, BorderLayout.CENTER);

        buttonPanel.setPreferredSize(new Dimension(100, 31));
        RoundedShadowPane roundedShadowPane = new RoundedShadowPane();
        roundedShadowPane.setContentPane(buttonPanel);
        roundedShadowPane.setBackground(Theme.Red);
        roundedShadowPane.setPreferredSize(new Dimension(100, 31));
        buttonWrapper.add(roundedShadowPane, BorderLayout.WEST);
        buttonWrapper.setBorder(new EmptyBorder(5, 5, 0, 0));
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Theme.Ultramarine);
        buttonWrapper.add(emptyPanel, BorderLayout.CENTER);
        buttonWrapper.setPreferredSize(new Dimension(screenWidth, 57));

        buttonWrapper.setBackground(Theme.Ultramarine);
        return buttonWrapper;
    }

    public static void main(String[] args) {
        new LectureScreenGUI();
    }

    private static void simulateVideoFrames(VideoPanel videoPanel, BookmarkSlider bookmarkSlider) {
        try {
            int frameCount = 0;
            while (true) {
                BufferedImage frame = new BufferedImage(842, 631, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = frame.createGraphics();
                g.setColor(new Color((frameCount * 10) % 255, (frameCount * 5) % 255, (frameCount * 3) % 255));
                g.fillRect(0, 0, 842, 631);
                g.setColor(Color.WHITE);
                g.drawString("Frame: " + frameCount, 421, 315);
                g.dispose();

                videoPanel.updateFrame(frame);
                bookmarkSlider.addFrame(frame);

                frameCount++;
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
