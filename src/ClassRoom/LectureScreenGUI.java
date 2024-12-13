package ClassRoom;

import Utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class LectureScreenGUI extends JFrame {
    private final int screenWidth = 960;
    private final int screenHeight = 540;
    private PaletteButton b_pen;
    private PaletteButton b_eraser;
    private PaletteButton b_bookmark;
    private DrawingPanel drawingPanel;

    LectureScreenGUI() {
        super("수업 중...");

        buildGUI();

        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void buildGUI() {
        drawingPanel = new DrawingPanel(); // b4, b5, b6 참조

        // 팔레트 패널
        JPanel palettePanel = createPalettePanel();
        // 앱 제어 패널
        JPanel controlPanel = createControlPanel();
        // 나가기 버튼 패널
        JPanel buttonPanel = createButtonPanel();

        // 수업 화면 패널
        JPanel screenPanel = createScreenPanel();
        screenPanel.setPreferredSize(new Dimension(842, 631));
        drawingPanel.setButtons(b_pen, b_eraser);
        drawingPanel.setLayout(new BorderLayout());
        drawingPanel.setOpaque(false);
        drawingPanel.setPreferredSize(new Dimension(842, 631));
        drawingPanel.add(screenPanel, BorderLayout.CENTER);

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
        contentPanel.add(drawingPanel, BorderLayout.CENTER);
        // ---------- 전체 패널 끝
        add(contentPanel);
    }

    // 영상 패널
    public JPanel createScreenPanel() {
        // 영상 패널
        VideoPanel videoPanel = new VideoPanel();

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) { // 우클릭 감지
                    videoPanel.showEmoji(e.getPoint());
                }
            }
        });
        // 슬라이더 - 타임라인 조정
        BookmarkSlider bookmarkSlider = new BookmarkSlider(videoPanel);
        bookmarkSlider.setBookmarkButton(b_bookmark);

        // (임시) 영상 녹화 테스트용
        new Thread(() -> simulateVideoFrames(bookmarkSlider)).start();
        
        // 영상+슬라이더 패널
        JPanel screenPanel = new JPanel(new BorderLayout());
        screenPanel.add(videoPanel, BorderLayout.CENTER);
        screenPanel.add(bookmarkSlider, BorderLayout.SOUTH);
        // ---------- 비디오+슬라이더 패널 끝
        
        // 둥근 스크린 패널
        RoundedPane roundedPane = new RoundedPane();
        roundedPane.setBackground(Theme.Blue);
        roundedPane.setContentPane(screenPanel);
        // ---------- 둥근 스크린 패널 끝

        return roundedPane;
    }

    JButton b_save;
    // 팔레트 패널 함수
    public JPanel createPalettePanel() {
        // 팔레트 버튼 모음
        // 북마크
        b_bookmark = new PaletteToggleButton(Icons.bookmarkInactiveIcon, Icons.bookmarkActiveIcon);
        // 지우개
        b_eraser = new PaletteToggleButton(Icons.eraserInactiveIcon, Icons.eraserActiveIcon);
        // 연필
        b_pen = new PaletteToggleButton(Icons.penInactiveIcon, Icons.penActiveIcon);
        // 연필 색 - 빨강
        PaletteColorButton b_red = new PaletteColorButton(Icons.redPaletteIcon, Theme.Red);
        b_red.setDrawingPanel(drawingPanel);
        // 연필 색 - 초록
        PaletteColorButton b_green = new PaletteColorButton(Icons.greenPaletteIcon, Theme.Green);
        b_green.setDrawingPanel(drawingPanel);
        // 연픽 색 - 파랑
        PaletteColorButton b_blue = new PaletteColorButton(Icons.bluePaletteIcon, Theme.Blue);
        b_blue.setDrawingPanel(drawingPanel);

        JPanel palettePanel = new JPanel();
        palettePanel.setBackground(Theme.White);
        palettePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        palettePanel.setPreferredSize(new Dimension(164, screenHeight));
        palettePanel.setLayout(new GridLayout(7, 1, 10, 10));
        b_save = new JButton();
        palettePanel.add(b_bookmark);
        palettePanel.add(b_eraser);
        palettePanel.add(b_pen);
        palettePanel.add(b_red);
        palettePanel.add(b_green);
        palettePanel.add(b_blue);
        palettePanel.add(b_save);

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

    // 제어 패널
    public JPanel createControlPanel() {
        JLabel label = new JLabel("Control Panel");
        label.setForeground(Color.WHITE);
        label.setSize(screenWidth, screenHeight);

        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBackground(Theme.Ultramarine);
        controlPanel.setPreferredSize(new Dimension(screenWidth, 68));

        controlPanel.add(label);

        return controlPanel;
    }

    // 나가기 버튼 배치 패널
    public JPanel createButtonPanel() {
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

    private void simulateVideoFrames(BookmarkSlider bookmarkSlider) {
        try {
            int frameCount = 0;
            while (true) {
                BufferedImage frame = new BufferedImage(600, 340, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = frame.createGraphics();
                g.setColor(new Color((frameCount * 10) % 255, (frameCount * 5) % 255, (frameCount * 3) % 255));
                g.fillRect(0, 0, 600, 340);
                g.setColor(Color.WHITE);
                g.drawString("Frame: " + frameCount, 250, 170);
                g.dispose();

                SwingUtilities.invokeLater(() -> {
                    bookmarkSlider.setDrawingPanel(drawingPanel);
                    bookmarkSlider.addFrame(frame);
                    bookmarkSlider.setButton(b_save);
                });
                frameCount++;
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

