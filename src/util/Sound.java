package util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/music/crossing_stones.wav");
        soundURL[1] = getClass().getResource("/sounds/sfx/sfx_pickup.wav");
        soundURL[2] = getClass().getResource("/sounds/sfx/sfx_unlock.wav");
        soundURL[3] = getClass().getResource("/sounds/sfx/sfx_locked.wav");
        soundURL[4] = getClass().getResource("/sounds/sfx/sfx_victory.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
}
