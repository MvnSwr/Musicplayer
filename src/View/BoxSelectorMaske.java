package View;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Controller.PlayOptions;

import java.awt.FlowLayout;

import java.util.List;

public class BoxSelectorMaske extends JFrame{

    private JComboBox<String> boxOflists = new JComboBox<>();
    private String choosenElement;

    public BoxSelectorMaske(List<String> list){
        list.forEach(e -> {
            boxOflists.addItem(e);
        });

        boxOflists.addActionListener(e -> {
            choosenElement = ((JComboBox<?>)e.getSource()).getSelectedItem().toString();
        });

        JButton accept = new JButton("Accept");

        accept.addActionListener(e -> {
            if (choosenElement != null) {
                PlayOptions.getPlayOptions().setCurrentPlaylist(choosenElement);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(  this, 
                                                "Please select a Item first.", 
                                                "Error", 
                                                JOptionPane.ERROR_MESSAGE);
            }
        });

        this.setLayout(new FlowLayout());
        this.add(boxOflists);
        this.add(accept);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public String getchoosenElement(){
        return choosenElement != null ? choosenElement : "";
    }
}