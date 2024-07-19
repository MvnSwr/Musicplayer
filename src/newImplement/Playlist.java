package newImplement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playlist {
    private List<SongRecord> listOfSongs;
    private int pointer;
    private String name;
    private Random random;

    public Playlist(String name){
        this.name = name;
        this.listOfSongs = new ArrayList<>();
        this.pointer = -1;
        this.random = new Random();

        fillListOfSongs(name);
    }

    public SongRecord getRandomSong(){
        return listOfSongs.get(random.nextInt(listOfSongs.size()));
    }

    public SongRecord getNextSongInLine(){
        return ++pointer >= listOfSongs.size() ? listOfSongs.get(pointer = 0): listOfSongs.get(pointer);
    }

    public SongRecord getCurrentSongRepeat(){
        return listOfSongs.get(pointer);
    }

    public SongRecord getLastSong(){
        return --pointer < 0 ? listOfSongs.get(pointer = listOfSongs.size() - 1): listOfSongs.get(pointer);
    }

    public String getName(){
        return name;
    }

    private void fillListOfSongs(String name){
        File root = new File("C:/Users/marvi/OneDrive/Laptop/Programmieren/Musicplayer/Music/" + name);
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