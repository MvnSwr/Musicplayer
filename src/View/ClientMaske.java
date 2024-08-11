package View;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Controller.GuiFactory;


public class ClientMaske extends JFrame{

    private static ClientMaske clientMaske;
    
    public ClientMaske(){
        this.setTitle("The greatest Musicplayer");
		this.setSize(720, 460);
		this.setResizable(false);
		this.setLayout(null);

        this.updateDisplayedText();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new Color(0x82A3AC));
        this.setJMenuBar(GuiFactory .getGuiFactory()
                                    .getDropDownMenu()
                                    .createMenu());
    }

    public static ClientMaske getClientMaske(){
        if(clientMaske == null){
            clientMaske = new ClientMaske();
        }
        return clientMaske;
    }

    public void updateButtons(){
        GuiFactory  .getGuiFactory()
                    .getAllButtons()
                    .forEach(e -> {
                        this.add(e.getButton());
                    });
        
        this.setVisible(true);
    }

    public void updateDisplayedText(){
        JLabel label = new JLabel("My Label ajisudaisdiad", JLabel.CENTER);
        this.add(label);

        this.setVisible(true);
    }
}