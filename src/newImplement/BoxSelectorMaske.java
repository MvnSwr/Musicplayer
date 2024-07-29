package newImplement;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;

import java.util.List;

public class BoxSelectorMaske extends JFrame{

    private JComboBox<String> boxOfPlaylists = new JComboBox<>();
    private String choosenPlaylist;

    public BoxSelectorMaske(List<String> playlists){
        playlists.forEach(e -> {
            boxOfPlaylists.addItem(e);
        });

        boxOfPlaylists.addActionListener(e -> {
            choosenPlaylist = ((JComboBox<?>)e.getSource()).getSelectedItem().toString();
        });

        JButton accept = new JButton("Accept");

        accept.addActionListener(e -> {
            if (choosenPlaylist != null) {
                PlayOptions.getPlayOptions().setCurrentPlaylist(choosenPlaylist);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a playlist first.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.setLayout(new FlowLayout());
        this.add(boxOfPlaylists);
        this.add(accept);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public String getChoosenPlaylist(){
        return choosenPlaylist != null ? choosenPlaylist : "";
    }
}