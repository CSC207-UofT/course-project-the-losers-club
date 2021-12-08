package usecases.IOInterfaces;

/**
 * Abstract IO interface which the game specific interfaces extend
 */
public interface GameIO {
    /**
     * This method should send a popup to the user containing a <code>message</code>.
     *
     * @param message a string that is to be sent to the user
     */
    void sendPopup(String message);

    /**
     * This method should display a <code>message</code> to the user and then close the GUI.
     *
     * @param message a string that is to be sent to the user.
     */
    void closeMessage(String message);

    /**
     * This method should close the GUI when called.
     */
    void close();
}
