package usecases.IOInterfaces;

/**
 * This class defines an interface for the game crazy eights. Any class that implements this interface should be
 * able to be seamlessly swapped out in the crazy eights game code, and it should work perfectly.
 */
public interface CrazyEightsIO extends GameIO {

    /**
     * This method should change the current user to <code>username</code> and then informers the user of this change.
     *
     * @param username The <code>username</code> of the next player.
     */
    void changePlayer(String username);

    /**
     * This method should display the new top card to the user.
     *
     * @param card a string representation of the card to be displayed.
     */
    void showTopCard(String card);

    /**
     * This method should display the user's hand.
     *
     * @param hand a string representation of a hand of cards.
     */
    void showHand(String hand);

    /**
     * This method should send a popup to the user containing a <code>message</code>.
     *
     * @param message a string that is to be sent to the user
     */
    void sendPopup(String message);

    /**
     * This method prompts the user to see if they want to draw a new card from the deck.
     *
     * @return true if the user wants to draw a card, false if the user does not want to draw a card.
     */
    boolean drawCard();

    /**
     * This method should return a string representation of the card that is selected by the user.
     *
     * @return a string representation of the card selected by the user.
     */
    String getCard();

    /**
     * This method should prompt the user to pick a suit
     *
     * @return a char representation of the chosen suit
     */
    char getSuit();

    /**
     * This method should close the GUI when called.
     */
    void close();

    /**
     * This method should display a <code>message</code> to the user and then close the GUI.
     *
     * @param message a string that is to be sent to the user.
     */
    void closeMessage(String message);
}
