package entities;

import java.util.*;

/**
 * A collection of cards in a queue format. Usually initialized with 52 Card objects, each representing 1 of the 52 unique cards in a
 * standard deck. However, can be initialized with other collections of cards
 */
public class Deck {

    private final Queue<Card> cards;

    /**
     * Constructs a deck and initializes it with a list of Card objects passed to it
     *
     * @param cards list of Card objects
     */
    public Deck(List<Card> cards) {
        this.cards = new LinkedList<>();
        this.cards.addAll(cards);
    }

    /**
     * Returns and removes the top card of the deck
     *
     * @return the Card that was just removed
     */
    public Card drawCard() {
        return cards.remove();
    }

    /**
     * Shuffles the deck
     */
    public void shuffle() {
        this.shuffle(new Random());
    }

    /**
     * Shuffles the deck with a pseudorandom seed.
     *
     * @param rand Random object used to seed the shuffle
     */
    public void shuffle(Random rand) {
        Collections.shuffle((List<?>) this.cards, rand);
    }

    /**
     * Returns the top card of the deck
     *
     * @return the first Card object in the instance attribute 'cards'
     */
    public Card peek() {
        return cards.peek();
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


    /**
     * Returns the number of cards in the deck
     *
     * @return the size of the instance attribute 'cards'
     */
    public int getSize() {
        return cards.size();
    }

}
