package Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class PaletteButton extends JButton implements ActionListener {
    Boolean isActive = false;

    public Boolean getActive() {
        return isActive;
    }

    public PaletteButton() {
    }

    public JButton getButton() {
        return this;
    }

    public void setOnClickEvent() {
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClick();
                getButton().repaint();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        onClick();
        this.repaint();
    }

    protected void setStyle() {
        this.setBackground(Theme.White);
        this.setBorderPainted(false);
    }

    public abstract void onClick();

    public void active() {
        isActive = true;
    }

    public void inactive() {
        isActive = false;
    }
}
