package usecases;

import entities.Card;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

public class War extends GameTemplate {
    private final ArrayList<Stack<Card>> playingField = new ArrayList<>(Arrays.asList(new Stack<>(), new Stack<>()));
    private final Input gameInput;
    private final Output gameOutput;
    private final static String[] HIERARCHY = new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final static int minPlayers = 2;
    private final static int maxPlayers = 2;

    /**
     * Constructor for War. Note that usernames must be a List of length 2
     *
     * @param gameInput  A Game.Input object allowing for player input.
     * @param gameOutput A Game.Output object allowing for output to the player.
     */
    public War(Input gameInput, Output gameOutput, List<String> usernames, UserManager userManager, Random random) {
        super(usernames, userManager);
        this.gameInput = gameInput;
        this.gameOutput = gameOutput;
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
     * @param userManager a usermanager that manages the user entities
     * @param gameInput   A Game.Input object allowing for player input.
     * @param gameOutput  A Game.Output object allowing for output to the player.
     */
    public War(Input gameInput, Output gameOutput, List<String> usernames, UserManager userManager) {
        this(gameInput, gameOutput, usernames, userManager, new Random());
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
            this.gameOutput.sendOutput("---------------------------------------\n");
            this.gameOutput.sendOutput("Turn " + turnCounter + ". Pile sizes: " + playingField.get(0).size() + "\n");
            this.gameOutput.sendOutput("---------------------------------------\n");
            for (int i = 0; i < 2; i++) {
                if (playingField.get(i).empty()) {
                    this.gameOutput.sendOutput(players[i].getUsername() + "'s Pile is empty\n");
                } else {
                    this.gameOutput.sendOutput(players[i].getUsername() + "'s Top Card is: " + playingField.get(i).peek() + "\n");
                }
                this.gameOutput.sendOutput("---------------------------------------\n");
            }
            gameInput.stall();

            flipCards();
            if (!checkWin()) {
                if (inWar) {
                    flipCards();
                }
            }

            Card topCard0 = this.returnTopCard(0);
            Card topCard1 = this.returnTopCard(1);

            this.gameOutput.sendOutput("---------------------------------------\n");
            this.gameOutput.sendOutput(players[0].getUsername() + " flipped the card " + topCard0 + "\n");
            this.gameOutput.sendOutput(players[1].getUsername() + " flipped the card " + topCard1 + "\n");

            int winner = decideRoundWinner(topCard0, topCard1, inWar);

            if (winner == 2) {
                inWar = true;
            } else if (winner < 2) {
                for (int j = 0; j < 2; j++) {
                    while (!playingField.get(j).empty()) {
                        players[winner].addToHand(playingField.get(j).pop());
                    }
                }
            }

            this.gameOutput.sendOutput("---------------------------------------\n");

            gameInput.stall();
        }

        if (players[0].isHandEmpty() && players[1].isHandEmpty()) {
            this.gameOutput.sendOutput("Somehow you two have managed to end up in an extremely improbable draw. Congratulations!\n");
        } else if (players[1].isHandEmpty()) {
            this.gameOutput.sendOutput(players[1].getUsername() + " is out of cards and can no longer participate. " + players[0].getUsername() + " wins!\n");
        } else if (players[0].isHandEmpty()) {
            this.gameOutput.sendOutput(players[0].getUsername() + " is out of cards and can no longer participate. " + players[1].getUsername() + " wins!\n");
        } else {
            this.gameOutput.sendOutput("how did you get here. " + playingField.get(0).size() + " " + playingField.get(1).size() + "\n");
        }
    }

    /**
     * Return's this game's maximum number of players allowed to play the game.
     * @return the maximum number of players.
     */
    protected static int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Return's this game's minimum number of players needed to play the game.
     * @return the maximum number of players.
     */
    protected static int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Flips a card for each player
     */
    public void flipCards() {
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
    public Card returnTopCard(int playerIndex) {
        return this.playingField.get(playerIndex).peek();
    }

    /**
     * Uses Hierarchy to decide which player wins a round of War. returns which player has won (0 or 1) or a 2 if
     * the players tie. Outputs a message accordingly.
     */
    public int decideRoundWinner(Card topCard0, Card topCard1, boolean inWar) {
        int topCardHierarchy0 = java.util.Arrays.asList(HIERARCHY).indexOf(topCard0.getRank());
        int topCardHierarchy1 = java.util.Arrays.asList(HIERARCHY).indexOf(topCard1.getRank());

        if (topCardHierarchy0 == topCardHierarchy1) {
            if (inWar) {
                this.gameOutput.sendOutput("Both players have flipped the same rank of card, so war continues!\n");
            } else {
                this.gameOutput.sendOutput("Both players have flipped the same rank of card, so war begins!\n");
            }
            return 2;
        } else if (topCardHierarchy0 < topCardHierarchy1) {
            this.gameOutput.sendOutput(players[1].getUsername() + "'s card outranks their opponents and they win the battle!\n");
            return 1;
        } else {
            this.gameOutput.sendOutput(players[0].getUsername() + "'s card outranks their opponents and they win the battle!\n");
            return 0;
        }
    }

    /**
     * Returns true as moves are predetermined in war
     *
     * @param card A card object
     */
    public boolean checkMove(Card card) {
        return true;
    }

    /**
     * Plays the given card into the playingField for the current player
     *
     * @param card A card object that will be played in the game
     */
    public void makeMove(Card card) {
        this.playingField.get(currPlayerIndex).push(card);
    }

    /**
     * Checks if the game has finished, i.e. if at least 1 player has an empty hand
     *
     * @return true if either player has an empty hand, false otherwise
     */
    public boolean checkWin() {
        return (players[0].isHandEmpty() || players[1].isHandEmpty());
    }
}
