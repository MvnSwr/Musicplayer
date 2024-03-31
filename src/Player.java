import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.sound.sampled.*;

public class Player {

	/**
	 * Zukunft:
	 * 
	 * richtiges gui
	 * 
	 * überprüfung auf .mp3 statt wav
	 * 
	 * zu formatieren einfach zwischen den Zeilen Stern löschen und dann speichern,
	 * kommt zwar zurück, merkt sich das aber
	 * 
	 * bereits importierte musik wird per verzeichnis in einer txt gespeichert.
	 * Gibts diese noch nicht, wird eine .txt erstellt. Zudem gibts nen extra Button
	 * um ggf. neue Musik zu importieren. ggf Problem wenn das alte Verzeichnis
	 * gelöscht wird
	 */

	// Constructor
	public Player() {
	}

	/*
	 * 
	 * 
	 */

	public String dropdownMenu() {
		JFrame frame = new JFrame();
		// frame.setAlwaysOnTop(true); Weiß nicht warum ich das brauchen sollte

		// getting the options right from the foldernames itself
		File folder = new File("C:/Users/marvi/OneDrive/Desktop/Laptop/Programmieren/Musicplayer/Music/");
		ArrayList<String> foldernames = new ArrayList<>();
		for (File fl : folder.listFiles()) {// for each bei Files müssen über methode iteriert werden
			if (fl.isDirectory()) {
				foldernames.add(fl.getName());
			}
		}

		// Writing the String from the list into an Array
		String[] options = new String[foldernames.size()];
		int i = 0;
		for (String name : foldernames) {
			options[i++] = name;
		}
		// We have to cast to String

		return (String) JOptionPane.showInputDialog(frame, "Choose the Genre", "Genre", JOptionPane.PLAIN_MESSAGE, null,
				options, options[0]);
	}

	/*
	 * 
	 * 
	 */

	// selection if we want to shuffle or to play in order
	int selection() {
		Object[] options = { "Shuffle", "In Order" };
		return JOptionPane.showOptionDialog(null, "How do you want the playlist to be played?", "Playstyle",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, JOptionPane.CLOSED_OPTION);
	}

	/*
	 * 
	 * 
	 */

	// Every Song including the path is safed and returned in a StringArray
	String[] getMusic(String str) {
		File folder = new File("C:/Users/marvi/OneDrive/Desktop/Laptop/Programmieren/Musicplayer/Music/" + str + "/");
		ArrayList<String> songnames = new ArrayList<>();
		for (File fl : folder.listFiles()) {// for each bei Files müssen über methoder iteriert werden
			if (fl.isFile()) {
				songnames.add("C:/Users/marvi/OneDrive/Desktop/Laptop/Programmieren/Musicplayer/Music/" + str + "/"
						+ fl.getName());
				System.out.println(fl.getName());
			}
		}

		// List to Stringarray
		String[] songs = new String[songnames.size()];
		int i = 0;
		for (String name : songnames) {
			songs[i++] = name;
		}
		return songs;
	}

	/*
	 * 
	 * 
	 */

	void order(String[] playlist) {
		System.out.println("\nClassical play in Order");
		for (String str : playlist)
			playtime(str);
	}

	/*
	 * 
	 * 
	 */

	void shuffle(String[] playlist) {
		System.out.println("\nsuper fancy shuffle");

		// Class for generating a random number
		Random ran = new Random();
		int[] used = new int[playlist.length];

		// Song an 0.ter Stelle braucht bisschen sonderbedingungen da unten in der
		// !isused abfrage der Fall 0 übersprungen wird
		boolean zero = true;
		for (int i = 0; i < playlist.length; i++) {
			int x;
			while (true) {
				boolean isused = false;

				// random Zahl wird x zugewiesen, playlist.length ist obere Grenze
				x = ran.nextInt(used.length);
				for (Integer j : used) {
					if (x == j) {
						isused = true;
					}
				}
				if (!isused) {
					used[i] = x;
					break;
				} else if (x == 0 && zero) {
					used[i] = 0;
					zero = false;
					break;
				}
			}
			playtime(playlist[x]);
		}
	}

	/*
	 * 
	 * 
	 */

	void playtime(String filepath) {
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filepath));
			AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

			// Check if the target format is supported
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, targetFormat);
			if (!AudioSystem.isLineSupported(info)) {
				System.err.println("Target format not supported");
				// .err ist wie out nur das man hier noch die Möglichkeit hat den Output in eine
				// txt datei zu speichern
			}

			// Create a line to play the audio
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(targetFormat);
			line.start();

			// Play the audio
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = audioStream.read(buffer)) != -1) {
				line.write(buffer, 0, bytesRead);
			}

			// Close the streams and the line
			audioStream.close();
			line.drain();
			line.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}