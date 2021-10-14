package entities;
import java.util.List;
import java.util.ArrayList;

public class Hand {
    // Instance attributes
    List<Card> cards;

    // Constructs an empty hand.
    public Hand() {
        this.cards = new ArrayList<>();
    }

    // Constructs a hand that contains the list of cards that is passed.
    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    // Returns the cards in the hand.
    public List<Card> getCards() {
        return this.cards;
    }

    // Adds a card to the hand.
    public void addCard(Card card) {
        cards.add(card);
    }

    // Removes the first card in the hand and returns it.
    public Card removeCard() {
        return cards.remove(0);
    }

    /**
     * Remove and return the given card in this Hand.
     *
     * Assumes the card is in the hand.
     *
     * @param rank rank of the card
     * @param suit suit of the card
     * @return the removed Card
     */
    public Card removeCard(String rank, char suit) {
        Card c = new Card(rank, suit);
        this.removeCard(c);
        return c;
    }

    /**
     * Remove and return the given card in this Hand.
     *
     * Assumes the card is in the hand.
     *
     * @param c the card
     * @return the removed Card
     */
    public Card removeCard(Card c) {
        int i = this.cards.indexOf(c);
        return this.cards.remove(i);
    }

    // Returns the size of the hand.
    public int getSize() {
        return cards.size();
    }

    public String toString() {
        return this.cards.toString();
    }
}
