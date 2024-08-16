package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import Controller.GuiFactory;


public class ClientMaske extends JFrame{
    private static ClientMaske clientMaske;
    private JLabel displayLabel;
    
    public ClientMaske(){
        this.setTitle("The greatest Musicplayer");
		this.setSize(720, 480);
		this.setResizable(true);
		this.setLayout(new BorderLayout());

        displayLabel = new JLabel(" ", JLabel.CENTER);
        this.add(displayLabel, BorderLayout.NORTH); //Noch besser machen

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

    public void addElement(Component comp, Object constr){
        this.add(comp, constr);
        this.revalidate();
        this.repaint();
    }

    public void updateDisplayedText(String text){
        SwingUtilities.invokeLater(
            () -> {
                // GuiFactory.getGuiFactory().updateLabel(text);
                displayLabel.setText(text);
                this.revalidate();
                this.repaint();
            }
        );
    }
}