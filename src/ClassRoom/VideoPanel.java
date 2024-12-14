package ClassRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// Panel for displaying video
public class VideoPanel extends JPanel {
    private BufferedImage currentFrame;
    private ImageIcon emojiIcon;
    private Point position;
    @Override
    public void setLayout(LayoutManager mgr) {
        super.setLayout(null);
    }

    public BufferedImage getCurrentFrame() {
        return currentFrame;
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
