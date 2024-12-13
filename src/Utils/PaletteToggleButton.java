package Utils;

import javax.swing.*;

public class PaletteToggleButton extends PaletteButton {
    ImageIcon activeImageIcon;
    ImageIcon inactiveImageIcon;

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

    @Override
    public void active() {
        super.active();
        setActiveImageIcon();
    }

    @Override
    public void inactive() {
        super.inactive();
        setInactiveImageIcon();
    }

    private void setInactiveImageIcon() {
        this.setIcon(inactiveImageIcon);
    }

    private void setActiveImageIcon() {
        this.setIcon(activeImageIcon);
    }
}
