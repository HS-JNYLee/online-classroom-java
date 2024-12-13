package Utils;

import javax.swing.*;

public class PaletteColorButton extends PaletteButton {
    ImageIcon imageIcon;

    public PaletteColorButton(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        this.setIcon(imageIcon);
        setStyle();
        setOnClickEvent();
    }

    @Override
    public void onClick() {}
}
