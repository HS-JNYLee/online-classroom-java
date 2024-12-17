package Threads;

import ClassRoom.ChatMsg;
import MainStudScreen.CommunicationCallbacks;
import User.User;

import javax.sound.sampled.*;
import java.util.Arrays;

public class SendMicSoundThread extends Thread {

    private CommunicationCallbacks sendMicCallback;
    private boolean running = true;
    private User user;
    private static final int THRESHOLD = 40; // 음성 인식의 임계값 (조정 필요)

    public SendMicSoundThread(User user, CommunicationCallbacks callback) {
        this.sendMicCallback = callback;
        this.user = user;
    }

    public void stopThread() {
        this.running = false;
    }

    @Override
    public void run() {
        try {
            // 오디오 포맷 설정
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            TargetDataLine line = AudioSystem.getTargetDataLine(format);
            line.open(format);
            line.start();

            SourceDataLine outputLine = AudioSystem.getSourceDataLine(format);
            outputLine.open(format);
            outputLine.start();

            byte[] buffer = new byte[1024]; // 버퍼 크기 설정
            while (running) {
                int bytesRead = line.read(buffer, 0, buffer.length); // 마이크에서 읽은 데이터 크기
                if (bytesRead > 0) {
                    double rms = calculateRMS(buffer); // 소리 크기 계산
                    if (rms > THRESHOLD) {
//                        System.out.println("음성 전송됨: " + bytesRead);

                        // 읽은 데이터 크기만큼 전송
                        byte[] soundData = Arrays.copyOf(buffer, bytesRead);
                        sendMicCallback.send(new ChatMsg(user.getId(), User.roleToString(user.getRole()), ChatMsg.MODE_MIC_SOUND, soundData));
                    }
//                    outputLine.write(buffer, 0, bytesRead);
                }
            }
            line.close(); // 라인 종료
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // RMS 값 계산 함수
    private double calculateRMS(byte[] buffer) {
        long sum = 0;
        for (int i = 0; i < buffer.length; i++) {
            sum += buffer[i] * buffer[i];
        }
        return Math.sqrt(sum / (double) buffer.length);
    }
}
