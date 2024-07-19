package View;
import java.awt.*;
import javax.swing.*;

import Model.Button;
import Model.Playlistss;
import Controller.Musicplayer;
import Exceptions.NoRemainingSongException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Gui extends JFrame {
 //GUI wirklich in eine eigene Klasse auslagern. Hier ist viel zu viel Logik drinne
	// Buttons
	private Button  choose_BTN,
					start_BTN,
					stop_BTN,
					break_BTN,
					test_BTN;

	private Thread thread = new Thread(() -> {
		while(true){
			System.out.println(Musicplayer.isEmpty());
			if(Musicplayer.isEmpty()){
				try {
					Musicplayer.loadNext(Playlistss.getSong());
				}catch(NoRemainingSongException err) {
					err.printStackTrace();
				}catch(UnsupportedAudioFileException | IOException | LineUnavailableException err){
					err.printStackTrace();
				}
				Musicplayer.start();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
	});

	// Dropdown
	// private JMenuBar menuBar;
	// private JMenu menu;
	// private JMenuItem menuItem1;

	// Constructor
	public Gui() {
		Playlistss.load();
		this.runGui();
	}

	private void runGui(){
		// Create Buttons
		choose_BTN = new Button(250, 150, 100, 25, "Get Music");
		start_BTN = new Button(250, 380, 70, 25, "Start");
		stop_BTN = new Button(330, 380, 70, 25, "Stop");
		break_BTN = new Button(410, 380, 70, 25, "break");
		test_BTN = new Button(375, 150, 100, 25, "Test");


		// Create the Frame
		this.setTitle("The greatest Musicplayer");
		this.setSize(720, 460);
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new Color(0x82A3AC));

		this.setVisible(true);
		this.setLayout(null);

		this.add(choose_BTN.getButton());
		this.add(start_BTN.getButton());
		this.add(stop_BTN.getButton());
		this.add(break_BTN.getButton());
		this.add(test_BTN.getButton());

		// create Buttonfunctionality
		buttonFunction();
	}

	// Buttonfunctionality

	private void buttonFunction() {
		choose_BTN.getButton().addActionListener(e -> {
			try {
				Musicplayer.loadNext(Playlistss.getSong());
			}catch(NoRemainingSongException err) {
				err.printStackTrace();
			}catch(UnsupportedAudioFileException | IOException | LineUnavailableException err){
				err.printStackTrace();
			}
		});

		start_BTN.getButton().addActionListener(e -> {
			try {
				Musicplayer.start();
				
				thread.start();
			} catch (NullPointerException err) {
				System.err.println("Es wurde noch keine Musik in den clip geladen! Keine Funktion ausgeführt");
			}
		});

		stop_BTN.getButton().addActionListener(e -> {
			try {
				Musicplayer.stop();
			} catch (NullPointerException err) {
				System.out.println("Keine Musik in clip geladen! Keine Funktion ausgeführt");
			}
		});

		break_BTN.getButton().addActionListener(e -> {
			try {
				Musicplayer.flush();
			} catch (NullPointerException err) {
				System.out.println("Keine Musik in clip geladen! Keine Funktion ausgeführt");
			}
		});

		test_BTN.getButton().addActionListener(e -> {
			try {
				Playlistss.lastTitle();
			}catch(NoRemainingSongException err) {
				err.printStackTrace();
			}catch(UnsupportedAudioFileException | IOException | LineUnavailableException err){
				err.printStackTrace();
			}
		});
	}

	// 	startstopBTN.addActionListener(new ActionListener() {
	// 		public void actionPerformed(ActionEvent e) {
	// 			// Musik wird abgespielt, sobald der Button gedrückt wird
	// 			if (!isPlaying) {
	// 				play(player, player.selection(), playlist);
	// 				startstopBTN.setText("Stop");
	// 				isPlaying = true;
	// 			} else {
	// 				// implementation die Musik zu stopen
	// 				startstopBTN.setText("start");
	// 				isPlaying = false;
	// 			}
	// 		}
	// 	});
	// }



	// Dropdown für sowas wie hier oben links das File Edit Source Refactor ...
// 	private void dropdownMenu() {
// 		// Create the dropdown
// 		menuBar = new JMenuBar();
// 		menu = new JMenu("File");

// 		// Create menu items
// 		menuItem1 = new JMenuItem("getMusic");
// //		JMenuItem menuItem2 = new JMenuItem("Save");
// //		JMenuItem menuItem3 = new JMenuItem("Exit");

// 		// Add menu items to the menu
// 		menu.add(menuItem1);
// //		menu.add(menuItem2);
// 		menu.addSeparator(); // Add a separator between menu items
// //		menu.add(menuItem3);

// 		// Add the menu to the menu bar
// 		menuBar.add(menu);
	// }
}