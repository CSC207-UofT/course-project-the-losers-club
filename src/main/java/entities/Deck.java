package entities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A standard 52-card playing deck containing 52 Card objects, each representing 1 of the 52 unique cards in a
 * standard deck.
 */
public class Deck {
    // Constants and instance attributes. RANKS is an array of all possible ranks,
    // SUITS is an array of all possible suits
    private List<Card> cards;

    /**
     * Constructs a deck and initializes it with a list of Card objects passed to it
     *
     * @param cards list of Card objects
     */
    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Returns and removes the top card of the deck
     *
     * @return the Card that was just removed
     */
    public Card drawCard() {
        return cards.remove(0);
    }

    /**
     * Shuffles the deck
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    /**
     * Returns the top card of the deck
     *
     * @return the first Card object in the instance attribute 'cards'
     */
    public Card peek() {
        return cards.get(0);
    }

    /**
     * Returns if the deck is empty
     *
     * @return a boolean for if deck is empty or not
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Adds a card to the bottom of the deck
     *
     * @param card the Card object to be added to the end of the instance attribute 'cards'
     */
    public void addCard(Card card) {
        cards.add(card);
    }

}
