package usecases;

import entities.Card;
import presenters.console.Input;
import presenters.console.Output;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class War extends GameTemplate {
    public final ArrayList[] playingField = new ArrayList[]{new ArrayList<Card>(), new ArrayList<Card>()};
    private final Input gameInput;
    private final Output gameOutput;
    private final static String[] Hierarchy = new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

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
            this.gameOutput.sendOutput("---------------------------------------");
            this.gameOutput.sendOutput("Turn " + turnCounter + ". Pile sizes: " + playingField[0].size());
            this.gameOutput.sendOutput("---------------------------------------");
            for (int i = 0; i < 2; i++) {
                if (playingField[i].isEmpty()) {
                    this.gameOutput.sendOutput(players[i].getUsername() + "'s Pile is empty");
                } else {
                    this.gameOutput.sendOutput(players[i].getUsername() + "'s Top Card is: " + playingField[i].get(playingField[i].size() - 1));
                }
                this.gameOutput.sendOutput("---------------------------------------");
            }
            gameInput.stall();

            flipCards();
            if (!checkWin()) {
                if (inWar) {
                    flipCards();
                }
            }

            Card topCard0 = (Card) playingField[0].get(playingField[0].size() - 1);
            Card topCard1 = (Card) playingField[1].get(playingField[1].size() - 1);

            this.gameOutput.sendOutput("---------------------------------------");
            this.gameOutput.sendOutput(players[0].getUsername() + " flipped the card " + topCard0);
            this.gameOutput.sendOutput(players[1].getUsername() + " flipped the card " + topCard1);

            int winner = decideRoundWinner(topCard0, topCard1, inWar);

            if (winner == 2) {
                inWar = true;
            } else if (winner < 2) {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < playingField[j].size(); k++) {
                        players[winner].addToHand((Card) playingField[j].remove(k));
                    }
                }
            }

            this.gameOutput.sendOutput("---------------------------------------");

            gameInput.stall();
        }

        if (players[0].isHandEmpty() && players[1].isHandEmpty()) {
            this.gameOutput.sendOutput("Somehow you two have managed to end up in an extremely improbable draw. Congratulations!");
        } else if (players[1].isHandEmpty()) {
            this.gameOutput.sendOutput(players[1].getUsername() + " is out of cards and can no longer participate. " + players[0].getUsername() + " wins!");
        } else if (players[0].isHandEmpty()) {
            this.gameOutput.sendOutput(players[0].getUsername() + " is out of cards and can no longer participate. " + players[1].getUsername() + " wins!");
        } else {
            this.gameOutput.sendOutput("how did you get here. " + playingField[0].size() + " " + playingField[1].size());
        }
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
     * Uses Hierarchy to decide which player wins a round of War. returns which player has won (0 or 1) or a 2 if
     * the players tie. Outputs a message accordingly.
     */
    public int decideRoundWinner (Card topCard0, Card topCard1, boolean inWar) {
        int topCardHierarchy0 = java.util.Arrays.asList(Hierarchy).indexOf(topCard0.getRank());
        int topCardHierarchy1 = java.util.Arrays.asList(Hierarchy).indexOf(topCard1.getRank());

        if (topCardHierarchy0 == topCardHierarchy1) {
            if (inWar) {
                this.gameOutput.sendOutput("Both players have flipped the same rank of card, so war continues!");
            } else {
                this.gameOutput.sendOutput("Both players have flipped the same rank of card, so war begins!");
            }
            return 2;
        } else if (topCardHierarchy0 < topCardHierarchy1) {
            this.gameOutput.sendOutput(players[1].getUsername() + "'s card outranks their opponents and they win the battle!");
            return 1;
        } else {
            this.gameOutput.sendOutput(players[0].getUsername() + "'s card outranks their opponents and they win the battle!");
            return 0;
        }
    }

    /**
     * Returns true as moves are predetermined in war
     *
     * @param card A card object
     */
    @Override
    public boolean checkMove(Card card) {
        return true;
    }

    /**
     * Plays the given card into the playingField for the current player
     *
     * @param card A card object that will be played in the game
     */
    @Override
    public void makeMove(Card card) {
        this.playingField[currPlayerIndex].add(card);
    }

    /**
     * Checks if the game has finished, i.e. if at least 1 player has an empty hand
     *
     * @return true if either player has an empty hand, false otherwise
     */
    @Override
    public boolean checkWin() {
        return (players[0].isHandEmpty() || players[1].isHandEmpty());
    }
}
