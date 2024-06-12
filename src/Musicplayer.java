import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Musicplayer{
    private static Clip clip = null;

    public static void loadNext(String path) throws UnsupportedAudioFileException,IOException,LineUnavailableException{
        File file = new File(path);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
    }

    static void start(){
        clip.start();
    }

    static void stop(){
        clip.stop();
    }

    static void flush(){ // Flush erfüllt nicht das, was ich wollte
        clip.close();
    }

    public static void test(){
        try {
            loadNext("C:/Users/marvi/OneDrive/Laptop/Programmieren/Musicplayer/Music/Techno/SexyBitchRemix.wav");
            clip.start();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clip.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
