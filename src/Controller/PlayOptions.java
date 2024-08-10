package Controller;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Model.CustomPlayerImpl;
import Model.Player;
import Model.Playlist;

public class PlayOptions{
    
    private static PlayOptions playOptions;
    private List<Playlist> playlists;
    private Playlist currentPlayList;
    private String directory = ""; // full path to the musicdirectories incl. "/"
    private Player clip;
    private Boolean isOnShuffle = false;
    private MusicUpdateThread updateThread;

    public PlayOptions(){
        try{
            this.load();
        }catch(ClassNotFoundException e){
        }catch(IOException e) {
            //Hier ein PopUp das des Laden nicht funktioniert hat
            e.printStackTrace();
        }
    }

    public static PlayOptions getPlayOptions(){ //Singelton
        return playOptions == null ? playOptions = new PlayOptions() : playOptions;
    }

    //Läuft über Buttonaufruf
    public void startSong(){
        if(clip == null){
            this.loadSong();
        }
        updateThread = new MusicUpdateThread();
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

    public void repeatSong(){
        try{
            this.stopIfClipIsOpen();

            clip.open(AudioSystem.getAudioInputStream(
                        new File(currentPlayList.getCurrentSongRepeat().title())));
            this.startSong();
        }catch(NullPointerException e){
        }catch(LineUnavailableException | IOException | UnsupportedAudioFileException e){
            e.printStackTrace();
        }
    }

    public void lastSong(){
        try{
            this.stopIfClipIsOpen();
            
            clip.open(AudioSystem.getAudioInputStream(
                        new File(currentPlayList.getLastSong().title())));
            this.startSong();
        }catch(NullPointerException e){
        }catch(LineUnavailableException | IOException | UnsupportedAudioFileException e){
            e.printStackTrace();
        }
    }

    public void skipSong(){
        try{
            this.stopSong();
            this.loadSong();
            this.startSong();
        }catch(NullPointerException e){
        }   
    }

    public void shuffleSwitch(){
        isOnShuffle = !isOnShuffle;
    }

    public void setDirectory(String directory){
        this.directory = directory;
        setPlaylists(); //bei neuem directory sollen direkt die Playlists gesetzt werden
    }

    // Ende "Läuft pber Buttonaufruf"

    public void setCurrentPlaylist(String name){
        playlists.forEach(e -> {
            if(e.getName().equals(name)){
                currentPlayList = e;
            }
        });

        if(this.clip != null){ // stop the playback if the playlist is changed
            GuiFactory.getGuiFactory().simulateStopButtonPress();
            clip = null;
        }
    }

    public List<String> getPlaylistNames(){
        List<String> names = new ArrayList<>();
        playlists.forEach(e -> {
            names.add(e.getName());
        });
        return names;
    }

    private void loadSong(){
        try{
            AudioInputStream audioStream = 
                            isOnShuffle == true ? AudioSystem.getAudioInputStream(
                                                    new File(currentPlayList.getRandomSong().title())) 
                                                : AudioSystem.getAudioInputStream(
                                                    new File(currentPlayList.getNextSongInLine().title()));
            clip = new CustomPlayerImpl();
            clip.open(audioStream);
        }catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
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

    private void stopIfClipIsOpen(){
        if(clip.isOpen()){
            this.stopSong();
            clip.close();
        }
    }

    protected void update(){
        System.out.println(clip.getMicrosecondPosition() + " von " + clip.getMicrosecondLength());
        if(clip.getMicrosecondPosition() == clip.getMicrosecondLength()){
            this.stopSong();
            this.loadSong();
            this.startSong();
        }
    }

    public void safe(){
        DatabaseHandler.safe(directory);
    }

    private void load() throws IOException, ClassNotFoundException{
        this.setDirectory(DatabaseHandler.load());
    }

    public void deleteSafedCache(){
        DatabaseHandler.deleteSafedCache();
    }
}