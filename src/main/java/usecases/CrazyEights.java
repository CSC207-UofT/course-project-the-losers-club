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
//            System.out.print(currPlayer.getHand());
//            System.out.print("Choose a card to play: ");
//            Scanner s = new Scanner(System.in);
//            int cardIndex = s.nextInt();
//            Card card = currPlayer.getHand().getCards().get(cardIndex);
            Card card;
            String crd;
            do {
                crd = this.controller.getCard();
                card = new Card(crd.substring(1), crd.charAt(0));
            } while (!checkMove(card));
            makeMove(card);
            this.currPlayerIndex = (this.currPlayerIndex + 1) % this.players.length;
        }
    }

    @Override
    public boolean checkMove(Card card) {
        return (card.get_suit() == this.playingField.peek().get_suit()) || card.get_rank().equals(this.playingField.peek().get_rank());
    }

    @Override
    public void makeMove(Card card) {
        this.playingField.add(card);
    }

    @Override
    public boolean checkWin() {
        return currPlayer.getHand().getSize() == 0;
    }
}
