package presenters.gui;

import usecases.IOInterfaces.GameIO;

public class GameGUIFactory {

    /**
     * Create a new game GUI object based on the selected game.
     *
     * @param game selected gamed
     * @return a <code>GameGUI</code> object
     */
    public static GameIO gameGUIFactory(String game) {
        switch (game.toUpperCase()) {
            case "BURA":
                return new BuraGUI();
            case "CRAZY EIGHTS":
                return new CrazyEightsGUI();
            case "GO FISH":
                return new GoFishGUI();
//            case "WAR":
//                return new WarGUI();
            default:
                throw new IllegalArgumentException("Illegal game selection of " + game + '.');
        }
    }
}
