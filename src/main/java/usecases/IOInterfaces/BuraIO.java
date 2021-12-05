package usecases.IOInterfaces;

public interface BuraIO {

    /**
     * This method should change the current user to <>username</> and then informers the user of this change.
     *
     * @param username The <>username</> of the next player.
     */
    void changePlayer(String username);

    /**
     * This method should send a popup to the user containing a <>message</>.
     *
     * @param message a string that is to be sent to the user
     */
    void sendPopup(String message);

    /**
     * This method should display the top card to the user and indicate that this is the card to beat.
     *
     * @param card a string representation of the card to beat.
     */
    void showCardToBeat(String card);

    /**
     * This method should display to the user which suit is the trump suit.
     *
     * @param trump a char representation of a suit
     */
    void showTrumpSuit(char trump);

    /**
     * This method should display the user's hand.
     *
     * @param hand a string representation of a hand of cards.
     */
    void showHand(String hand);

    /**
     * This method should return a string representation of the card that is selected by the user.
     *
     * @return a string representation of the card selected by the user.
     */
    String getCard();

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
