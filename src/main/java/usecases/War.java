package usecases;

import entities.Card;
import usecases.usermanagement.UserDatabaseAccess;
import usecases.usermanagement.UserManager;

import java.util.*;

public class War extends GameTemplate {
    private final static String[] HIERARCHY = new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final static int MIN_PLAYERS = 2;
    private final static int MAX_PLAYERS = 2;
    private final ArrayList<Stack<Card>> PLAYING_FIELD = new ArrayList<>(Arrays.asList(new Stack<>(), new Stack<>()));
    private final Input GAME_INPUT;
    private final Output GAME_OUTPUT;

    /**
     * Constructor for War. Note that usernames must be a List of length 2
     *
     * @param usernames   the list of usernames of player that are playing the game
     * @param userManager user management vessel
     * @param gameInput   A GameTemplate.Input object allowing for player input.
     * @param gameOutput  A GameTemplate.Output object allowing for output to the player.
     * @param random      a random object for the purposes of game seeding
     */
    public War(List<String> usernames, UserManager userManager, Input gameInput, Output gameOutput, Random random) {
        super(usernames, userManager, gameInput, gameOutput);
        this.GAME_INPUT = gameInput;
        this.GAME_OUTPUT = gameOutput;
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
     * @param gameInput   A GameTemplate.Input object allowing for player input.
     * @param gameOutput  A GameTemplate.Output object allowing for output to the player.
     */
    public War(List<String> usernames, UserManager userManager, Input gameInput, Output gameOutput) {
        this(usernames, userManager, gameInput, gameOutput, new Random());
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
        int turnCounter = 0;
        boolean inWar = false;
        while (!checkWin()) {
            turnCounter += 1;
            this.currPlayer = this.players[this.currPlayerIndex];
            this.GAME_OUTPUT.sendOutput("---------------------------------------\n");
            this.GAME_OUTPUT.sendOutput("Turn " + turnCounter + ". Pile sizes: " + PLAYING_FIELD.get(0).size() + "\n");
            this.GAME_OUTPUT.sendOutput("---------------------------------------\n");
            for (int i = 0; i < 2; i++) {
                if (PLAYING_FIELD.get(i).empty()) {
                    this.GAME_OUTPUT.sendOutput(players[i].getUsername() + "'s Pile is empty\n");
                } else {
                    this.GAME_OUTPUT.sendOutput(players[i].getUsername() + "'s Top Card is: " + PLAYING_FIELD.get(i).peek() + "\n");
                }
                this.GAME_OUTPUT.sendOutput("---------------------------------------\n");
            }
            GAME_INPUT.stall();

            flipCards();
            if (!checkWin()) {
                if (inWar) {
                    flipCards();
                }
            }

            Card topCard0 = this.returnTopCard(0);
            Card topCard1 = this.returnTopCard(1);

            this.GAME_OUTPUT.sendOutput("---------------------------------------\n");
            this.GAME_OUTPUT.sendOutput(players[0].getUsername() + " flipped the card " + topCard0 + "\n");
            this.GAME_OUTPUT.sendOutput(players[1].getUsername() + " flipped the card " + topCard1 + "\n");

            int winner = decideRoundWinner(topCard0, topCard1, inWar);

            if (winner == 2) {
                inWar = true;
            } else if (winner < 2) {
                for (int j = 0; j < 2; j++) {
                    while (!PLAYING_FIELD.get(j).empty()) {
                        players[winner].addToHand(PLAYING_FIELD.get(j).pop());
                    }
                }
            }

            this.GAME_OUTPUT.sendOutput("---------------------------------------\n");

            GAME_INPUT.stall();
        }

        if (players[0].isHandEmpty() && players[1].isHandEmpty()) {
            this.GAME_OUTPUT.sendOutput("Somehow you two have managed to end up in an extremely improbable draw. Congratulations!\n");
            this.addUserStats(this.usernames);
        } else if (players[1].isHandEmpty()) {
            this.GAME_OUTPUT.sendOutput(players[1].getUsername() + " is out of cards and can no longer participate. " + players[0].getUsername() + " wins!\n");
            this.addUserStats(players[0].getUsername());
        } else if (players[0].isHandEmpty()) {
            this.GAME_OUTPUT.sendOutput(players[0].getUsername() + " is out of cards and can no longer participate. " + players[1].getUsername() + " wins!\n");
            this.addUserStats(players[1].getUsername());
        } else {
            this.GAME_OUTPUT.sendOutput("how did you get here. " + PLAYING_FIELD.get(0).size() + " " + PLAYING_FIELD.get(1).size() + "\n");
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
                this.GAME_OUTPUT.sendOutput("Both players have flipped the same rank of card, so war continues!\n");
            } else {
                this.GAME_OUTPUT.sendOutput("Both players have flipped the same rank of card, so war begins!\n");
            }
            return 2;
        } else if (topCardHierarchy0 < topCardHierarchy1) {
            this.GAME_OUTPUT.sendOutput(players[1].getUsername() + "'s card outranks their opponents and they win the battle!\n");
            return 1;
        } else {
            this.GAME_OUTPUT.sendOutput(players[0].getUsername() + "'s card outranks their opponents and they win the battle!\n");
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
