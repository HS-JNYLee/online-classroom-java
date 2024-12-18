package Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// 아이콘 출처 : https://www.figma.com/community/plugin/735098390272716381/iconify
public class Icons {
    private static int size = 31;
    public static ImageIcon bookmarkActiveIcon = setIcon("assets/icons/bookmark_active.png");
    public static ImageIcon bookmarkInactiveIcon = setIcon("assets/icons/bookmark_inactive.png") ;
    public static ImageIcon eraserActiveIcon = setIcon("assets/icons/eraser_active.png") ;
    public static ImageIcon eraserInactiveIcon = setIcon("assets/icons/eraser_inactive.png") ;
    public static ImageIcon penActiveIcon = setIcon("assets/icons/pen_active.png") ;
    public static ImageIcon penInactiveIcon = setIcon("assets/icons/pen_inactive.png") ;
    public static ImageIcon redPaletteIcon = createCircleIcon(Theme.Red);
    public static ImageIcon greenPaletteIcon = createCircleIcon(Theme.Green);
    public static ImageIcon bluePaletteIcon = createCircleIcon(Theme.Blue);
    public static ImageIcon exitIcon = setIcon("assets/icons/exit.png");
    public static ImageIcon userIcon = setIcon("assets/icons/user_icon.png");
    public static ImageIcon micOnIcon = setIcon("assets/icons/mic_on.png");
    public static ImageIcon micOffIcon = setIcon("assets/icons/mic_off.png");
    public static ImageIcon soundOnIcon = setIcon("assets/icons/sound_on.png");
    public static ImageIcon soundOffIcon = setIcon("assets/icons/sound_off.png");
    public static ImageIcon screenShareOnIcon = setIcon("assets/icons/screen_share_on.png");
    public static ImageIcon screenShareOffIcon = setIcon("assets/icons/screen_share_off.png");
    public static ImageIcon chatOnIcon = setIcon("assets/icons/chat_on.png");
    public static ImageIcon chatOffIcon = setIcon("assets/icons/chat_off.png");
    private static ImageIcon setIcon(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        image = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        icon.setImage(image);
        return icon;
    }

    public static ImageIcon createCircleIcon(Color color) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, size, size);

        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.setColor(color);
        g2d.fillOval(0, 0, size-3, size-3);
        g2d.dispose();

        return new ImageIcon(image);
    }
}
