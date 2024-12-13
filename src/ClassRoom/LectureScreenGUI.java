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

    // 팔레트 패널 만드는 함수
    public JPanel getPalettePanel() {
        // 팔레트 버튼 모음
        // 북마크
        PaletteButton b1 = new PaletteToggleButton(Icons.bookmarkInactiveIcon, Icons.bookmarkActiveIcon);
        // 지우개
        PaletteButton b2 = new PaletteToggleButton(Icons.eraserInactiveIcon, Icons.eraserActiveIcon);
        // 연필
        PaletteButton b3 = new PaletteToggleButton(Icons.penInactiveIcon, Icons.penActiveIcon);
        // 연필 색 - 빨강
        PaletteButton b4 = new PaletteColorButton(Icons.redPaletteIcon);
        // 연필 색 - 초록
        PaletteButton b5 = new PaletteColorButton(Icons.greenPaletteIcon);
        // 연픽 색 - 파랑
        PaletteButton b6 = new PaletteColorButton(Icons.bluePaletteIcon);

        JPanel palettePanel = new JPanel();
        palettePanel.setBackground(Theme.White);
        palettePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        palettePanel.setPreferredSize(new Dimension(164, screenHeight));
        palettePanel.setLayout(new GridLayout(6, 1, 10, 10));

        palettePanel.add(b1);
        palettePanel.add(b2);
        palettePanel.add(b3);
        palettePanel.add(b4);
        palettePanel.add(b5);
        palettePanel.add(b6);
        // ---------- 팔레트 버튼 모음 끝

        // 팔레트 패널 둥글게 만들기
        RoundedPane roundedPane = new RoundedPane();
        roundedPane.setBackground(Theme.White);
        roundedPane.setPreferredSize(new Dimension(45, 253));

        roundedPane.setContentPane(palettePanel);
        // ---------- 둥근 팔레트 끝

        // 팔레트 패널 중앙 정렬
        JPanel paletteWrapper = new JPanel(new GridBagLayout());
        paletteWrapper.setBackground(Theme.Ultramarine);
        paletteWrapper.setPreferredSize(new Dimension(164, screenHeight));

        paletteWrapper.add(roundedPane);
        // ---------- 팔레트 중앙 정렬 끝
        return paletteWrapper;
    }

    public JPanel getControlPanel() {
        JLabel label = new JLabel("Control Panel");
        label.setForeground(Color.WHITE);
        label.setSize(screenWidth, screenHeight);

        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBackground(Theme.Ultramarine);
        controlPanel.setPreferredSize(new Dimension(screenWidth, 68));

        controlPanel.add(label);

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
