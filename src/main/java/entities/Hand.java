package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a player's hand during a card game. Each hand contains a list of Card objects, which represent the
 * cards in that player's hand.
 */
public class Hand implements Iterable<Card> {
    private final List<Card> cards;

    /**
     * Constructs an empty hand
     */
    public Hand() {
        this.cards = new ArrayList<>();
    }

    /**
     * Constructs a hand with the list of cards that is passed
     *
     * @param cards a list of Card objects that is passed to be in the hand
     */
    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Returns all the cards in the hand
     *
     * @return the instance attribute 'cards' - a list of card objects
     */

    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * Adds a card to the hand
     *
     * @param card the card to be added
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Adds all the cards in the given list to the hand.
     *
     * @param cards ArrayList of Cards to be added to the hand.
     */
    public void addCard(ArrayList<Card> cards) {
        this.cards.addAll(cards);
    }

    /**
     * Removes the first card in the hand and returns it
     *
     * @return the removed Card
     */
    public Card removeCard() {
        return cards.remove(0);
    }

    /**
     * Remove and return the given card in this Hand.
     * <p>
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
     * <p>
     * Assumes the card is in the hand.
     *
     * @param c the card
     * @return the removed Card
     */
    public Card removeCard(Card c) {
        int i = this.cards.indexOf(c);
        return this.cards.remove(i);
    }

    /**
     * Remove and return all cards of the given rank.
     *
     * @param rank the rank of the card
     * @return ArrayList of cards of the given rank.
     */
    public ArrayList<Card> removeCard(String rank) {
        ArrayList<Card> toReturn = new ArrayList<>();
        Iterator<Card> cardIterator = this.iterator();
        while (cardIterator.hasNext()) {
            Card card = cardIterator.next();
            if (card.getRank().equals(rank)) {
                toReturn.add(card);
                cardIterator.remove();
            }
        }
        return toReturn;
    }

    /**
     * Returns the number of cards in the hand
     *
     * @return the size of the instance attribute 'cards'
     */
    public int getSize() {
        return cards.size();
    }

    /**
     * Returns all the cards in the hand as a string
     *
     * @return String representing all the cards in the hand
     */
    public String toString() {
        return this.cards.toString();
    }

    /**
     * Returns if the hand is empty
     *
     * @return a boolean for if the hand is empty or not
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * This method generates a new hand will all the same cards as self but orders the cards as follows.
     * The cards are first grouped into spades, clubs, diamonds, and hearts and then within those groups they are sorted
     * by rank with respect to c.
     *
     * @param c A comparator implementation for Card, this describes how you want to sort the cards.
     * @return A new Hand in which all the cards are sorted as described above
     */
    public Hand sortedHand(Comparator<Card> c) {
        List<Card> clubs = new ArrayList<>();
        List<Card> spades = new ArrayList<>();
        List<Card> hearts = new ArrayList<>();
        List<Card> diamonds = new ArrayList<>();

        //Sorts the cards into groups based on their suits
        for (Card card : this.cards) {
            switch (card.getSuit()) {
                case 'c':
                    clubs.add(card);
                    break;
                case 's':
                    spades.add(card);
                    break;
                case 'h':
                    hearts.add(card);
                    break;
                case 'd':
                    diamonds.add(card);
                    break;
            }
        }

        //Sorts all the groups using c
        clubs.sort(c);
        hearts.sort(c);
        diamonds.sort(c);
        spades.sort(c);

        //Throw all the cards into one list
        spades.addAll(clubs);
        spades.addAll(diamonds);
        spades.addAll(hearts);

        //Return the hand of that new list
        return new Hand(spades);
    }


    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }
}
