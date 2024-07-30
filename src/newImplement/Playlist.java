package newImplement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playlist {
    private List<SongRecord> listOfSongs;
    private List<SongRecord> queue;
    private int pointer;
    private String name;
    private Random random;

    public Playlist(String directory){
        this.name = getNameOfPlaylist(directory, '\\');
        this.listOfSongs = new ArrayList<>();
        this.queue = new ArrayList<>();
        this.pointer = -1;
        this.random = new Random();

        fillListOfSongs(directory); //name ist noch der ganze Pfad
    }

    public SongRecord getRandomSong(){
        return addToQueue(listOfSongs.get(pointer = random.nextInt(listOfSongs.size())));
    }

    public SongRecord getNextSongInLine(){
        return addToQueue(++pointer >= listOfSongs.size() ? listOfSongs.get(pointer = 0): listOfSongs.get(pointer));
    }

    public SongRecord getCurrentSongRepeat(){
        return queue.get(queue.size() - 1);
    }

    public SongRecord getLastSong(){
        if(queue.size() - 2 < 0){
            return null;
        }else{
            --pointer;
            queue.remove(queue.size() - 1);
            return queue.get(queue.size() - 1);
        }
    }

    public String getName(){
        return name;
    }
    
    private String getNameOfPlaylist(String input, char separator) {
        int lastIndex = input.lastIndexOf(separator);
        if (lastIndex == -1) {
            return input;
        }
        return input.substring(lastIndex + 1);
    }
    
    private SongRecord addToQueue(SongRecord song){
        queue.add(song);
        return queue.get(queue.size()-1);
    }

    private void fillListOfSongs(String directory){
        File root = new File(directory);
        if (root.exists() && root.isDirectory()) {
            getFiles(root.listFiles());  
        }
    }

    private void getFiles(File[] files){ //Rekursiv
        for(File file : files){
            if(file.isDirectory()){
                getFiles(file.listFiles());
            }else if(file.getName().endsWith(".wav")){
                listOfSongs.add(new SongRecord(file.getAbsolutePath()));
            }
        }
    }
}