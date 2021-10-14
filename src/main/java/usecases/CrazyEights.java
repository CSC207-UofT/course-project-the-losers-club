package usecases;

import controllers.InOut;
import entities.Card;

import java.util.Stack;

public class CrazyEights extends Game{
    private final Stack<Card> playingField;
    private final InOut controller;

    // Creates a game of CrazyEights, numPlayers must be less than 6
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

    @Override
    public void startGame() {
        while (!checkWin()) {
            this.currPlayer = this.players[this.currPlayerIndex];
            Card card;
            String crd;
            do {
                this.controller.sendOutput("Top card: " + this.playingField.peek());
                this.controller.sendOutput(this.currPlayer.getName() + "'s Hand: " + this.currPlayer.getHand().toString());
                crd = this.controller.getCard();
                card = new Card(crd.substring(1), crd.charAt(0));
            } while (!checkMove(card));
            makeMove(card);
            this.currPlayerIndex = (this.currPlayerIndex + 1) % this.players.length;
            this.controller.sendOutput("");
        }
    }

    @Override
    public boolean checkMove(Card card) {
        int ind = currPlayer.getHand().getCards().indexOf(card);
        if (ind == -1) {
            return false;
        } else {
            return (card.get_suit() == this.playingField.peek().get_suit()) || card.get_rank().equals(this.playingField.peek().get_rank());
        }
    }

    @Override
    public void makeMove(Card card) {
        this.playingField.add(card);
        this.currPlayer.removeFromHand(card);
    }

    @Override
    public boolean checkWin() {
        return currPlayer.getHand().getSize() == 0;
    }
}
