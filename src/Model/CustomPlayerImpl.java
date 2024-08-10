package Model;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class CustomPlayerImpl implements Player{
    private Clip clip;

    public CustomPlayerImpl() throws LineUnavailableException {
        this.clip = AudioSystem.getClip();
    }

    @Override
    public void start() {
        clip.start();
    }

    @Override
    public void stop() {
        clip.stop();
    }

    @Override
    public void open(AudioInputStream stream) throws LineUnavailableException, IOException {
        clip.open(stream);
    }

    @Override
    public boolean isOpen() {
        return clip.isOpen();
    }

    @Override
    public void close() {
        clip.close();
    }

    @Override
    public long getMicrosecondPosition() {
        return clip.getMicrosecondPosition();
    }

    @Override
    public long getMicrosecondLength() {
        return clip.getMicrosecondLength();
    }
    
    @Override
    public boolean isActive(){
        return clip.isActive();
    }
}