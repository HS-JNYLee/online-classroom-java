package ClassRoom;

import Utils.PaletteButton;
import Utils.SoundManager;
import Utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

// Slider with playback control and bookmark support
public class BookmarkSlider extends JPanel {
    private final JSlider slider;
    private final List<BufferedImage> frameBuffer;
    private final List<Integer> bookmarks;
    private final VideoPanel videoPanel;
    private final JPanel markerPanel;
    private boolean isDragging = false;
    private PaletteButton bookmarkButton;
    private SoundManager soundManager;

    public void setLectureSoundManager(SoundManager lectureSoundManager) {
        this.soundManager = lectureSoundManager;
        soundManager.startPlayback(); // 재생 시작
    }

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

