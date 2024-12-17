package Utils;

import ClassRoom.WithTalk;

import javax.swing.*;
import java.awt.*;

public class ExitModalForUser {
    public static void showModalDialog(JFrame parentFrame) {
        JDialog modalDialog = new JDialog(parentFrame, "수업 종료 확인", true);
        modalDialog.setSize(300, 200);
        modalDialog.setLocationRelativeTo(parentFrame);

        // Add content to the dialog
        JLabel label = new JLabel("현재 수업 중 입니다. 수업에서 나가시겠습니까?", SwingConstants.CENTER);
        JButton closeButton = new JButton("나가기");
        closeButton.addActionListener(e -> modalDialog.dispose());
        closeButton.addActionListener(e -> {
            parentFrame.dispose();
            new WithTalk("127.0.0.1", 8080);
        });

        modalDialog.setLayout(new BorderLayout());
        modalDialog.add(label, BorderLayout.CENTER);
        modalDialog.add(closeButton, BorderLayout.SOUTH);

        modalDialog.setVisible(true);
    }
}
