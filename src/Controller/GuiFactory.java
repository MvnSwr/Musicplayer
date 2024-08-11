package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import Model.Button;
import View.DropDownMenuMaske;
import View.ClientMaske;
import View.BoxSelectorMaske;

public class GuiFactory {
    private static GuiFactory guiFactory;

    private List<Button> allButtons; // for easier iteration
    private DropDownMenuMaske dropDownMenuMaske;

    private Button startButton;
    private Button stopButton;
    private Button skipButton;
    private Button repeatButton;
    private Button lastSongButton; // doppelt belegen und erst ab einer gewissen audiolength erst den letzten song spielen, sonst einfach repeat song
    private Button setDirectoryButton;
    private Button selectPlaylistButton;
    private Button shuffleButton;

    public GuiFactory(){
        allButtons = new ArrayList<>();
        dropDownMenuMaske = new DropDownMenuMaske();

        initializeGui();
    }

    public static GuiFactory getGuiFactory(){ //Singelton
        if (guiFactory == null) {
            guiFactory = new GuiFactory();
        }
        return guiFactory;
    }

    private void initializeGui() {
        SwingUtilities.invokeLater(() -> {
            GuiFactory.getGuiFactory().createButtons();
            GuiFactory.getGuiFactory().setButtonFunctionality();
            ClientMaske.getClientMaske().updateButtons();
        });
    }

    public void addButton(Button button){
        allButtons.add(button);
    }

    public List<Button> getAllButtons() {
        return allButtons;
    }

    private void createButtons(){
        startButton = new Button(180, 350, 70, 25, "start").addButton();
        stopButton = new Button(180, 350, 70, 25, "stop", false).addButton();
        skipButton = new Button(280, 350, 70, 25, "skip").addButton();
        repeatButton = new Button(250, 280, 90, 25, "repeat").addButton();
        lastSongButton = new Button(360, 280, 130, 25, "lastSong").addButton();
        setDirectoryButton = new Button(250, 120, 120, 25, "Set Directory").addButton();
        selectPlaylistButton = new Button(420, 360, 140, 25, "select Playlist").addButton();
        shuffleButton = new Button(570, 360, 90, 25, "shuffle").addButton();
    }

    //Set Button functionality
    private void setButtonFunctionality() {
        startButton.getButton().addActionListener(e -> handleStartButtonPress());
        stopButton.getButton().addActionListener(e -> handleStopButtonPress());
        skipButton.getButton().addActionListener(e -> PlayOptions.getPlayOptions().skipSong());
        repeatButton.getButton().addActionListener(e -> PlayOptions.getPlayOptions().repeatSong());
        lastSongButton.getButton().addActionListener(e -> PlayOptions.getPlayOptions().lastSong());
        setDirectoryButton.getButton().addActionListener(e -> handleSetDirectoryButtonPress());
        selectPlaylistButton.getButton().addActionListener(e -> handleSelectPlaylistButtonPress());
        shuffleButton.getButton().addActionListener(e -> PlayOptions.getPlayOptions().shuffleSwitch());
    }

    private void handleStartButtonPress() {
        ExceptionHandler.handleException(
            () -> {
                PlayOptions.getPlayOptions().startSong();
                switchStartStopButton();
                return null;
            }
        );
    }

    private void handleStopButtonPress() {
        PlayOptions.getPlayOptions().stopSong();
        switchStartStopButton();
    }

    private void handleSetDirectoryButtonPress() {
        ExceptionHandler.handleException(
            () -> {
                SwingUtilities.invokeLater(
                    () -> {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setAcceptAllFileFilterUsed(true);
                        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        chooser.setCurrentDirectory(new File("C:/"));
            
                        int result = chooser.showOpenDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            PlayOptions.getPlayOptions().setDirectory(chooser.getSelectedFile().getAbsolutePath() + "/");
                        }
                    }
                );
                return null;
            }
        );
    }

    private void handleSelectPlaylistButtonPress() {
        SwingUtilities.invokeLater(() -> {
            BoxSelectorMaske boxSelectorMaske = new BoxSelectorMaske(PlayOptions.getPlayOptions().getPlaylistNames());
            boxSelectorMaske.setSize(300, 200);
            boxSelectorMaske.setVisible(true);
        });
    }

    private void switchStartStopButton() {
        SwingUtilities.invokeLater(() -> {
            boolean isStartVisible = startButton.getButton().isVisible();
            startButton.getButton().setVisible(!isStartVisible);
            stopButton.getButton().setVisible(isStartVisible);
            ClientMaske.getClientMaske().updateButtons();
        });
    }

    protected void simulateStopButtonPress(){
        this.handleStopButtonPress();
    }

    public DropDownMenuMaske getDropDownMenu(){
        return dropDownMenuMaske;
    }
}