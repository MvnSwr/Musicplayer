import java.awt.*;
import javax.swing.*;

public class Gui extends JFrame {

	// für Buttons
	private JButton choose_BTN, 
					startstop_BTN,
					start_BTN,
					stop_BTN,
					break_BTN;

	// für Dropdown
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem1;

	// Constructor
	public Gui() {
		this.runGui();
	}

	private void runGui(){
		// Create Buttons
		choose_BTN = addButtons(250, 150, 100, 25, "Get Music");
		startstop_BTN = addButtons(375, 150, 100, 25, "StartStopBTN");
		start_BTN = addButtons(250, 380, 70, 25, "Start");
		stop_BTN = addButtons(330, 380, 70, 25, "Stop");
		break_BTN = addButtons(410, 380, 70, 25, "break");


		// Create the Frame
		this.setTitle("The greatest Musicplayer");
		this.setSize(720, 460);
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // funktioniert nicht so ganz
		this.getContentPane().setBackground(new Color(0x82A3AC));

		this.setVisible(true);
		this.setLayout(null);

		this.add(choose_BTN);
		this.add(startstop_BTN);
		this.add(start_BTN);
		this.add(stop_BTN);
		this.add(break_BTN);

		// create Buttonfunctionality
		buttonFunction();

		// create DropdownMenu
		dropdownMenu();
	}

	@SuppressWarnings("unused")
	// Create Button Methods
	private JButton addButtons(int x, int y, int width, int height) {
		return addButtons(x, y, width, height, "Standard");
	}

	private JButton addButtons(int x, int y, int width, int height, String name) {
		JButton btn = new JButton(name);
		btn.setBounds(x, y, width, height);
		return btn;
	}

	// Buttonfunctionality

	private void buttonFunction() {
		choose_BTN.addActionListener(e -> {
			// -- //
		});
		
		startstop_BTN.addActionListener(e -> {
			// -- //
		});

		start_BTN.addActionListener(e -> {
			// -- //
		});

		stop_BTN.addActionListener(e -> {
			// -- //
		});

		break_BTN.addActionListener(e -> {
			// -- //
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
	private void dropdownMenu() {
		// Create the dropdown
		menuBar = new JMenuBar();
		menu = new JMenu("File");

		// Create menu items
		menuItem1 = new JMenuItem("getMusic");
//		JMenuItem menuItem2 = new JMenuItem("Save");
//		JMenuItem menuItem3 = new JMenuItem("Exit");

		// Add menu items to the menu
		menu.add(menuItem1);
//		menu.add(menuItem2);
		menu.addSeparator(); // Add a separator between menu items
//		menu.add(menuItem3);

		// Add the menu to the menu bar
		menuBar.add(menu);
	}
}