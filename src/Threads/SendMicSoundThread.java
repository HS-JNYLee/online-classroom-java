package Threads;

import ClassRoom.ChatMsg;
import MainStudScreen.CommunicationCallbacks;
import User.User;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.SourceDataLine;

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
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false); // 샘플링 주파수 16kHz, 16-bit, mono
            TargetDataLine line = AudioSystem.getTargetDataLine(format);
            line.open(format);
            line.start();

            // 출력용 SourceDataLine 설정
            SourceDataLine outputLine = AudioSystem.getSourceDataLine(format);
            outputLine.open(format);
            outputLine.start();

            byte[] buffer = new byte[1024];
            while (running) {
                int bytesRead = line.read(buffer, 0, buffer.length); // 마이크에서 읽은 데이터
                if (bytesRead > 0) {
                    // 소리의 크기 계산 (RMS 방식)
                    double rms = calculateRMS(buffer);

                    // 소리의 크기가 임계값을 넘으면 전송
                    if (rms > THRESHOLD) {
                        System.out.println("음성 전송됨 : " + bytesRead + buffer.length);
                        sendMicCallback.send(new ChatMsg(user.getId(), User.roleToString(user.getRole()), ChatMsg.MODE_MIC_SOUND, buffer));
                    }

                    // 마이크 입력 소리 바로 출력
//                    outputLine.write(buffer, 0, bytesRead);
                }
//                Thread.sleep(100); // 잠시 대기 (너무 짧은 간격으로 읽지 않도록 조절)
            }
            outputLine.drain(); // 남은 데이터 전부 출력
            outputLine.close(); // 출력 라인 종료

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
