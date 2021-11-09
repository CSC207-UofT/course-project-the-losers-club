package usecases;

import java.util.ArrayList;
import java.util.List;

import entities.Deck;
import entities.Card;

public abstract class GameTemplate {

    protected Player[] players;
    protected Deck deck;
    protected Player currPlayer;
    protected int currPlayerIndex;
    protected static final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    protected static final char[] SUITS = {'H', 'S', 'D', 'C'};

    protected GameTemplate(int numPlayers){
        this.players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            Player newPlayer = new Player("Player " + (i + 1));
            this.players[i] = newPlayer;
        }
        this.currPlayer = this.players[0];

        // Creates a deck with the cards created outside the Deck class
        List<Card> cardList = new ArrayList<>();
        for (String i : RANKS){
            for (char j : SUITS){
                cardList.add(new Card(i, j));
            }
        }
        this.deck = new Deck(cardList);
    }

    public abstract void startGame();


    public static GameTemplate GameFactory(String name, int numPlayers, Input input, Output output) {
//        switch (name.toUpperCase()) {
//            case "CRAZY EIGHTS":
//                return new CrazyEights(numPlayers, input, output);
////            case "WAR":
////                return new War(...);  // TODO ADD WAR WHEN IMPLEMENTED
//            default:
//                return null;
//        }

        return new CrazyEights(numPlayers, input, output);
    }

    /**
     * Input is an interface allowing Games to retrieve input from a user.
     */
    public interface Input {

        /**
         * Implementations should return a String corresponding to a picked card.
         * <p>
         * The first character of the String should be the suit (one of 'C', 'D', 'H', 'S'), while the
         * second and possibly third characters should be
         * the rank (one of "A", "2", "3", "4", "5", "6", "7", "8', "9", "10", "J", "Q", "K").
         *
         * @return a 2-3 character String representing the picked card.
         */
        String getCard();

        /**
         * Implementations should return a boolean representing whether a card should be drawn from the deck.
         *
         * @return true if a card should be drawn, false otherwise
         */
        boolean drawCard();

        /**
         * Implementations should return a character corresponding to a picked suit.
         * <p>
         * The character must be one of {'C', 'D', 'H', 'S'} representing Clubs, Diamonds, Hearts, Spades respectively.
         */
        char getSuit();

        /**
         * Implementations should return a string corresponding to a picked rank.
         * The string must be one of {"A", "2", "3", "4", "5", "6", "7", "8', "9", "10", "J", "Q", "K"}.
         */
        String getRank();

        /**
         * Implementation should return a Player object corresponding to a picked player.
         * @return a Player that is chosen by the user.
         */
        Player getPlayer(Player currPlayer, Player[] players);

        /**
         * Implementations should stall the output display. This can be used when the user needs to "click to continue"
         * or in the case of a command line interface, "press enter to continue".
         */
        boolean stall();

    }

    /**
     * Output is an interface allowing Games to output back to the user.
     */
    public interface Output {

        /**
         * Implementations should take the given Object s and handle it's output to the user.
         * How this is done depends on the implementation.
         *
         * @param s An Object that can be somehow outputted to the user.
         */
        void sendOutput(Object s);
    }
}
