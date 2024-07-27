package newImplement;

public class GuiFactory {
    private static GuiFactory guiFactory;
    private Button startButton;
    private Button stopButton;
    private Button skipButton;
    private Button repeatButton;
    private Button lastSongButton;

    public GuiFactory(){}

    public static GuiFactory getGuiFactory(){
        return guiFactory == null ? guiFactory = new GuiFactory() : guiFactory;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public Button getSkipButton() {
        return skipButton;
    }

    public Button getRepeatButton() {
        return repeatButton;
    }

    public Button getLastSongButton() {
        return lastSongButton;
    }

}