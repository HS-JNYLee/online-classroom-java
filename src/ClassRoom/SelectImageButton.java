package ClassRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class SelectImageButton {
    JButton button;
    private Image image = null;

    SelectImageButton(JButton button) {
        this.button = button;
    }

    public SelectImageButton() {
        button = buildButton();
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    private JButton buildButton() {
        return new JButton() {

            @Override
            protected void paintComponent(Graphics g) {
                // 기본 배경 처리 (잔상 제거)
                super.paintComponent(g);

                // 버튼의 배경을 동그란 모양으로 채움
                Graphics2D g2 = (Graphics2D) g;

                if (image != null) {
                    ImageIcon icon = new ImageIcon(image);
                    int width = getWidth();
                    int height = getHeight();
                    int size = Math.min(width, height); // 버튼 크기 기준으로 원 크기 설정

                    // 원형 모양의 클리핑 영역 설정
                    g2.setClip(new Ellipse2D.Float(0, 0, size, size));

                    // 이미지를 원형 영역 안에 맞게 그리기
                    g2.drawImage(icon.getImage(), (width - size) / 2, (height - size) / 2, size, size, this);
                    setIcon(null);
                }
                if (image == null) {
                    g2.setColor(new Color(0xD7DBDC));
                    g2.fillOval(0, 0, getWidth(), getHeight());
                    // 이미지가 없을 경우 기본 십자가 표시
                    g2.setColor(new Color(0x1B74E7));
                    int padding = 5; // 십자가의 여백
                    int centerX = getWidth() / 2;
                    int centerY = getHeight() / 2;

                    // 수직 선
                    g2.fillRect(centerX - 2, 5 * padding, 4, getHeight() - 10 * padding);

                    // 수평 선
                    g2.fillRect(5 * padding, centerY - 2, getWidth() - 10 * padding, 4);
                }
            }

            @Override
            protected void paintBorder(Graphics g) {}

            @Override
            public Dimension getPreferredSize() {
                // 버튼의 기본 크기를 설정
                return new Dimension(100, 100);
            }
        };
    }

    public void setImage(Image newImage) {
        this.image = newImage;
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setIcon(new ImageIcon(newImage));

        button.repaint();
    }

    JButton getButton() {
        return button;
    }
}
