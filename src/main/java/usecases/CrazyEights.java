package usecases;

import entities.Card;
import entities.Hand;
import presenters.gui.PlayerGUI;
import presenters.gui.SingleCardGUI;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class CrazyEights extends GameTemplate {
    private static final int MAX_PLAYERS = 5;
    private static final int MIN_PLAYERS = 2;
    private final Stack<Card> PLAYING_FIELD;
    private final PlayerGUI PLAYER_GUI;
    private final SingleCardGUI SINGLE_CARD_GUI;
    private char suitTracker;

    /**
     * Instantiate a new CrazyEights game instance.
     *
     * @param usernames   the list of usernames of player that are playing the game
     * @param userManager a usermanager that manages the user entities
     * @param playerGUI   A PlayerGUI object allowing for player input and hand visualization.
     * @param singleCardGUI  A SingleCardGUI object allowing for the top card visualization.
     */
    public CrazyEights(List<String> usernames, UserManager userManager, PlayerGUI playerGUI, SingleCardGUI singleCardGUI) {
        this(usernames, userManager, playerGUI, singleCardGUI, new Random());
    }

    /**
     * Instantiate a new CrazyEights game instance. This constructor allows the deck to be seeded with a state.
     *
     * @param usernames   the list of usernames of player that are playing the game
     * @param userManager a usermanager that manages the user entities
     * @param playerGUI   A PlayerGUI object allowing for player input and hand visualization.
     * @param singleCardGUI  A SingleCardGUI object allowing for the top card visualization.
     * @param rand        a Random object for creating deterministic behaviour
     */
    public CrazyEights(List<String> usernames, UserManager userManager,
                       PlayerGUI playerGUI, SingleCardGUI singleCardGUI, Random rand) {
        super(usernames, userManager);
        this.PLAYER_GUI = playerGUI;
        this.SINGLE_CARD_GUI = singleCardGUI;
        this.currPlayerIndex = 0;
        this.PLAYING_FIELD = new Stack<>();
        this.deck.shuffle(rand);
        for (Player player : this.players) {
            for (int i = 0; i < 5; i++) {
                player.addToHand(this.deck.drawCard());
            }
        }
        this.PLAYING_FIELD.add(this.deck.drawCard());
        this.suitTracker = this.PLAYING_FIELD.peek().getSuit();
    }

    /**
     * Return's this game's maximum number of players.
     *
     * @return the maximum number of players.
     */
    protected static int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    /**
     * Return's this game's minimum number of players required to play the game.
     *
     * @return the maximum number of players.
     */
    protected static int getMinPlayers() {
        return MIN_PLAYERS;
    }

    /**
     * Return a String representation of this class.
     *
     * @return the String "Crazy Eights"
     */
    @Override
    public String toString() {
        return "Crazy Eights";
    }

    /**
     * The main part of the game that shows players their hands, takes in their answers, determines if a move is valid
     * or invalid, plays the move, and determines a winner.
     */
    @Override
    public void startGame() {
        while (!checkWin()) {
            this.currPlayer = this.players[this.currPlayerIndex];
            Card card = null;
            String crd;
            boolean looped = false;
//            this.GAME_OUTPUT.sendOutput("---------------------------------------\n");
//            this.GAME_OUTPUT.sendOutput(this.currPlayer.getUsername() + "'s Turn\n");
//            this.GAME_OUTPUT.sendOutput("---------------------------------------\n");
            this.PLAYER_GUI.changePlayer(currPlayer.getUsername());
            this.SINGLE_CARD_GUI.sendOutput((this.PLAYING_FIELD.peek().getRank() + this.suitTracker));
            System.out.println(this.currPlayer.getHandStringFormatted());
            this.PLAYER_GUI.sendOutput(this.currPlayer.getHandStringFormatted());
//            this.GAME_OUTPUT.sendOutput("Top card: " + this.PLAYING_FIELD.peek().getRank() + this.suitTracker + "\n");
//            this.GAME_OUTPUT.sendOutput(this.currPlayer.getUsername() + "'s Hand: " + this.currPlayer.getHandString() + "\n");

            do {
                if (looped) {
                    this.PLAYER_GUI.sendPopup("This is not a valid move.");
                    card = null;
                }

                System.out.println(this.suitTracker);

                if (!hasValidMove(currPlayer.getHand())) {
                    this.PLAYER_GUI.sendPopup("Card drawn from Deck because there are no cards to play.");
                } else if (!this.PLAYER_GUI.drawCard()) {
                    crd = this.PLAYER_GUI.getCard().toUpperCase();
                    card = new Card(crd.substring(0, 1), crd.charAt(1));
                    if (card.getRank().equals("8")) {
                        this.suitTracker = Character.toUpperCase(this.PLAYER_GUI.getSuit());
                    }
                }

                if (card != null && !checkMove(card)) {
                    looped = true;
                }
            } while (card != null && !checkMove(card));
            if (card == null) {
                currPlayer.addToHand(deck.drawCard());
            } else {
                makeMove(card);
            }
            this.currPlayerIndex = (this.currPlayerIndex + 1) % this.players.length;
        }
        for (String u : this.usernames) {
            if (this.currPlayer.getUsername().equals(u)) {
                try {
                    this.userManager.addGamesPlayed(u, 1);
                } catch (UserManager.UserNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    this.userManager.addGamesPlayed(u, -1);
                } catch (UserManager.UserNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        this.PLAYER_GUI.close(this.currPlayer.getUsername() + " Wins!!!");
        this.SINGLE_CARD_GUI.close();
    }

    /**
     * Checks if the given card is a valid move or an invalid move
     *
     * @param card A card object that will be checked if it is a valid move
     * @return true if card is a valid move, false otherwise
     */
    public boolean checkMove(Card card) {
        if (this.currPlayer.isHandEmpty()) {
            return false;
        } else if (card.getRank().equals("8")) {
            return true;
        } else {
            int ind = currPlayer.getHand().getCards().indexOf(card);
            if (ind == -1) {
                return false;
            } else {
                return (card.getSuit() == this.suitTracker) || card.getRank().equals(this.PLAYING_FIELD.peek().getRank());
            }
        }
    }

    /**
     * Checks if a hand has at least one card that is a valid move
     *
     * @param hand A hand object of a player playing the game
     * @return true if the hand has a valid move, false otherwise
     */
    public boolean hasValidMove(Hand hand) {
        List<Card> cards = hand.getCards();
        for (Card card : cards) {
            if (card.getSuit() == this.suitTracker || card.getRank().equals(this.PLAYING_FIELD.peek().getRank()) || card.getRank().equals("8")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Plays the given card into the playingField
     *
     * @param card A card object that will be played in the game
     */
    public void makeMove(Card card) {
        this.PLAYING_FIELD.add(card);
        this.currPlayer.removeFromHand(card);
        if (!card.getRank().equals("8")) {
            this.suitTracker = card.getSuit();
        }
    }

    /**
     * Checks if a player has won the game, i.e. if the player has an empty hand
     *
     * @return true if player has an empty hand, false otherwise
     */
    public boolean checkWin() {
        return currPlayer.isHandEmpty();
    }
}
