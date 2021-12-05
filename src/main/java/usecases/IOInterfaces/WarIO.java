package usecases.IOInterfaces;

/**
 * This class defines an interface for War. Any class that implements this interface can be given to the War
 * constructor and be used to play War.
 */
public interface WarIO {
    /**
     * This method should change the current user to <>username</> and then informers the user of this change.
     *
     * @param username The <>username</> of the next player.
     */
    void changePlayer(String username);

    /**
     * This method should display the new top card to the user.
     *
     * @param card a string representation of the card to be displayed.
     */
    void showTopCard(String card);

    /**
     * This method should send a popup to the user containing a <>message</>.
     *
     * @param message a string that is to be sent to the user
     */
    void sendPopup(String message);

    /**
     * This method should close the GUI when called.
     */
    void close();

    /**
     * This method should display a <>message</> to the user and then close the GUI.
     *
     * @param message a string that is to be sent to the user.
     */
    void closeMessage(String message);
}
