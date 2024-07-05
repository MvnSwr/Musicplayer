package Model;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playlist {
    private List<TrackRecord> list;
    private int pointer;
    private static Random random;
    private String name;

    public Playlist(String name){
        this.name = name;
        list = new ArrayList<>();
        pointer = -1;
        random = new Random();
    }

    public TrackRecord getSongInLine(){
        loadIfEmpty();
        return list.get(++pointer);
    }

    public TrackRecord getRandomSong(){
        loadIfEmpty();
        return  list.get(random.nextInt(list.size()));
    }

    private void loadIfEmpty(){
        if(list.isEmpty()){
            this.load();
        }
    }

    private void load(){
		File root = new File("C:/Users/marvi/OneDrive/Laptop/Programmieren/Musicplayer/Music/" + name);
		if (root.exists() && root.isDirectory()) {
            getFiles(root.listFiles());
        }
    }

    private void getFiles(File[] files){
		for(File fl : files){
			if(fl.isDirectory()){
				getFiles(fl.listFiles());
			}else if(fl.getName().endsWith(".wav")){
				list.add(new TrackRecord(fl.getAbsolutePath()));
			}
		}
	}

    /*
    private static List<TrackRecord> tracksAll = new ArrayList<>();
	private static List<TrackRecord> queue = new ArrayList<>();
    private static int pointer = -1;
    
	public static String getSong() throws NoRemainingSongException{
		return (queue.get(pointer += 1) != null) ? queue.get(++pointer).titel() : getSong(random.nextInt(tracksAll.size())); //Testen
	}

    private static String getSong(int n) throws NoRemainingSongException{
		if (tracksAll.isEmpty()){
			throw new NoRemainingSongException();
		}
		queue.add(tracksAll.remove(n));
		return queue.get(++pointer).titel();
	}

    */
	
}