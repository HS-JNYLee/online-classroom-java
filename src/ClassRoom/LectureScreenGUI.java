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
    private BookmarkSlider bookmarkSlider;
    private long threadSleep = 50;
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
        JPanel emptyPanel = createBookmarkHistory();
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

    // 북마크 히스토리 함수
    public JPanel createBookmarkHistory() {
        JPanel emptyWestPanel = new JPanel();
        emptyWestPanel.setPreferredSize(new Dimension(10, screenHeight));
        emptyWestPanel.setBackground(Theme.Ultramarine);
        JPanel emptyEastPanel = new JPanel();
        emptyEastPanel.setPreferredSize(new Dimension(10, screenHeight));
        emptyEastPanel.setBackground(Theme.Ultramarine);
        JPanel bookmarkHistoryPanel = new JPanel(new BorderLayout());

        JLabel bookmarkHistoryTitle = new JLabel("북마크 리스트");
        bookmarkHistoryTitle.setHorizontalAlignment(JLabel.CENTER);
        bookmarkHistoryTitle.setFont(new Font("돋움", Font.BOLD, 16));
        bookmarkHistoryTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        bookmarkHistoryTitle.setForeground(Theme.White);

        bookmarkHistoryPanel.add(bookmarkHistoryTitle, BorderLayout.NORTH);

        // JList에 사용할 모델 설정
        DefaultListModel<Object[]> listModel = new DefaultListModel<>();
        bookmarkSlider.setBookListModel(listModel);

        // JList 생성
        JList<Object[]> bookmarkList = new JList<>(listModel);

        // 커스텀 셀 렌더러 설정 (아이콘과 텍스트를 함께 표시)
        bookmarkList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(new EmptyBorder(5, 5, 5, 5));

            String text = (String) value[0];
            ImageIcon icon = (ImageIcon) value[1];
            int bookmarkId = (int) value[2];

            // 텍스트와 아이콘을 패널에 추가
            icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(text, icon, JLabel.LEFT);
            panel.add(label, BorderLayout.CENTER);

            // 선택된 항목일 경우 배경색 변경
            if (isSelected) {
                panel.setBackground(Color.LIGHT_GRAY);
                bookmarkSlider.setSliderValue(bookmarkId);
            } else {
                panel.setBackground(Color.WHITE);
            }

            return panel;
        });
        // JList를 JScrollPane에 넣기
        JScrollPane scrollPane = new JScrollPane(bookmarkList);
        //


        bookmarkHistoryPanel.add(scrollPane, BorderLayout.CENTER);
        bookmarkHistoryPanel.setBackground(Theme.Ultramarine);

        JPanel bookmarkHistory = new JPanel(new BorderLayout());
        bookmarkHistory.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        bookmarkHistory.setPreferredSize(new Dimension(164, screenHeight));
        bookmarkHistory.add(emptyWestPanel, BorderLayout.WEST);
        bookmarkHistory.add(bookmarkHistoryPanel, BorderLayout.CENTER);
        bookmarkHistory.add(emptyEastPanel, BorderLayout.EAST);
        return bookmarkHistory;
    }
    private VideoPanel videoPanel;
    // 영상 패널
    public JPanel createScreenPanel() {
        // 영상 패널
        videoPanel = new VideoPanel();

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) { // 우클릭 감지
                    videoPanel.showEmoji(e.getPoint());
                    sendObserver.send(e.getPoint().x, e.getPoint().y);
                }
            }
        });
        // 슬라이더 - 타임라인 조정
        bookmarkSlider = new BookmarkSlider(videoPanel);
        bookmarkSlider.setBookmarkButton(b_bookmark);
        soundManager = new SoundManager();
        bookmarkSlider.setLectureSoundManager(soundManager);

        // (임시) 소리 테스트용
        simulateAudioStream();
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
        palettePanel.setPreferredSize(new Dimension(100, screenHeight));
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
        paletteWrapper.setPreferredSize(new Dimension(100, screenHeight));

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
        PaletteButton exitButton = new PaletteColorButton(Icons.exitIcon) {
            @Override
            public void onClick() {
                ExitModal.showModalDialog(LectureScreenGUI.this);
            }
        };
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

    private BufferedImage nowFrame;
    public void getImages(BufferedImage icon) {
        nowFrame = icon;
    }
    private byte[] audioChunk;
    public void getAudioChunk(byte[] audioChunk) {
        this.audioChunk = audioChunk;
    }

    private SoundManager soundManager;
    private void simulateAudioStream() {
        new Thread(() -> {
            while (true) {
                // 임시 소리 데이터 생성 (실제 서버에서 받아오는 방식으로 대체)
                if (audioChunk != null){
                    byte[] soundChunk = audioChunk;
                    soundManager.addSoundChunk(audioChunk);
                }

                try {
                    Thread.sleep(threadSleep); // 0.1초 간격으로 추가
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                if (nowFrame != null) {
                    g.drawImage(nowFrame, 0, 0, null);
                }
                g.dispose();

                SwingUtilities.invokeLater(() -> {
                    bookmarkSlider.setDrawingPanel(drawingPanel);
                    bookmarkSlider.addFrame(frame);
                    bookmarkSlider.setButton(b_save);
                });
                frameCount++;
                Thread.sleep(threadSleep);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private SendObserver sendObserver;
    void setSendObserver(SendObserver sendObserver) {
        this.sendObserver = sendObserver;
    }
    private Point point;
    public void setPoint(Point point) {
        videoPanel.showEmoji(point);
        this.point = point;
    }
}

