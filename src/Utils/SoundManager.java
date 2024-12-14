package Utils;

import javax.sound.sampled.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SoundManager {
    private BlockingQueue<byte[]> soundQueue;
    private AudioFormat format;
    private volatile boolean isPlaying;

    public SoundManager() {
        this.soundQueue = new LinkedBlockingQueue<>();
        this.format = new AudioFormat(44100, 16, 1, true, false); // 표준 PCM 오디오 포맷
        this.isPlaying = false;
    }

    public void addSoundChunk(byte[] chunk) {
        soundQueue.offer(chunk); // 큐에 데이터를 추가
    }

    public void startPlayback() {
        if (isPlaying) return; // 이미 재생 중이면 무시

        isPlaying = true;

        new Thread(() -> {
            try (SourceDataLine line = AudioSystem.getSourceDataLine(format)) {
                line.open(format);
                line.start();

                while (isPlaying) {
                    byte[] chunk = soundQueue.poll();
                    if (chunk != null) {
                        line.write(chunk, 0, chunk.length); // 데이터를 스트림에 쓰기
                    } else {
                        Thread.sleep(10); // 데이터가 없으면 잠시 대기
                    }
                }

                line.drain();
                line.stop();

            } catch (LineUnavailableException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stopPlayback() {
        isPlaying = false;
    }
}
