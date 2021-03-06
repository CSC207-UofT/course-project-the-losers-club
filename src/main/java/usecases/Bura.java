package usecases;


import entities.Card;
import entities.Deck;
import usecases.IOInterfaces.BuraIO;
import usecases.usermanagement.UserManager;

import java.util.*;

/**
 * Class that plays the game of Bura. Contains implemented versions of the methods found in GameTemplate with
 * the game Bura in mind, such as the main game loop and sending and receiving input and output. A sample ruleset for
 * Bura can be found here: https://www.pagat.com/aceten/bura.html.
 * <p>
 * Note that rules have been modified from the original. Instead of being able to play any number of cards from your
 * hand that were the same rank, our implementation only allows you to play one card per trick. Also, game players are
 * not able to call an end to the game when they think they have 31 points. Instead, the game ends once a player has
 * reached 31 points, or when the deck has been emptied.
 */
public class Bura extends GameTemplate {
    protected static final String[] RANKS = {"A", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final static int MIN_PLAYERS = 2;
    private final static int MAX_PLAYERS = 6;
    private static char TRUMP_SUIT;
    protected final HashMap<Player, Integer> SCORE_TRACKER = new HashMap<>();
    protected final Map<String, Integer> ranks = Map.of("A", 11, "K", 4, "10", 10, "Q", 3,
            "J", 2, "9", 0, "8", 0, "7", 0, "6", 0);
    private final BuraIO BURA_IO;
    private final Stack<Card> PLAYING_FIELD = new Stack<>();
    private final Map<String, Integer> rankToPoint = Map.of("A", 9, "K", 8, "10", 5, "Q", 7,
            "J", 6, "9", 4, "8", 3, "7", 2, "6", 1);

    /**
     * Instantiate a new Bura game instance.
     *
     * @param usernames   the list of usernames of players that are playing the game
     * @param userManager a <code>UserManager</code> that manages the user entities
     * @param buraIO      A BuraIO object allowing for player input/output and game visualization.
     */
    public Bura(List<String> usernames, UserManager userManager, BuraIO buraIO) {
        this(usernames, userManager, buraIO, new Random());
    }

    /**
     * Instantiate a new Bura game instance. This constructor allows the deck to be seeded with a state.
     *
     * @param usernames   the list of usernames of players that are playing the game.
     * @param userManager a <code>UserManager</code> that manages the user entities
     * @param buraIO      A BuraIO object allowing for player input/output and game visualization.
     * @param rand        a Random object for creating deterministic behaviour.
     */
    public Bura(List<String> usernames, UserManager userManager, BuraIO buraIO, Random rand) {
        super(usernames, userManager, buraIO);
        this.BURA_IO = buraIO;
        this.currPlayerIndex = 0;
        List<Card> cardList = new ArrayList<>();
        for (String i : RANKS) {
            for (char j : SUITS) {
                cardList.add(new Card(i, j));
            }
        }
        this.deck = new Deck(cardList);
        this.deck.shuffle(rand);
        for (Player player : this.players) {
            for (int i = 0; i < 3; i++) {
                player.addToHand(this.deck.drawCard());
            }
            this.SCORE_TRACKER.put(player, 0);
        }

        TRUMP_SUIT = this.deck.peek().getSuit();
        this.deck.shuffle(rand);

        this.currPlayerIndex = 0;
    }

    /**
     * Return's this game's maximum number of players.
     *
     * @return the maximum number of players.
     */
    public static int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    /**
     * Return's this game's minimum number of players required to play the game.
     *
     * @return the maximum number of players.
     */
    public static int getMinPlayers() {
        return MIN_PLAYERS;
    }

    /**
     * Return a String representation of this class.
     *
     * @return the String "Bura"
     */
    public String toString() {
        return "Bura";
    }

    /**
     * The main part of the game that shows players their hands, takes in their answers, determines if a move is valid
     * or invalid, plays the move, and determines a winner.
     */
    @Override
    public void startGame() {
        while (!gameEnd()) {
            playRound();
            updateScore();
            restockHands();
        }
        outputWinner();
    }

    /**
     * Plays one round of the game. This means that each player plays a card and the player that played the highest card
     * takes all the cards on the playing field.
     * <p>
     * Precondition: this.currPlayerIndex refers to the index of the player that won the previous round
     * Post-condition: this.currPlayerIndex refers to the index of the player that just won the round
     */
    private void playRound() {
        int startIndex = this.currPlayerIndex;
        int winningPlayerIndex = this.currPlayerIndex;
        String crd;
        boolean loopedRankChoice = false;

        do {
            this.currPlayer = this.players[this.currPlayerIndex];
            this.BURA_IO.changePlayer(this.currPlayer.getUsername());

            if (PLAYING_FIELD.empty()) {
                this.BURA_IO.showCardToBeat("");
                this.BURA_IO.sendPopup("New round started. Play the first card.\n");
            } else {
                this.BURA_IO.showCardToBeat(this.PLAYING_FIELD.peek().toString());
            }

            this.BURA_IO.showTrumpSuit(TRUMP_SUIT);
            this.BURA_IO.showHand(this.currPlayer.getHandStringFormatted());

            do {
                if (loopedRankChoice) {
                    this.BURA_IO.sendPopup("Invalid card chosen. Try again.");
                }

                crd = this.BURA_IO.getCard();
                if (invalidMove(crd)) {
                    loopedRankChoice = true;
                }
            } while (invalidMove(crd));

            if (addCard(crd)) {
                winningPlayerIndex = currPlayerIndex;
            }

            this.currPlayerIndex = (this.currPlayerIndex + 1) % this.players.length;

        } while (currPlayerIndex != startIndex);

        this.BURA_IO.sendPopup(this.players[winningPlayerIndex].getUsername() + " wins the round! \n");
        this.currPlayerIndex = winningPlayerIndex;
    }

    /**
     * Checks if the game has ended. The game ends if a player reaches 31 points or there are no cards
     * remaining to be played.
     *
     * @return true if game has ended, false otherwise
     */
    private boolean gameEnd() {
        int max_score = Collections.max(this.SCORE_TRACKER.values());
        return max_score >= 31 || this.currPlayer.getHand().isEmpty();
    }

    /**
     * Add the chosen card to the playing field. Return whether it beats the top card of the playing field.
     *
     * @param crd the String representation of the card chosen by the User.
     * @return true if the chosen card beats the highest card on the playing field; false otherwise.
     */
    private boolean addCard(String crd) {
        Card chosenCard = this.currPlayer.getHand().removeCard(crd.substring(0, crd.length() - 1), crd.charAt(crd.length() - 1));
        if (this.PLAYING_FIELD.empty()) {
            this.PLAYING_FIELD.push(chosenCard);
        } else {
            Card topCard = this.PLAYING_FIELD.peek();
            if (beatsCard(chosenCard, topCard)) {
                this.PLAYING_FIELD.push(chosenCard);
            } else {
                this.PLAYING_FIELD.pop();
                this.PLAYING_FIELD.push(chosenCard);
                this.PLAYING_FIELD.push(topCard);
            }
        }
        return chosenCard.equals(this.PLAYING_FIELD.peek());
    }

    /**
     * Restock the hand of each player to be 3 cards. If there are not enough cards left in the deck
     * to equally distribute, the hands are not restocks.
     */
    private void restockHands() {
        this.BURA_IO.sendPopup("Round ended! Restocking every player's hand.\n");
        while (this.currPlayer.getHand().getSize() < 3 && this.deck.getSize() >= this.players.length) {
            for (Player player : this.players) {
                player.addToHand(this.deck.drawCard());
            }
        }
    }

    /**
     * Compares card1 and card2 to decide whether card1 beats card2.
     *
     * @param card1 card to be introduced
     * @param card2 card to be beat
     * @return True if card1 beats card2
     */
    boolean beatsCard(Card card1, Card card2) {
        return (card1.getSuit() == (card2.getSuit()) && rankToPoint.get(card1.getRank()) >
                rankToPoint.get(card2.getRank())) || (card1.getSuit() == TRUMP_SUIT && card2.getSuit() != TRUMP_SUIT);
    }

    /**
     * Checks whether the move made was valid. A move is valid if the card is in the player's hand.
     *
     * @param crd move made by the current player
     * @return True if the move is valid, false otherwise
     */
    private boolean invalidMove(String crd) {
        List<Card> cards = this.currPlayer.getHand().getCards();
        for (Card card : cards) {
            String str = card.toString();

            if (str.equals(crd)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the score after a round is played. The score is added to the player that won the round
     * <p>
     * Precondition: currPlayerIndex refers to the player that just won the round
     */
    private void updateScore() {
        int sumScore = 0;
        while (!this.PLAYING_FIELD.empty()) {
            sumScore += ranks.get(this.PLAYING_FIELD.pop().getRank());
        }
        this.SCORE_TRACKER.put(this.players[this.currPlayerIndex],
                this.SCORE_TRACKER.get(this.players[this.currPlayerIndex]) + sumScore);
    }

    /**
     * Outputs the winner based on number of points collected. Also updates user statistics by incrementing
     * games played by 1 and whether it's a win or a loss.
     */
    private void outputWinner() {
        int currMaxScore = 0;
        Player winner = null;
        for (Player player : this.players) {
            if (this.SCORE_TRACKER.get(player) > currMaxScore) {
                currMaxScore = this.SCORE_TRACKER.get(player);
                winner = player;
            }
        }
        assert winner != null;
        this.addUserStats(winner.getUsername());
        this.BURA_IO.closeMessage(String.format("Winner is %s with %s points!\n", winner.getUsername(), currMaxScore));
    }

}