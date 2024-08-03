package View;

import java.awt.Color;

import javax.swing.JFrame;

import Controller.GuiFactory;


public class ClientMaske extends JFrame{

    private static ClientMaske clientMaske;
    
    public ClientMaske(){
        this.setTitle("The greatest Musicplayer");
		this.setSize(720, 460);
		this.setResizable(false);
		this.setLayout(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Hier Lambda für das Speichern der Playlists etc.?!
		this.getContentPane().setBackground(new Color(0x82A3AC));
        this.setJMenuBar(GuiFactory .getGuiFactory()
                                    .getDropDownMenu()
                                    .createMenu());
    }

    public static ClientMaske getClientMaske(){
        return clientMaske == null ? clientMaske = new ClientMaske() : clientMaske;
    }

    public void updateButtons(){
        GuiFactory  .getGuiFactory()
                    .getAllButtons()
                    .forEach(e -> {
                        this.add(e.getButton());
                    });
        
        this.setVisible(true);
    }
}