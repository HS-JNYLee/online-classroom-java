package Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPane extends JPanel {

    private double cornerRadius = 20;
    private int shadowSize = 0;
    private Color backgroundColor = Theme.Ultramarine;
    private Color shadowColor = Color.BLACK;
    private float shadowAlpha = 0.25f;
    private JComponent contentPane;

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public RoundedPane() {
        setOpaque(false);
        int shadowSize = getShadowSize();
        int cornerRadius = (int) getCornerRadius() / 4;
        setBorder(new EmptyBorder(
                shadowSize + cornerRadius,
                shadowSize + cornerRadius,
                shadowSize + cornerRadius + shadowSize,
                shadowSize + cornerRadius + shadowSize
        ));
        setLayout(new BorderLayout());
        add(getContentPane());
    }

    public JComponent getContentPane() {
        if (contentPane == null) {
            contentPane = new JPanel();
        }
        return contentPane;
    }

    public void setContentPane(JComponent contentPane) {
        if (this.contentPane != null) {
            remove(this.contentPane);
        }
        this.contentPane = contentPane;
        this.setBackground(this.contentPane.getBackground());
        add(this.contentPane);
    }

    public double getCornerRadius() {
        return cornerRadius;
    }

    public int getShadowSize() {
        return shadowSize;
    }

    public Color getShadowColor() {
        return shadowColor;
    }

    public float getShadowAlpha() {
        return shadowAlpha;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        double cornerRadius = getCornerRadius();
        double cornerInsets = cornerRadius / 4d;

        int width = getWidth();
        int height = getHeight();
        Insets insets = getInsets();
        insets.left -= cornerInsets;
        insets.right -= cornerInsets;
        insets.top -= cornerInsets;
        insets.bottom -= cornerInsets;

        width -= insets.left + insets.right;
        height -= insets.top + insets.bottom;

        RoundRectangle2D border = new RoundRectangle2D.Double(0, 0, width, height, cornerRadius, cornerRadius);
        ImageUtilities.applyQualityRenderingHints(g2d);
        g2d.drawImage(
                ImageUtilities.applyShadow(
                        border, getShadowSize(),
                        getBackground(),
                        getShadowColor(),
                        getShadowAlpha()
                ),
                insets.left,
                insets.top,
                this
        );
        g2d.setColor(Color.DARK_GRAY);
        g2d.translate(insets.left, insets.top);
        g2d.draw(border);
        g2d.dispose();
    }

}