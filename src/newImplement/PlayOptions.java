package newImplement;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlayOptions {
    class UpdateThread extends Thread{
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                PlayOptions.getPlayOptions().update();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        public void stopRunning() {
            running = false;
        }
    }

    private List<Playlist> playlists;
    private Playlist currentPlayList;
    private static PlayOptions playOptions;
    private String directory; // full path to the musicdirectories incl. "/"
    private Clip clip;
    private Boolean isOnShuffle;
    private UpdateThread updateThread;

    public PlayOptions(){
        setPlaylists();
    }

    public static PlayOptions getPlayOptions(){
        return playOptions == null ? playOptions = new PlayOptions() : playOptions;
    }

    //Läuft über Buttonaufruf
    //ALLES TODO
    public void startSong(){
        updateThread = new UpdateThread();
        updateThread.run();
        clip.start();
    }

    public void stopSong() throws InterruptedException{
        clip.stop();
        updateThread.stopRunning();
        updateThread.join();
    }
    public void repeatSong(){}
    public void lastSong(){}
    public void skipSong(){}
    public void clearCache(){}

    //Ende ToDo
    public void shuffleSwitch(){
        isOnShuffle = !isOnShuffle;
    }

    public void setDirectory(String directory) throws noDirectoryException{ //Soll über einen Button laufen
        File directoryFile = new File(directory);
        if (directoryFile.exists() && directoryFile.isDirectory()) {
            this.directory = directory;  
        }else{
            throw new noDirectoryException();
        }
    } // "C:/Users/marvi/OneDrive/Laptop/Programmieren/Musicplayer/Music/"

    // Ende "Läuft pber Buttonaufruf"

    public String getDirectory(){
        return directory;
    }

    private void loadSong() throws UnsupportedAudioFileException,IOException,LineUnavailableException{
        AudioInputStream audioStream = 
                         isOnShuffle == true ? AudioSystem.getAudioInputStream(new File(currentPlayList.getRandomSong().title())) 
                                             : AudioSystem.getAudioInputStream(new File(currentPlayList.getNextSongInLine().title()));
        clip = AudioSystem.getClip();
        clip.open(audioStream);
    }

    private void update(){
        if(clip.getMicrosecondPosition() == clip.getMicrosecondLength()){
            try{
                this.loadSong();
            }catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){
                e.printStackTrace();
            }
        }
    }

    private void setPlaylists(){
        File[] playlistSuperFolder = new File(directory).listFiles();
        for(File files : playlistSuperFolder){
            if(files.isDirectory()){
                playlists.add(new Playlist(directory, files.toString())); // Wird der String hier richtig gebaut? TESTEN!!
            }
        }
    }
}