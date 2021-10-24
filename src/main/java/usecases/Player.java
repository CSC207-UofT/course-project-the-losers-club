package usecases;

import entities.Card;
import entities.Hand;

/***
 * Player class represents a player that plays implementations of Game. Each player contains a hand,
 * which is the cards the player holds at a given moment.
 */
public class Player {
    private Hand hand;

    public String getName() {
        return name;
    }

    private String name;

    public Player(String name) {
        this.hand = new Hand();
        this.name = name;
    }

    /**
     * adds a card to the hand of this player
     *
     * @param card a card object to add to the hand
     */
    public void addToHand(Card card) {
        this.hand.addCard(card);
    }

    /**
     * removes a card that is present in the hand.
     * assumes that the card to be removed is in hand
     *
     * @param card a card object to be removed from hand; assumes that card is in hand
     */
    public void removeFromHand(Card card) {
        this.hand.removeCard(card);
    }

    public Hand getHand() {
        return this.hand;
    }

    public boolean playerHandEmpty(){
        return this.hand.isEmpty();
    }

    public String playerHandString(){
        return this.hand.toString();
    }
}
