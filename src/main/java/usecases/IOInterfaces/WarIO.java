package usecases.IOInterfaces;

/**
 * This class defines an interface for War. Any class that implements this interface can be given to the War
 * constructor and be used to play War.
 */
public interface WarIO extends GameIO {
    /**
     * This method should change the current user to <code>username</code> and then informers the user of this change.
     *
     * @param username The <code>username</code> of the next player.
     */
    void changePlayer(String username);

    /**
     * This method should display the top of both of the War piles.
     *
     * @param card1     a string representation of the card on the first War pile to be displayed. If this pile is empty, this will be the empty string.
     * @param card2     a string representation of the card on the second War pile to be displayed. If this pile is empty, this will be the empty string.
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

}
