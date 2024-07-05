package Controller;

import Exceptions.NoRemainingSongException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Controller.Musicplayer;

import java.io.IOException;
public class SongQueue { //Singelton
    private SongQueue songQueue = null;

    public SongQueue(){}

    public SongQueue getSongQueue(){
        if(songQueue == null){
            songQueue = new SongQueue();
        }
        return songQueue;
    }


    public void lastTitle() throws UnsupportedAudioFileException, LineUnavailableException, IOException, NoRemainingSongException{
		if(--pointer < 0){
			throw new NoRemainingSongException();
		}
		Musicplayer.flush();
		Musicplayer.loadNext(queue.get(pointer).titel());
		Musicplayer.start();
	}
}