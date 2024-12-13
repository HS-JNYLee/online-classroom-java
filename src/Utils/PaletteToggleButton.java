package Utils;

import javax.swing.*;

public class PaletteToggleButton extends PaletteButton {
    ImageIcon activeImageIcon;
    ImageIcon inactiveImageIcon;
    Boolean isActive = false;

    public PaletteToggleButton(ImageIcon activeImageIcon, ImageIcon inactiveImageIcon) {
        this.activeImageIcon = activeImageIcon;
        this.inactiveImageIcon = inactiveImageIcon;
        setStyle();
        setOnClickEvent();
        setInactiveImageIcon();
    }

    @Override
    public void onClick() {
        isActive = !isActive;
        if (isActive) {
            active();
        } else {
            inactive();
        }
    }

    @Override
    protected void setStyle() {
        super.setStyle();
        setInactiveImageIcon();
    }

    public void active() {
        setActiveImageIcon();
        isActive = true;
    }

    public void inactive() {
        setInactiveImageIcon();
        isActive = false;
    }

    private void setInactiveImageIcon() {
        this.setIcon(inactiveImageIcon);
    }

    private void setActiveImageIcon() {
        this.setIcon(activeImageIcon);
    }
}
