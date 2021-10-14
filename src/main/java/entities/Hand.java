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

    // Removes the card specified by the hashcode if it is in the hand. Returns the card object if it is removed and null otherwise.
    public Card removeCard(int hashcode) {
        //TODO: implement remove card depending on hashcode format.
        return new Card("5", 'H');
    }

    // Returns the size of the hand.
    public int getSize() {
        return cards.size();
    }

    public String toString() {
        return this.cards.toString();
    }
}
