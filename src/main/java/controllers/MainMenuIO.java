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
     * @return an integer representing the selection
     */
    int getUserSelection();

    /**
     * Retrieve a list of unique usernames that are to be playing the selected game.
     *
     * @return a valid username representation. The length must be between
     * <code>minNum</code> (inclusive) and <code>maxNum</code> (inclusive)
     * @see helpers.UsernameCheck
     */
    List<String> getUsernames(int minNum, int maxNum);

    /**
     * Send a popup to the user containing <code>message</code>.
     *
     * @param message the message to send to the user
     */
    void sendPopup(String message);

}
