package Utils;

import ClassRoom.WithTalk;

import javax.swing.*;
import java.awt.*;

public class ExitModal {
    public static void showModalDialog(JFrame parentFrame) {
        JDialog modalDialog = new JDialog(parentFrame, "수업 종료 확인", true);  // 'true' makes it modal
        modalDialog.setSize(300, 200);
        modalDialog.setLocationRelativeTo(parentFrame);

        // Add content to the dialog
        JLabel label = new JLabel("현재 수업 중 입니다. 수업에서 나가시겠습니까?", SwingConstants.CENTER);
        JButton closeButton = new JButton("나가기");
        closeButton.addActionListener(e -> modalDialog.dispose());
        closeButton.addActionListener(e -> {
            parentFrame.dispose();
            // new LoginScreenGUI(); // 로그인 창으로 돌아가기 / 나가기
            WithTalk.main(new String[]{""}); // [임시]: 클라이언트 창으로 돌아가기
        });

        modalDialog.setLayout(new BorderLayout());
        modalDialog.add(label, BorderLayout.CENTER);
        modalDialog.add(closeButton, BorderLayout.SOUTH);

        modalDialog.setVisible(true);
    }
}
