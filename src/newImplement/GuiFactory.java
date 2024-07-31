package newImplement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

public class GuiFactory {
    private static GuiFactory guiFactory;

    private List<Button> allButtons; // for easier iteration
    private boolean startStopSwitch;

    private Button startStopButton;
    private Button skipButton;
    private Button repeatButton;
    private Button lastSongButton; // doppelt belegen und erst ab einer gewissen audiolength erst den letzten song spielen, sonst einfach repeat song
    private Button setDirectoryButton;
    private Button selectPlaylistButton;
    private Button shuffleButton;

    public GuiFactory(){
        allButtons = new ArrayList<>();
        startStopSwitch = false;

        this.addButtons();
        this.setButtonFunctionality();        
    }

    public static GuiFactory getGuiFactory(){
        return guiFactory == null ? guiFactory = new GuiFactory() : guiFactory;
    }

    public List<Button> getAllButtons() {
        return allButtons;
    }

    private void addButtons(){
        allButtons.add(startStopButton = new Button(180, 350, 70, 25, "start"));
        allButtons.add(skipButton = new Button(280, 350, 70, 25, "skip"));
        allButtons.add(repeatButton = new Button(250, 280, 90, 25, "repeat"));
        allButtons.add(lastSongButton = new Button(360, 280, 130, 25, "lastSong"));
        allButtons.add(setDirectoryButton = new Button(250, 120, 120, 25, "Set Directory"));
        allButtons.add(selectPlaylistButton = new Button(420, 360, 140, 25, "select Playlist"));
        allButtons.add(shuffleButton = new Button(570, 360, 90, 25, "shuffle"));
    }

    //Set Button functionality
    private void setButtonFunctionality(){

        //Start-Stop Button
        startStopButton.getButton().addActionListener(e -> {
            if(!startStopSwitch){
                try{
                    PlayOptions.getPlayOptions().startSong();
                    allButtons.add(0, startStopButton = new Button(180, 350, 70, 25, "stop"));
                    this.setButtonFunctionality();
                }catch(NullPointerException err){ //Das mit dem Button funktioniert noch nicht..
                    return;
                }
            }else{
                PlayOptions.getPlayOptions().stopSong();
                allButtons.add(0,startStopButton = new Button(180, 350, 70, 25, "start"));
                this.setButtonFunctionality();
            }
            startStopSwitch = !startStopSwitch;  
            ClientMaske.getClientMaske().update();
        });

        //Skip Button
        skipButton.getButton().addActionListener(e -> {
            PlayOptions.getPlayOptions().skipSong();
        });

        //Repeat Button
        repeatButton.getButton().addActionListener(e -> {
            PlayOptions.getPlayOptions().repeatSong();
        });

        //Last Song Button
        lastSongButton.getButton().addActionListener(e -> {
            PlayOptions.getPlayOptions().lastSong();
        });

        //Set Directory Button
        setDirectoryButton.getButton().addActionListener(e -> {
            try{
                JFileChooser chooser = new JFileChooser();
                chooser.setAcceptAllFileFilterUsed(true);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setCurrentDirectory(new File("C:/"));

                int result = chooser.showOpenDialog(null); //result is an enum of what the user did
                
                if(result == JFileChooser.APPROVE_OPTION){
                    PlayOptions.getPlayOptions().setDirectory(chooser.getSelectedFile().getAbsolutePath() + "/");
                }
            }catch(Exception err){
                err.printStackTrace();
            }
        });

        //Select Playlist Button
        selectPlaylistButton.getButton().addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                BoxSelectorMaske boxSelectorMaske = new BoxSelectorMaske(PlayOptions.getPlayOptions().getPlaylistNames());
                boxSelectorMaske.setSize(300,200);
                boxSelectorMaske.setVisible(true);
            });
        });

        //Shuffle Button
        shuffleButton.getButton().addActionListener(e -> {
            PlayOptions.getPlayOptions().shuffleSwitch();
        });
    }
}