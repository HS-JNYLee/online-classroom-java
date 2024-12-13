package ClassRoom;

import Utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class VideoBookmarkApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Video Bookmark Feature");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Main panel for video and controls
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Video display panel
        VideoPanel videoPanel = new VideoPanel();
        mainPanel.add(videoPanel, BorderLayout.CENTER);

        // Playback control slider with bookmarks
        BookmarkSlider bookmarkSlider = new BookmarkSlider(videoPanel);
        mainPanel.add(bookmarkSlider, BorderLayout.SOUTH);

        // Bookmark Button
        JButton bookmarkButton = new JButton("Add Bookmark");
        bookmarkButton.addActionListener(e -> bookmarkSlider.addBookmark());
        frame.add(bookmarkButton, BorderLayout.NORTH);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Simulate incoming video frames
        new Thread(() -> simulateVideoFrames(videoPanel, bookmarkSlider)).start();
    }
    static int frameWidth = 462;
    static int frameHeight = 369;
    private static void simulateVideoFrames(VideoPanel videoPanel, BookmarkSlider bookmarkSlider) {
        try {
            int frameCount = 0;
            while (true) {
                BufferedImage frame = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = frame.createGraphics();
                g.setColor(new Color((frameCount * 10) % 255, (frameCount * 5) % 255, (frameCount * 3) % 255));
                g.fillRect(0, 0, frameWidth, frameHeight);
                g.setColor(Color.WHITE);
                g.drawString("Frame: " + frameCount, frameWidth/2, frameHeight/2);
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

// Panel for displaying video
class VideoPanel extends JPanel {
    private BufferedImage currentFrame;

    public void updateFrame(BufferedImage frame) {
        this.currentFrame = frame;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentFrame != null) {
            g.drawImage(currentFrame, 0, 0, getWidth(), getHeight(), null);
        }
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
                    videoPanel.updateFrame(frameBuffer.get(frameIndex));
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

    public void addFrame(BufferedImage frame) {
        if (frameBuffer.size() >= 300) {
            frameBuffer.remove(0);
        }
        frameBuffer.add(frame);
        slider.setMaximum(frameBuffer.size() - 1);
        if (!isDragging) {
            slider.setValue(frameBuffer.size() - 1);
        }
        updateMarkers();
    }

    public void addBookmark() {
        int currentFrame = slider.getValue();
        System.out.println("Adding bookmark at frame " + currentFrame);
        if (!bookmarks.contains(currentFrame)) {
            bookmarks.add(currentFrame);
            updateMarkers();
        }
    }

    private void updateMarkers() {
        markerPanel.removeAll();
        for (int bookmark : bookmarks) {
            JButton marker = new JButton("●");
            marker.setFont(new Font("Arial", Font.BOLD, 10));
            marker.setBounds(bookmark * markerPanel.getWidth() / slider.getMaximum(), 0, 10, 10);
            marker.addActionListener(e -> slider.setValue(bookmark));
            markerPanel.add(marker);
        }
        markerPanel.revalidate();
        markerPanel.repaint();
    }
}
