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
        super("수업 중...");

        buildGUI();

        setLocationRelativeTo(null);
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void buildGUI() {
        // 팔레트 패널
        JPanel palettePanel = getPalettePanel();
        // 앱 제어 패널
        JPanel controlPanel = getControlPanel();
        // 나가기 버튼 패널
        JPanel buttonPanel = getButtonPanel();
        // 수업 화면 패널
        JPanel screenPanel = getScreenPanel();
        // 빈 패널 (위치 조정용)
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(164, screenHeight));
        emptyPanel.setBackground(Theme.Ultramarine);

        // 실시간 녹화 전체 패널
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Theme.Ultramarine);

        contentPanel.add(emptyPanel, BorderLayout.EAST);
        contentPanel.add(buttonPanel, BorderLayout.NORTH);
        contentPanel.add(palettePanel, BorderLayout.WEST);
        contentPanel.add(controlPanel, BorderLayout.SOUTH);
        contentPanel.add(screenPanel, BorderLayout.CENTER);
        // ---------- 전체 패널 끝
        add(contentPanel);
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
        // 나가기 버튼 아이콘
        PaletteButton exitButton = new PaletteColorButton(Icons.exitIcon);
        exitButton.setPreferredSize(new Dimension(30, 31));
        // ---------- 나가기 버튼 아이콘 끝
        
        // 나가기 라벨
        JLabel label = new JLabel("나가기");
        label.setBackground(Theme.Red);
        label.setForeground(Theme.White);
        label.setHorizontalAlignment(JLabel.CENTER);
        // ---------- 나가기 라벨 끝

        // 나가기 버튼+라벨 배치 패널
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Theme.Red);
        buttonPanel.setPreferredSize(new Dimension(100, 31));

        buttonPanel.add(label, BorderLayout.CENTER);
        buttonPanel.add(exitButton, BorderLayout.WEST);
        // ---------- 나가기 버튼+라벨 배치 패널 끝

        // 그림진 둥근 나가기 버튼+라벨
        RoundedShadowPane roundedShadowPane = new RoundedShadowPane();
        roundedShadowPane.setBackground(Theme.Red);
        roundedShadowPane.setContentPane(buttonPanel);
        roundedShadowPane.setPreferredSize(new Dimension(100, 31));
        // ---------- 그림진 둥근 나가기 버튼+라벨 끝

        // BorderLayout CENTER 영역 차지를 위한 빈 패널
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Theme.Ultramarine);
        // ---------- 빈 패널 끝

        // 나가기 버튼+라벨 배치 패널 (좌로 밀착)
        JPanel buttonWrapper = new JPanel(new BorderLayout());
        buttonWrapper.setBackground(Theme.Ultramarine);
        buttonWrapper.setPreferredSize(new Dimension(screenWidth, 57));
        buttonWrapper.setBorder(new EmptyBorder(5, 5, 0, 0));

        buttonWrapper.add(emptyPanel, BorderLayout.CENTER);
        buttonWrapper.add(roundedShadowPane, BorderLayout.WEST);
        // ---------- 나가기 버튼+라벨 배치 패널 (좌로 밀착) 끝

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
