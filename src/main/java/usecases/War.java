package usecases;

import entities.Card;
import usecases.IOInterfaces.WarIO;
import usecases.usermanagement.UserManager;

import java.util.*;

public class War extends GameTemplate {
    private final static String[] HIERARCHY = new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final static int MIN_PLAYERS = 2;
    private final static int MAX_PLAYERS = 2;
    private final ArrayList<Stack<Card>> PLAYING_FIELD = new ArrayList<>(Arrays.asList(new Stack<>(), new Stack<>()));
    private final WarIO WAR_IO;

    /**
     * Constructor for War. Note that usernames must be a List of length 2
     *
     * @param usernames   the list of usernames of player that are playing the game
     * @param userManager user management vessel
     * @param warIO   A WarIO object allowing for player input and game visualization.
     * @param random      a random object for the purposes of game seeding
     */
    public War(List<String> usernames, UserManager userManager, WarIO warIO, Random random) {
        super(usernames, userManager, warIO);
        this.WAR_IO = warIO;
        this.currPlayerIndex = 0;
        this.deck.shuffle(random);
        for (Player player : this.players) {
            for (int i = 0; i < 26; i++) {
                player.addToHand(this.deck.drawCard());
            }
        }
    }

    /**
     * Instantiate a new War game instance.
     *
     * @param usernames   the list of usernames of player that are playing the game
     * @param userManager user management vessel
     * @param warIO   A WarIO object allowing for player input and game visualization.
     */
    public War(List<String> usernames, UserManager userManager, WarIO warIO) {
        this(usernames, userManager, warIO, new Random());
    }

    /**
     * Return's this game's maximum number of players allowed to play the game.
     *
     * @return the maximum number of players.
     */
    public static int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    /**
     * Return's this game's minimum number of players needed to play the game.
     *
     * @return the maximum number of players.
     */
    public static int getMinPlayers() {
        return MIN_PLAYERS;
    }

    /**
     * Return a String representation of this class.
     *
     * @return the String "War"
     */
    @Override
    public String toString() {
        return "War";
    }

    /**
     * The main part of the game that shows players their hands, takes any input, and then acts according to the rules
     * of the game of war. if/when the game finishes, the function also determines the winner.
     */
    @Override
    public void startGame() {
        boolean inWar = false;
        while (!checkWin()) {
            this.currPlayer = this.players[this.currPlayerIndex];
            if (this.PLAYING_FIELD.get(0).isEmpty()) {
                this.WAR_IO.displayBoard("", "", PLAYING_FIELD.get(0).size(), this.players[0].getUsername(), this.players[1].getUsername());
            } else {
                this.WAR_IO.displayBoard(PLAYING_FIELD.get(0).get(0).toString(), PLAYING_FIELD.get(1).get(0).toString(), PLAYING_FIELD.get(0).size(), this.players[0].getUsername(), this.players[1].getUsername());
            }

            this.WAR_IO.stall();

            flipCards();
            if (!checkWin()) {
                if (inWar) {
                    flipCards();
                }
            }

            Card topCard0 = this.returnTopCard(0);
            Card topCard1 = this.returnTopCard(1);

            this.WAR_IO.displayBoard(topCard0.toString(), topCard1.toString(), PLAYING_FIELD.get(0).size(), this.players[0].getUsername(), this.players[1].getUsername());


            int winner = decideRoundWinner(topCard0, topCard1, inWar);

            if (winner == 2) {
                inWar = true;
            } else if (winner < 2) {
                for (int j = 0; j < 2; j++) {
                    while (!PLAYING_FIELD.get(j).empty()) {
                        players[winner].addToHand(PLAYING_FIELD.get(j).pop());
                    }
                    inWar = false;
                }
            }

            this.WAR_IO.stall();
        }

        if (players[0].isHandEmpty() && players[1].isHandEmpty()) {
            this.WAR_IO.closeMessage("Somehow you two have managed to end up in an extremely improbable draw. Congratulations!\n");
            this.addUserStats(this.usernames);
        } else if (players[1].isHandEmpty()) {
            this.WAR_IO.closeMessage(players[1].getUsername() + " is out of cards and can no longer participate. " + players[0].getUsername() + " wins!\n");
            this.addUserStats(players[0].getUsername());
        } else if (players[0].isHandEmpty()) {
            this.WAR_IO.closeMessage(players[0].getUsername() + " is out of cards and can no longer participate. " + players[1].getUsername() + " wins!\n");
            this.addUserStats(players[1].getUsername());
        } else {
            this.WAR_IO.closeMessage("how did you get here. " + PLAYING_FIELD.get(0).size() + " " + PLAYING_FIELD.get(1).size() + "\n");
        }
    }

    /**
     * Flips a card for each player
     */
    protected void flipCards() {
        makeMove(currPlayer.getHand().removeCard());
        currPlayerIndex = 1;
        currPlayer = players[currPlayerIndex];
        makeMove(currPlayer.getHand().removeCard());
        currPlayerIndex = 0;
        currPlayer = players[currPlayerIndex];
    }

    /**
     * Returns the top card of the player's pile
     *
     * @param playerIndex the index representing the player's pile we want to return
     */
    protected Card returnTopCard(int playerIndex) {
        return this.PLAYING_FIELD.get(playerIndex).peek();
    }

    /**
     * Uses Hierarchy to decide which player wins a round of War. returns which player has won (0 or 1) or a 2 if
     * the players tie. Outputs a message accordingly.
     */
    protected int decideRoundWinner(Card topCard0, Card topCard1, boolean inWar) {
        int topCardHierarchy0 = java.util.Arrays.asList(HIERARCHY).indexOf(topCard0.getRank());
        int topCardHierarchy1 = java.util.Arrays.asList(HIERARCHY).indexOf(topCard1.getRank());

        if (topCardHierarchy0 == topCardHierarchy1) {
            if (inWar) {
                this.WAR_IO.sendPopup("Both players have flipped the same rank of card, so war continues!\n");
            } else {
                this.WAR_IO.sendPopup("Both players have flipped the same rank of card, so war begins!\n");
            }
            return 2;
        } else if (topCardHierarchy0 < topCardHierarchy1) {
            this.WAR_IO.sendPopup(players[1].getUsername() + "'s card outranks their opponents and they win the battle!\n");
            return 1;
        } else {
            this.WAR_IO.sendPopup(players[0].getUsername() + "'s card outranks their opponents and they win the battle!\n");
            return 0;
        }
    }

    /**
     * Plays the given card into the playingField for the current player
     *
     * @param card A card object that will be played in the game
     */
    protected void makeMove(Card card) {
        this.PLAYING_FIELD.get(currPlayerIndex).push(card);
    }

    /**
     * Checks if the game has finished, i.e. if at least 1 player has an empty hand
     *
     * @return true if either player has an empty hand, false otherwise
     */
    private boolean checkWin() {
        return (players[0].isHandEmpty() || players[1].isHandEmpty());
    }
}
