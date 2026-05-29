import java.io.File;
import javax.sound.sampled.*;

public class SoundManager {

    // ============================================
    // PLAY SOUND EFFECT
    // ============================================
    public void play(String path) {

        try {

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(
                            new File(path)
                    );

            Clip clip = AudioSystem.getClip();

            clip.open(audio);
            clip.start();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // ============================================
    // LOOP MUSIC
    // ============================================
    Clip musicClip;

    public void loop(String path) {

        try {

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(
                            new File(path)
                    );

            musicClip = AudioSystem.getClip();
            musicClip.open(audio);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicClip.start();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // ============================================
    // STOP MUSIC
    // ============================================
    public void stop() {
        if (musicClip != null) {
            musicClip.stop();
        }
    }
}
