package Controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
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
    private Button setDirectoryButton;
    private Button selectPlaylistButton;
    private Button shuffleButton;

    // private JLabel songTitleLabel;
    // private JLabel shuffleLabel;

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
            createButtons();
            setButtonFunctionality();
            setButtonsInClientMaske();

            // createLabels();
            // setLabelsInClientMaske();
        });
    }

    public void addButton(Button button){
        allButtons.add(button);
    }

    public List<Button> getAllButtons() {
        return allButtons;
    }

    private void createButtons(){
        startButton = new Button("start").addButton();
        stopButton = new Button("stop", false).addButton();
        skipButton = new Button("skip").addButton();
        repeatButton = new Button( "repeat").addButton();
        setDirectoryButton = new Button( "Set Directory").addButton();
        selectPlaylistButton = new Button("select Playlist").addButton();
        shuffleButton = new Button( "shuffle").addButton();
    }

    // private void createLabels(){
    //     songTitleLabel = new JLabel(" ", JLabel.CENTER);
    //     shuffleLabel = new JLabel("shuffle is off", JLabel.CENTER);
    // }

    //Set Button functionality
    private void setButtonFunctionality(){
        startButton.getButton().addActionListener(e -> handleStartButtonPress());
        stopButton.getButton().addActionListener(e -> handleStopButtonPress());
        skipButton.getButton().addActionListener(e -> PlayOptions.getPlayOptions().skipSong());
        repeatButton.getButton().addActionListener(e -> PlayOptions.getPlayOptions().repeatSong());
        setDirectoryButton.getButton().addActionListener(e -> handleSetDirectoryButtonPress());
        selectPlaylistButton.getButton().addActionListener(e -> handleSelectPlaylistButtonPress());
        shuffleButton.getButton().addActionListener(e -> PlayOptions.getPlayOptions().shuffleSwitch());
    }

    private void handleStartButtonPress(){
        ExceptionHandler.handleException(
            () -> {
                PlayOptions.getPlayOptions().startSong();
                switchStartStopButton();
                return null;
            }
        );
    }

    private void handleStopButtonPress(){
        PlayOptions.getPlayOptions().stopSong();
        switchStartStopButton();
    }

    private void handleSetDirectoryButtonPress(){
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

    private void handleSelectPlaylistButtonPress(){
        SwingUtilities.invokeLater(() -> {
            BoxSelectorMaske boxSelectorMaske = new BoxSelectorMaske(PlayOptions.getPlayOptions().getPlaylistNames());
            boxSelectorMaske.setSize(300, 200);
            boxSelectorMaske.setVisible(true);
        });
    }

    private void switchStartStopButton(){
        boolean isStartVisible = startButton.getButton().isVisible();
        startButton.getButton().setVisible(!isStartVisible);
        stopButton.getButton().setVisible(isStartVisible);

        startButton.getButton().repaint();
        stopButton.getButton().repaint();
    }

    protected void simulateStopButtonPress(){
        this.handleStopButtonPress();
    }

    public DropDownMenuMaske getDropDownMenu(){
        return dropDownMenuMaske;
    }

    private void setButtonsInClientMaske() {
        JPanel settingButtons = createPanelWithButtons( new FlowLayout(FlowLayout.LEFT, 10, 10),
                                                        setDirectoryButton,
                                                        selectPlaylistButton);

        JPanel playbackButtons = createPanelWithButtons(new FlowLayout(FlowLayout.CENTER, 10, 10),
                                                        repeatButton,
                                                        startButton,
                                                        stopButton,
                                                        skipButton);

        JPanel playbackOption = createPanelWithButtons( new FlowLayout(FlowLayout.RIGHT, 10, 10),
                                                        shuffleButton);

        JPanel combinedPanel = createCombinedPanel(playbackButtons, playbackOption);


        ClientMaske.getClientMaske().addElement(settingButtons, BorderLayout.NORTH);
        ClientMaske.getClientMaske().addElement(combinedPanel, BorderLayout.SOUTH);
    }

    private JPanel createPanelWithButtons(LayoutManager layout, Button... buttons) {
        JPanel panel = new JPanel(layout);
        
        for (Button button : buttons) {
            panel.add(button.getButton());
        }
        return panel;
    }

    private JPanel createCombinedPanel(JPanel centerPanel, JPanel eastPanel) { //noch generischer machen?
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(centerPanel, BorderLayout.CENTER);
        combinedPanel.add(eastPanel, BorderLayout.EAST);
        return combinedPanel;
    }

    // private void setLabelsInClientMaske(){
    //     ClientMaske.getClientMaske().addElement(songTitleLabel, BorderLayout.NORTH);
    //     ClientMaske.getClientMaske().addElement(shuffleLabel, BorderLayout.NORTH);
    // }

    // public void updateLabel(String text){
    //     songTitleLabel.setText(text);
    //     ClientMaske.getClientMaske().addElement(songTitleLabel, BorderLayout.NORTH);
    // }
}