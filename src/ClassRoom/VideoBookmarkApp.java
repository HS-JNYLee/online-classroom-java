package ClassRoom;

import Utils.PaletteButton;
import Utils.Theme;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

// Panel for displaying video
class VideoPanel extends JPanel {
    private BufferedImage currentFrame;
    private ImageIcon emojiIcon;
    private Point position;
    @Override
    public void setLayout(LayoutManager mgr) {
        super.setLayout(null);
    }

    public void updateFrame(BufferedImage frame) {
        this.currentFrame = frame;
        if (emojiIcon != null) {
            BufferedImage combined = new BufferedImage(currentFrame.getWidth(), currentFrame.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = combined.createGraphics();
            g.drawImage(currentFrame, 0, 0, null);
            Image icon = emojiIcon.getImage();
            g.drawImage(emojiIcon.getImage(), position.x - 35, position.y - 35, 30, 30,null);
            g.dispose();
            this.currentFrame = combined;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentFrame != null) {
            g.drawImage(currentFrame, 0, 0, getWidth(), getHeight(), null);
        }
    }

    // 이모지를 표시하고 일정 시간 후 제거
    public void showEmoji(Point location) {
        // 이모지 라벨 생성
        System.out.println("Showing emoji");
        emojiIcon = new ImageIcon("./assets/icons/emoji.png");
        position = location;
        // 일정 시간 후 제거 (500ms)
        Timer timer = new Timer(500, e -> {
            emojiIcon = null;
        });
        timer.setRepeats(false); // 타이머 반복하지 않음
        timer.start();
    }

}

// Slider with playback control and bookmark support
class BookmarkSlider extends JPanel {
    private final JSlider slider;
    private final List<BufferedImage> frameBuffer;
    private final List<Integer> bookmarks;
    private final VideoPanel videoPanel;
    private final JPanel markerPanel;
    private boolean isDragging = false;
    private PaletteButton bookmarkButton;

    public List<Integer> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarkButton(PaletteButton bookmarkButton) {
        this.bookmarkButton = bookmarkButton;
        this.bookmarkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int frameIndex = slider.getValue();
                toggleBookmark(frameIndex);
            }
        });
    }

    // 저장 버튼 동작
    public void setButton(JButton button) {
        button.addActionListener(_ -> {
            int frameIndex = slider.getValue();
            BufferedImage combinedImage = combineImages(frameBuffer.get(frameIndex), drawingPanel.getDrawingImage());
            frameBuffer.set(frameIndex, combinedImage);
            drawingPanel.removePaint();
            videoPanel.updateFrame(frameBuffer.get(frameIndex));
        });
    }

    public BookmarkSlider(VideoPanel videoPanel) {
        this.videoPanel = videoPanel;
        this.frameBuffer = new ArrayList<>();
        this.bookmarks = new ArrayList<>();

        setLayout(new BorderLayout());

        // Slider setup
        slider = new JSlider(0, 0, 0);
        //slider.setEnabled(false);
        slider.addChangeListener(e -> {
            if (slider.getValueIsAdjusting()) {
                isDragging = true;
                int frameIndex = slider.getValue();
                if (frameIndex < frameBuffer.size()) {
                    BufferedImage combinedImage = combineImages(frameBuffer.get(frameIndex), drawingPanel.getDrawingImage());
                    videoPanel.updateFrame(combinedImage);
                    displayBookmark(frameIndex);
                }
            } else {
                isDragging = false;
            }
        });
        slider.setBackground(Theme.Blue);

        // Marker panel for bookmarks
        markerPanel = new JPanel(null);
        markerPanel.setOpaque(false);
        setBackground(Theme.Blue);
        add(slider, BorderLayout.CENTER);
        add(markerPanel, BorderLayout.SOUTH);
    }

    public void toggleBookmark(int frameIndex) {
        if (bookmarks.contains(frameIndex)) {
            System.out.println("Active");
            bookListModel.remove(bookmarks.indexOf(frameIndex));
            bookmarks.remove(bookmarks.indexOf(frameIndex));
        } else {
            System.out.println("Inactive");
            bookmarks.add(frameIndex);
            bookListModel.removeAllElements();
            int count = 1;
            for (int bookmark : bookmarks) {
                bookListModel.addElement(new Object[]{
                        "Bookmark " + count,
                        new ImageIcon("./assets/icons/bookmark_active.png"),
                        bookmark
                });
                count++;
            }
            System.out.println(bookmarks);
        }
    }

    public void displayBookmark(int frameIndex) {
        if (bookmarks.contains(frameIndex)) {
            bookmarkButton.active();
        } else {
            bookmarkButton.inactive();
        }
    }

    public void addFrame(BufferedImage frame) {
        BufferedImage combinedImage = frame;
        int frameIndex = slider.getValue();
        if (frameBuffer.size() >= 36000) { // 0.1 단위로 36000장(약 1시간 분량)
            frameBuffer.removeFirst();
        }
        frameBuffer.add(frame);
        slider.setMaximum(frameBuffer.size() - 1);
        // 최신까지 당긴다면, 라이브로 유지
        if (frameIndex == frameBuffer.size() - 2) { // 업데이트 직전 프레임에 위치한다면,
            combinedImage = combineImages(frameBuffer.get(frameIndex), drawingPanel.getDrawingImage());
            slider.setValue(frameBuffer.size() - 1); // 최신 프레임 상태로 유지
            videoPanel.updateFrame(combinedImage); // 최신 프레임으로 업데이트
            displayBookmark(frameIndex);
        }
    }

    // 북마크 히스토리 업데이트, 조회를 위한 메소드
    private  DefaultListModel<Object[]> bookListModel;
    public void setBookListModel(DefaultListModel<Object[]> bookListModel) {
        this.bookListModel = bookListModel;
    }

    public void setSliderValue(int value) {
        slider.setValue(value);
        videoPanel.updateFrame(frameBuffer.get(value));
        displayBookmark(value);
    }
    // ---------- 북마크 히스토리 메소드 끝
    private DrawingPanel drawingPanel;
    public void setDrawingPanel(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    private BufferedImage combineImages(BufferedImage videoFrame, BufferedImage drawing) {
        BufferedImage combined = new BufferedImage(videoFrame.getWidth(), videoFrame.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();
        g.drawImage(videoFrame, 0, 0, null);
        g.drawImage(drawing, 0, 0, null);
        g.dispose();
        return combined;
    }
}

