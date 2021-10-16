package entities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
    // Constants and instance attributes. RANKS is an array of all possible ranks, SUITS is an array of all possible suits
    private List<Card> cards = new ArrayList<>();
    private static final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static final char[] SUITS = {'H', 'S', 'D', 'C'};

    // Constructor. Initializes a deck containing all cards.
    public Deck() {
        for (String i : this.RANKS){
            for (char j : this.SUITS){
                this.cards.add(new Card(i, j));
            }
        }
    }

    // Constructor. Initializes a deck containing the list of cards that is passed.
    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    // Returns the deck's top card and removes it from the deck.
    public Card drawCard() {
        return cards.remove(0);
    }

    // Shuffles the deck.
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    // Returns the deck's top card without removing it.
    public Card peek() {
        return cards.get(0);
    }

    // Returns if the deck is empty.
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    // Adds a card to the bottom of the deck.
    public void addCard(Card card) {
        cards.add(card);
    }
}
