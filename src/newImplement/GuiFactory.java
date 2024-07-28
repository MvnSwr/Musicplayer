package newImplement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.FilerException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GuiFactory {
    private static GuiFactory guiFactory;

    private List<Button> allButtons; // for easier iteration
    private boolean startStopSwitch;

    private Button startStopButton;
    private Button skipButton;
    private Button repeatButton;
    private Button lastSongButton;
    private Button setDirectoryButton;
    private Button selectPlaylistButton;
    private Button shuffleButton;

    public Button getSetDirectoryButton() {
        return setDirectoryButton;
    }

    public GuiFactory(){
        allButtons = new ArrayList<>();
        startStopSwitch = false;

        allButtons.add(startStopButton = new Button(250, 380, 70, 25, "start/stop"));
        allButtons.add(skipButton = new Button(410, 380, 70, 25, "skip"));
        allButtons.add(repeatButton = new Button(280, 300, 70, 25, "repeat"));
        allButtons.add(lastSongButton = new Button(360, 300, 70, 25, "lastSong"));
        allButtons.add(setDirectoryButton = new Button(250, 150, 100, 25, "Set Directory"));
        allButtons.add(selectPlaylistButton = new Button(490, 380, 70, 25, "select Playlist"));
        allButtons.add(shuffleButton = new Button(570, 380, 70, 25, "shuffle"));

        //Set Button functionality
        startStopButton.getButton().addActionListener(e -> {
            if(!startStopSwitch){
                PlayOptions.getPlayOptions().startSong();
            }else{
                PlayOptions.getPlayOptions().stopSong();
            }
            startStopSwitch = !startStopSwitch;            
        });

        skipButton.getButton().addActionListener(e -> {
            PlayOptions.getPlayOptions().skipSong();
        });

        repeatButton.getButton().addActionListener(e -> {
            PlayOptions.getPlayOptions().repeatSong();
        });

        lastSongButton.getButton().addActionListener(e -> {
            PlayOptions.getPlayOptions().lastSong();
        });

        setDirectoryButton.getButton().addActionListener(e -> {
            try{
                JFileChooser chooser = new JFileChooser();
                chooser.setAcceptAllFileFilterUsed(true);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setCurrentDirectory(new File("C:/"));

                int result = chooser.showOpenDialog(null); //result is an enum of what the user did
                
                if(result == JFileChooser.APPROVE_OPTION){
                    try{
                        PlayOptions.getPlayOptions().setDirectory(chooser.getSelectedFile().getAbsolutePath() + "/");
                    }catch(NullPointerException err){
                        PlayOptions.getPlayOptions(chooser.getSelectedFile().getAbsolutePath() + "/");
                    }
                }
            }catch(Exception err){
                err.printStackTrace();
            }
        });

        selectPlaylistButton.getButton().addActionListener(e -> {
            PlayOptions.getPlayOptions().setCurrentPlaylist(); //Überarbeiten
        });

        shuffleButton.getButton().addActionListener(e -> {
            PlayOptions.getPlayOptions().shuffleSwitch();
        });
        
    }

    public static GuiFactory getGuiFactory(){
        return guiFactory == null ? guiFactory = new GuiFactory() : guiFactory;
    }

    public List<Button> getAllButtons() {
        return allButtons;
    }
}