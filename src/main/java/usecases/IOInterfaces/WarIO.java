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
     * This method should display the top of both of the War piles.
     *
     * @param card1     a string representation of the card on the first War pile to be displayed.
     * @param card2     a string representation of the card on the second War pile to be displayed.
     * @param pileSize  an int representation of the size of the War piles.
     * @param username1 a string representation of the one of the player's username.
     * @param username2 a string representation of the other player's username.
     */
    void displayBoard(String card1, String card2, int pileSize, String username1, String username2);

    /**
     * This method takes a keyboard or mouse input from the user and continues the game once the input is received.
     * Acts to stall the game so that the players can look at the current state of the game.
     */
    void stall();

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
