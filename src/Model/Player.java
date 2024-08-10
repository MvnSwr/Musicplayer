package Model;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;

public interface Player{
    void start();
    void stop();
    void open(AudioInputStream stream) throws LineUnavailableException, IOException;
    boolean isOpen();
    void close();
    long getMicrosecondPosition();
    long getMicrosecondLength();
    boolean isActive();
}