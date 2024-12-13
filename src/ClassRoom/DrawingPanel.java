package ClassRoom;

import Utils.PaletteButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

// Panel for drawing annotations
public class DrawingPanel extends JPanel {
    private final BufferedImage drawingImage;
    private Color color;
    private PaletteButton penButton;
    private PaletteButton eraserButton;
    public void setColor(Color color) {
        this.color = color;
    }

    public void setButtons(PaletteButton penButton, PaletteButton eraserButton) {
        this.penButton = penButton;
        this.eraserButton = eraserButton;
    }

    public DrawingPanel() {
        drawingImage = new BufferedImage(600, 340, BufferedImage.TYPE_INT_ARGB);
        MouseAdapter adapter = new MouseAdapter() {
            private Point prevPoint;

            @Override
            public void mousePressed(MouseEvent e) {
                prevPoint = e.getPoint();
                if (eraserButton.getActive()) {
                    erase(e.getPoint());
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (penButton.getActive()) {
                    drawLine(e.getPoint(), color, prevPoint); // 연필은 빨간색
                } else if (eraserButton.getActive()) {
                    erase(e.getPoint()); // 지우개 기능
                }
                prevPoint = e.getPoint();
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    private void erase(Point point) {
        Graphics2D g = drawingImage.createGraphics();
        g.setComposite(AlphaComposite.Clear); // 투명하게 지우기
        int eraserSize = 20;
        g.fillRect(point.x - eraserSize / 2, point.y - eraserSize / 2, eraserSize, eraserSize);
        g.dispose();
        repaint();
    }

    private void drawLine(Point currPoint, Color color, Point prevPoint) {
        Graphics2D g = drawingImage.createGraphics();
        g.setColor(color);
        g.setStroke(new BasicStroke(2));
        g.drawLine(prevPoint.x, prevPoint.y, currPoint.x, currPoint.y);
        g.dispose();
        repaint();
    }

    public BufferedImage getDrawingImage() {
        return drawingImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drawingImage, 0, 0, null);
    }
}
