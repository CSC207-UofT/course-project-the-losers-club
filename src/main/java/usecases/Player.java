package usecases;

import entities.Card;
import entities.Hand;

public class Player {
    private Hand hand;
    private String name;

    public Player() {
    }

    public void addToHand(Card card) {
    }

    public void removeFromHand(Card card) {
    }

    // Return this player's hand
    public Hand getHand() {
        return this.hand;
    }
}
