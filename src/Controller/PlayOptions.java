package Controller;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import Model.CustomPlayerImpl;
import Model.Player;
import Model.Playlist;
import View.ClientMaske;

public class PlayOptions{
    
    private static PlayOptions playOptions;
    private List<Playlist> playlists;
    private Playlist currentPlayList;
    private String directory = ""; // full path to the musicdirectories incl. "/"
    private Player clip;
    private Boolean isOnShuffle = false;
    private MusicUpdateThread updateThread;

    public PlayOptions(){
        ExceptionHandler.handleException(
            () -> {
                if(new File("Cache.tmp").exists()){
                    this.load(); //Hier ein PopUp das des Laden nicht funktioniert hat (IOException)
                }
                return null;
            }
        );
    }

    public static PlayOptions getPlayOptions(){ //Singelton
        if(playOptions == null){
            playOptions = new PlayOptions();
        }
        return playOptions;
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

        ExceptionHandler.handleException(
            () -> {
                updateThread.join();
                return null;
            }
        );
    }

    public void repeatSong(){
        ExceptionHandler.handleException(
            () -> {
                if(clip.getMicrosecondPosition() < 2500000){
                    this.lastSong();
                }

                this.stopIfClipIsOpen();
                clip.open(AudioSystem.getAudioInputStream(
                        new File(currentPlayList.getCurrentSongRepeat().title())));
                this.startSong();
                return null;
            }
        );
    }

    public void lastSong(){
        ExceptionHandler.handleException(
            () -> {
                String title = currentPlayList.getLastSong().title();
                this.stopIfClipIsOpen();
                clip.open(AudioSystem.getAudioInputStream(
                        new File(title)));
                this.startSong();
                this.buildSubstringForTitle(title);
                return null;
            }
        );
    }

    public void skipSong(){
        ExceptionHandler.handleException(
            () -> {
                this.stopSong();
                this.loadSong();
                this.startSong();
                return null;
            }
        );
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
            if(clip.isActive()){
                GuiFactory.getGuiFactory().simulateStopButtonPress();
            }
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
        ExceptionHandler.handleException(
            () -> {
                String title;
                AudioInputStream audioStream = 
                isOnShuffle == true ? AudioSystem.getAudioInputStream(
                                        new File(title = currentPlayList.getRandomSong().title())) 
                                    : AudioSystem.getAudioInputStream(
                                        new File(title = currentPlayList.getNextSongInLine().title()));
                clip = new CustomPlayerImpl();
                clip.open(audioStream);

                this.buildSubstringForTitle(title);
                return null;
            }
        );
    }

    private void buildSubstringForTitle(String path){
        // Erstellung des Substrings um den Titel anzuzeigen
        path = path.substring(path.lastIndexOf('\\') + 1, path.indexOf(".wav"));
        ClientMaske.getClientMaske().updateDisplayedText(path);
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