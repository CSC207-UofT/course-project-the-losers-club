package usecases;

import entities.Card;
import entities.Hand;

import java.util.List;
import java.util.Stack;

public class CrazyEights extends GameTemplate {
    private final Stack<Card> playingField;
    private final Input gameInput;
    private final Output gameOutput;
    private char suitTracker;

    /**
     *
     * @param numPlayers The number of players playing the game.
     * @param gameInput A Game.Input object allowing for player input.
     * @param gameOutput A Game.Output object allowing for output to the player.
     */
    public CrazyEights(int numPlayers, Input gameInput, Output gameOutput) {
        super(numPlayers);
        this.gameInput = gameInput;
        this.gameOutput = gameOutput;
        this.currPlayerIndex = 0;
        this.playingField = new Stack<>();
        this.deck.shuffle();
        for (Player player : this.players) {
            for (int i=0; i < 5; i++) {
                player.addToHand(this.deck.drawCard());
            }
        }
        this.playingField.add(this.deck.drawCard());
        this.suitTracker = this.playingField.peek().getSuit();
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
            this.gameOutput.sendOutput("---------------------------------------\n");
            this.gameOutput.sendOutput(this.currPlayer.getName() + "'s Turn\n");
            this.gameOutput.sendOutput("---------------------------------------\n");
            this.gameOutput.sendOutput("Top card: " + this.playingField.peek().getRank() + this.suitTracker + "\n");
            this.gameOutput.sendOutput(this.currPlayer.getName() + "'s Hand: " + this.currPlayer.getHandString() + "\n");

            do {
                if (looped){
                    this.gameOutput.sendOutput("This is not a valid move.\n");
                    card = null;
                }

                if (!hasValidMove(currPlayer.getHand())){
                    this.gameOutput.sendOutput("Card drawn from Deck because there are no cards to play.\n");
                }
                else if (!this.gameInput.drawCard()) {
                    crd = this.gameInput.getCard();
                    card = new Card(crd.substring(1), crd.charAt(0));
                    if (card.getRank().equals("8")) {
                        this.suitTracker = this.gameInput.getSuit();
                    }
                }

                if (card != null && !checkMove(card)){
                    looped = true;
                }
            } while (card != null && !checkMove(card));
            if (card == null) {
                currPlayer.addToHand(deck.drawCard());
            } else {
                makeMove(card);
            }
            this.currPlayerIndex = (this.currPlayerIndex + 1) % this.players.length;
            this.gameOutput.sendOutput("\n");
        }
        this.gameOutput.sendOutput(this.currPlayer.getName() + " Wins!!!\n");
    }

    /**
     * Checks if the given card is a valid move or an invalid move
     * @param card A card object that will be checked if it is a valid move
     * @return true if card is a valid move, false otherwise
     */
    @Override
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
                return (card.getSuit() == this.suitTracker) || card.getRank().equals(this.playingField.peek().getRank());
            }
        }
    }

    /**
     * Checks if a hand has at least one card that is a valid move
     * @param hand A hand object of a player playing the game
     * @return true if the hand has a valid move, false otherwise
     */
    public boolean hasValidMove(Hand hand){
        List<Card> cards = hand.getCards();
        for (Card card : cards){
            if (card.getSuit() == this.suitTracker || card.getRank().equals(this.playingField.peek().getRank()) || card.getRank().equals("8")){
                return true;
            }
        }
        return false;
    }

    /**
     * Plays the given card into the playingField
     * @param card A card object that will be played in the game
     */
    @Override
    public void makeMove(Card card) {
        this.playingField.add(card);
        this.currPlayer.removeFromHand(card);
        if (!card.getRank().equals("8")) {
            this.suitTracker = card.getSuit();
        }
    }

    /**
     * Checks if a player has won the game, i.e. if the player has an empty hand
     * @return true if player has an empty hand, false otherwise
     */
    @Override
    public boolean checkWin() {
        return currPlayer.isHandEmpty();
    }
}
