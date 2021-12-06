package controllers;

import java.util.List;

/**
 * This class defines an interface for the Main Menu. Any class that implements this interface can be given to the
 * <code>MainMenu</code> constructor and be used to interact with the menu.
 */
public interface MainMenuIO {

    /**
     * Retrieve the user's selection.
     *
     * @param games array of games for the menu
     * @return an integer representing the selection
     */
    int getUserSelection(String[] games);

    /**
     * Retrieve a username of a player that is to be playing the selected game.
     *
     * @return a valid username representation or "done" if the user does not want to enter another user name.
     * @see helpers.UsernameCheck
     */
    String getUsername();

    /**
     * Send a popup to the user containing <code>message</code>.
     *
     * @param message the message to send to the user
     */
    void sendPopup(String message);

    /**
     * This method should close the GUI when called.
     */
    void close();

}
