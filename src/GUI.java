import java.awt.*;
import javax.swing.*;

public class Gui extends JFrame {

	// für Buttons
	JButton chooseBTN, startstopBTN;

	// für Dropdown
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem1;

	// Constructor
	public Gui() {
		this.runGui();
	}

	private void runGui(){
		// Create Buttons
		chooseBTN = addButtons(325, 380, 100, 25, "Get Music");
		startstopBTN = addButtons(450, 380, 100, 25, "Start");


		// Create the Frame
		this.setTitle("The greatest Musicplayer");
		this.setSize(720, 460);
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // funktioniert nicht so ganz
		this.getContentPane().setBackground(new Color(0x82A3AC));

		this.setVisible(true);
		this.setLayout(null);
		this.add(chooseBTN);
		this.add(startstopBTN);

		// create Buttonfunctionality
		// buttonFunction();

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

	// private void buttonFunction() {
	// 	chooseBTN.addActionListener(e -> {
	// 		String genre = player.dropdownMenu();
	// 		playlist = player.getMusic(genre);
	// 	});

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