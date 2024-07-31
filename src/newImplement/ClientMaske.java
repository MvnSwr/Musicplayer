package newImplement;

import java.awt.Color;

import javax.swing.JFrame;


public class ClientMaske extends JFrame{

    private static ClientMaske clientMaske;
    
    public ClientMaske(){
        this.setTitle("The greatest Musicplayer");
		this.setSize(720, 460);
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Hier Lambda für das Speichern der Playlists etc.?!
		this.getContentPane().setBackground(new Color(0x82A3AC));

		this.setLayout(null);

        this.setJMenuBar(DropDownMenuMaske.createMenu());

        this.update();
    }

    public static ClientMaske getClientMaske(){
        return clientMaske == null ? clientMaske = new ClientMaske() : clientMaske;
    }

    public void update(){
        GuiFactory.getGuiFactory().getAllButtons().forEach(e -> {
            this.add(e.getButton());
        });

        this.setVisible(true);
    }
}