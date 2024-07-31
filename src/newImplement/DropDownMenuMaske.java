package newImplement;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class DropDownMenuMaske {

    public static JMenuBar createMenu() {
        // Menüleiste erstellen
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");

        // Menüeinträge erstellen
        JMenuItem safeButton = new JMenuItem("Save Music");
        JMenuItem deleteButton = new JMenuItem("Delete Cache");

        safeButton.addActionListener(e -> {
            PlayOptions.getPlayOptions().safe();
        });

        deleteButton.addActionListener(e -> {
            PlayOptions.getPlayOptions().deleteSafedCache();
        });

        // Menüeinträge zum Menü hinzufügen
        menu.add(safeButton);
        menu.addSeparator(); // Trennlinie zwischen Menüeinträgen hinzufügen
        menu.add(deleteButton);

        // Menü zur Menüleiste hinzufügen
        menuBar.add(menu);

        return menuBar;
    }
}
