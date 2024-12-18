package Utils;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

public class TailRoundedPane extends RoundedPane {
    public enum TailPosition {
        TOP_LEFT, BOTTOM_LEFT, TOP_RIGHT, BOTTOM_RIGHT
    }

    private TailPosition tailPosition = TailPosition.TOP_LEFT;
    public TailRoundedPane(TailPosition tailPosition) {
        super();
        this.tailPosition = tailPosition;
    }

    @Override
    public void setBackgroundColor(Color backgroundColor) {
        super.setBackgroundColor(backgroundColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Theme.Darkblue);
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
        double arc = cornerRadius;

        GeneralPath path = new GeneralPath();

        // Create shape based on TailPosition
        if (tailPosition == TailPosition.TOP_LEFT) {
            path.moveTo(0, 0);
            path.lineTo(width - arc, 0);
            path.quadTo(width, 0, width, arc);
            path.lineTo(width, height - arc);
            path.quadTo(width, height, width - arc, height);
            path.lineTo(arc, height);
            path.quadTo(0, height, 0, height - arc);
            path.lineTo(0, 0);
        } else if (tailPosition == TailPosition.BOTTOM_LEFT) {
            path.moveTo(arc, 0);
            path.quadTo(0, 0, 0, arc);
            path.lineTo(0, height);
            path.lineTo(width - arc, height);
            path.quadTo(width, height, width, height - arc);
            path.lineTo(width, arc);
            path.quadTo(width, 0, width - arc, 0);
            path.lineTo(arc, 0);
        } else if (tailPosition == TailPosition.TOP_RIGHT) {
            path.moveTo(0, 0);
            path.lineTo(width, 0);
            path.lineTo(width, height - arc);
            path.quadTo(width, height, width - arc, height);
            path.lineTo(arc, height);
            path.quadTo(0, height, 0, height - arc);
            path.quadTo(0, 0, arc, 0);
        } else if (tailPosition == TailPosition.BOTTOM_RIGHT) {
            path.moveTo(arc, 0);
            path.quadTo(0, 0, 0, arc);
            path.lineTo(0, height - arc);
            path.quadTo(0, height, arc, height);
            path.lineTo(width, height);
            path.lineTo(width, arc);
            path.quadTo(width, 0, width - arc, 0);
            path.lineTo(arc, 0);
        }

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

        g2d.setColor(this.getBackground());
        g2d.fill(path);
        g2d.translate(insets.left, insets.top);
        g2d.dispose();
    }
}
