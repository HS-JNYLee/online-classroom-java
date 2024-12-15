package Threads;

import ClassRoom.ChatMsg;
import MainStudScreen.CommunicationCallbacks;
import User.User;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SendScreenThread extends Thread{
    private CommunicationCallbacks sendScreenCallback;
    private User user;
    private boolean running = true;

    public SendScreenThread(User user, CommunicationCallbacks callback) {
        this.sendScreenCallback = callback;
        this.user = user;
    }

    public void stopThread(){
        this.running = false;
    }

    private int cnt = 0;

    @Override
    public void run() {
        try {
            Robot robot = new Robot();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenRect = new Rectangle(screenSize);

            while (running){
                System.out.println("sec : " + cnt++);
                BufferedImage screenImage = robot.createScreenCapture(screenRect);
                int newWidth = 600;
                int newHeight = 340;

                // 이미지 크기 변경
                Image scaledImage = screenImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                // BufferedImage로 변환
                BufferedImage scaledBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = scaledBufferedImage.createGraphics();
                g2d.drawImage(scaledImage, 0, 0, null);
                g2d.dispose();

                sendScreenCallback.send(new ChatMsg(user.getId(), User.roleToString(user.getRole()), ChatMsg.MODE_SHARED_SCREEN, scaledBufferedImage));
                Thread.sleep(100);
            }

        } catch (AWTException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
