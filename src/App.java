

import View.ClientMaske;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args){
        SwingUtilities.invokeLater( //Every Gui change has to be done in the AWT-Thread
            () ->{
                ClientMaske.getClientMaske().setVisible(true);
            }
        );
        
    }
    //TODO SoundCloud und Spotify integration
    // SoundCloud integration durch automatisiertes Downloaden?
    //TODO alles etwas schöner machen
    //TODO Ich will wissen welche Playlist und welcher Song läuft
    //TODO es soll merkbar sein ob der Shuffle gerade an oder aus ist
    //TODO Problem beheben das manchmal einfach eine zweite Instanz der Gui erzeugt wird
}