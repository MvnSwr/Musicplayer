import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Repository.NoRemainingSongException;
import Repository.TrackRecord;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Music {
    private static List<TrackRecord> tracksAll = new ArrayList<>();
	private static List<TrackRecord> queue = new ArrayList<>();
	private static Random random = new Random();
	private static int pointer = -1;
    
	static String getSong() throws NoRemainingSongException{
		return (queue.get(pointer += 1) != null) ? queue.get(++pointer).titel() : getSong(random.nextInt(tracksAll.size())); //Testen
	}

    static void load(){
		File root = new File("C:/Users/marvi/OneDrive/Laptop/Programmieren/Musicplayer/Music");
		if (root.exists() && root.isDirectory()) {
            getFiles(root.listFiles());
        }
    }

	static void lastTitle() throws UnsupportedAudioFileException, LineUnavailableException, IOException, NoRemainingSongException{
		if(--pointer < 0){
			throw new NoRemainingSongException();
		}
		Musicplayer.flush();
		Musicplayer.loadNext(queue.get(pointer).titel());
		Musicplayer.start();
	}

	private static void getFiles(File[] files){
		for(File fl : files){
			if(fl.isDirectory()){
				getFiles(fl.listFiles());
			}else if(fl.getName().endsWith(".wav")){
				tracksAll.add(new TrackRecord(fl.getAbsolutePath()));
			}
		}
	}

	private static String getSong(int n) throws NoRemainingSongException{
		if (tracksAll.isEmpty()){
			throw new NoRemainingSongException();
		}
		queue.add(tracksAll.remove(n));
		return queue.get(++pointer).titel();
	}
}