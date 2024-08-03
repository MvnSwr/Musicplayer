package View;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import Controller.PlayOptions;

public class DropDownMenuMaske {

    public JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");

        JMenuItem safeButton = new JMenuItem("Save Music");
        JMenuItem deleteButton = new JMenuItem("Delete Cache");

        safeButton.addActionListener(e -> {
            PlayOptions.getPlayOptions().safe();
        });

        deleteButton.addActionListener(e -> {
            PlayOptions.getPlayOptions().deleteSafedCache();
        });

        menu.add(safeButton);
        menu.addSeparator();
        menu.add(deleteButton);

        menuBar.add(menu);

        return menuBar;
    }
}
