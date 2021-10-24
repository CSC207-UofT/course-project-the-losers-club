package usecases;

import controllers.InOut;
import entities.Card;
import entities.Hand;

import java.util.List;
import java.util.Stack;

public class CrazyEights extends Game{
    private final Stack<Card> playingField;
    private final InOut controller;

    /**
     *
     * @param numPlayers The number of players playing the game.
     * @param controller An interface that communicates with the players through a command line.
     */
    public CrazyEights(int numPlayers, InOut controller) {
        super(numPlayers);
        this.controller = controller;
        this.currPlayerIndex = 0;
        this.playingField = new Stack<>();
        this.deck.shuffle();
        for (Player player : this.players) {
            for (int i=0; i < 5; i++) {
                player.addToHand(this.deck.drawCard());
            }
        }
        this.playingField.add(this.deck.drawCard());
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
            this.controller.sendOutput("---------------------------------------");
            this.controller.sendOutput(this.currPlayer.getName() + "'s Turn");
            this.controller.sendOutput("---------------------------------------");
            this.controller.sendOutput("Top card: " + this.playingField.peek());
            this.controller.sendOutput(this.currPlayer.getName() + "'s Hand: " + this.currPlayer.playerHandString());

            do {
                if (looped){
                    this.controller.sendOutput("This is not a valid move.");
                    card = null;
                }

                if (!hasValidMove(currPlayer.getHand())){
                    this.controller.sendOutput("Card drawn from Deck because there are no cards to play.");
                }
                else if (!controller.drawCard()) {
                    crd = this.controller.getCard();
                    card = new Card(crd.substring(1), crd.charAt(0));
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
            this.controller.sendOutput("");
        }
        this.controller.sendOutput(this.currPlayer.getName() + " Wins!!!");
    }

    /**
     * Checks if the given card is a valid move or an invalid move
     * @param card A card object that will be checked if it is a valid move
     * @return true if card is a valid move, false otherwise
     */
    @Override
    public boolean checkMove(Card card) {
        if (this.currPlayer.playerHandEmpty()) {
            return false;
        } else {
            return (card.getSuit() == this.playingField.peek().getSuit()) || card.getRank().equals(this.playingField.peek().getRank());
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
            if (card.getSuit() == this.playingField.peek().getSuit() || card.getRank().equals(this.playingField.peek().getRank())){
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
    }

    /**
     * Checks if a player has won the game, i.e. if the player has an empty hand
     * @return true if player has an empty hand, false otherwise
     */
    @Override
    public boolean checkWin() {
        return currPlayer.playerHandEmpty();
    }
}
