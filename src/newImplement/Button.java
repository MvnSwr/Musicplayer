package newImplement;
import javax.swing.*;

public class Button{

    private JButton button;

    public JButton getButton(){
        return this.button;
    }
    
    public Button(int x, int y, int width, int height){
        this(x, y, width, height, "Standard");
    }

    public Button(int x, int y, int width, int height, String name){
        button = new JButton(name);
		button.setBounds(x, y, width, height);
    }
}