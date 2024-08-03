package Model;
import javax.swing.*;

import Controller.GuiFactory;

public class Button{

    private JButton button;

    public JButton getButton(){
        return this.button;
    }
    
    public Button(int x, int y, int width, int height){
        this(x, y, width, height, "Standard");
    }

    public Button(int x, int y, int width, int height, String name){
        this(x, y, width, height, name, true);
    }

    public Button(int x, int y, int width, int height, String name, boolean visibility){
        button = new JButton(name);
		button.setBounds(x, y, width, height);
        button.setVisible(visibility);
    }

    public Button addButton(){
        GuiFactory.getGuiFactory().addButton(this);
        return this;
    }
}