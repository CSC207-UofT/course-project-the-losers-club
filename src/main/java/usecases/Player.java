package usecases;

import entities.Card;
import entities.Hand;

/***
 * Player class represents a player that plays implementations of Game. Each player contains a hand,
 * which is the cards the player holds at a given moment.
 */
public class Player {
    private final Hand hand;
    private final String username;

    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username username of the user that is attached to the player
     */
    public Player(String username) {
        this.hand = new Hand();
        this.username = username;
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

    /**
     * Returns if the player's hand is empty
     *
     * @return a boolean for if the player's hand is empty or not
     */
    public boolean isHandEmpty(){
        return this.hand.isEmpty();
    }

    /**
     * Returns the player's hand as a string
     *
     * @return the player's hand as a string
     */
    public String getHandString(){
        return this.hand.toString();
    }
}
