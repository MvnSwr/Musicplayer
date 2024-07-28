package newImplement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        public void stopRunning() {
            running = false;
            this.interrupt();
        }
    }

    private List<Playlist> playlists;
    private Playlist currentPlayList;
    private static PlayOptions playOptions;
    private String directory; // full path to the musicdirectories incl. "/"
    private Clip clip;
    private Boolean isOnShuffle = false;
    private UpdateThread updateThread;

    public PlayOptions(){
        setPlaylists();
    }

    public PlayOptions(String directory){
        this.directory = directory;
        setPlaylists();
    }

    public static PlayOptions getPlayOptions(){
        return playOptions == null ? playOptions = new PlayOptions() : playOptions;
    }

    public static PlayOptions getPlayOptions(String directory){
        return playOptions == null ? playOptions = new PlayOptions(directory) : playOptions;
    }

    //Läuft über Buttonaufruf
    //ALLES TODO
    public void startSong(){
        if(clip == null){
            this.loadSongHandler();
        }
        updateThread = new UpdateThread();
        updateThread.start();
        clip.start();
    }

    public void stopSong(){
        clip.stop();
        updateThread.stopRunning();
        try{
            updateThread.join();
        }catch(InterruptedException e){
            //nothing to see here
        }
    }
    public void repeatSong(){}
    public void lastSong(){}
    public void skipSong(){
        this.stopSong();
        this.loadSongHandler();
        this.startSong();
    }
    public void clearCache(){}

    //Ende ToDo
    public void shuffleSwitch(){
        isOnShuffle = !isOnShuffle;
    }

    public void setDirectory(String directory){
        this.directory = directory;
        setPlaylists(); //bei neuem directory sollen direkt die Playlists gesetzt werden
    } // "C:/Users/marvi/OneDrive/Laptop/Programmieren/Musicplayer/Music/"

    // Ende "Läuft pber Buttonaufruf"

    public String getDirectory(){
        return directory;
    }

    public void setCurrentPlaylist(){
        currentPlayList = playlists.get(0);//Überarbeiten
    }

    private void loadSong() throws UnsupportedAudioFileException,IOException,LineUnavailableException{
        AudioInputStream audioStream = 
                         isOnShuffle == true ? AudioSystem.getAudioInputStream(new File(currentPlayList.getRandomSong().title())) 
                                             : AudioSystem.getAudioInputStream(new File(currentPlayList.getNextSongInLine().title()));
        clip = AudioSystem.getClip();
        clip.open(audioStream);
    }

    private void loadSongHandler(){
        try {
            this.loadSong();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void update(){
        System.out.println(clip.getMicrosecondPosition() + " von " + clip.getMicrosecondLength());
        if(clip.getMicrosecondPosition() == clip.getMicrosecondLength()){
            this.stopSong();
            this.loadSongHandler();
            this.startSong();
        }
    }

    private void setPlaylists(){
        if(playlists == null){
            playlists = new ArrayList<>();
        }

        File[] playlistSuperFolder = new File(directory).listFiles();
        for(File files : playlistSuperFolder){
            if(files.isDirectory()){
                playlists.add(new Playlist(files.toString())); // files.toString gibt den ganzen Pfad bis zur "Playlist" an
            }
        }
    }
}