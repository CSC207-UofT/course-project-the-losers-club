package usecases.IOInterfaces;

import java.util.List;

public interface GoFishIO {

    /**
     * This method should change the current user to <>username</> and then informer the user of this change.
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
     * This method should display the user's hand.
     *
     * @param hand a string representation of a hand of cards.
     */
    void showHand(String hand);

    /**
     * This method should ask the user which rank of card they would like to request from the other players.
     * The string must be one of {"A", "2", "3", "4", "5", "6", "7", "8', "9", "10", "J", "Q", "K"}.
     * @return a string representation of the chosen rank
     */
    String getRank();

    /**
     * This method should ask the user to choose a player to request the chosen rank from.
     * @param currPlayer a string representation of the current player's username and is not in the list of usernames
     *                   displayed to the user
     * @param usernames a list of all the player usernames in the game
     * @return a string representation of the username selected by the user
     */
    String getPlayerUsername(String currPlayer, List<String> usernames);
}
