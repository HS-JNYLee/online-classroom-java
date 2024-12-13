package Utils;

import ClassRoom.DrawingPanel;

import javax.swing.*;
import java.awt.*;

public class PaletteColorButton extends PaletteButton {
    ImageIcon imageIcon;
    Color paletteColor;

    public PaletteColorButton(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        this.setIcon(imageIcon);
        setStyle();
        setOnClickEvent();
    }

    public PaletteColorButton(ImageIcon imageIcon, Color color) {
        this(imageIcon);
        this.paletteColor = color;
    }

    DrawingPanel dp;

    public void setDrawingPanel(DrawingPanel dp) {
        this.dp = dp;
    }

    @Override
    public void onClick() {
        dp.setColor(paletteColor);
    }
}
