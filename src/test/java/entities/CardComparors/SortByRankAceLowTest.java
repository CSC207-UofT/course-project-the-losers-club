package entities.CardComparors;

import entities.Card;
import entities.Hand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortByRankAceLowTest {

    @Test
    void CheckOnlyNumbers() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("4", 's'));
        cards.add(new Card("6", 's'));
        cards.add(new Card("7", 's'));
        cards.add(new Card("5", 's'));
        cards.add(new Card("2", 's'));
        cards.add(new Card("6", 'h'));
        cards.add(new Card("9", 'h'));
        cards.add(new Card("3", 'h'));

        Hand hand = new Hand(cards);

        cards = new ArrayList<>();

        cards.add(new Card("2", 's'));
        cards.add(new Card("4", 's'));
        cards.add(new Card("5", 's'));
        cards.add(new Card("6", 's'));
        cards.add(new Card("7", 's'));
        cards.add(new Card("3", 'h'));
        cards.add(new Card("6", 'h'));
        cards.add(new Card("9", 'h'));

        Hand sortedHand = new Hand(cards);

        Comparator<Card> c = new SortByRankAceLow();

        assertEquals(sortedHand.toString(), hand.sortedHand(c).toString());
    }

    @Test
    void CheckOnlyNumbersPlusAce() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("4", 's'));
        cards.add(new Card("6", 's'));
        cards.add(new Card("7", 's'));
        cards.add(new Card("5", 's'));
        cards.add(new Card("a", 's'));
        cards.add(new Card("2", 's'));
        cards.add(new Card("6", 'h'));
        cards.add(new Card("9", 'h'));
        cards.add(new Card("a", 'h'));
        cards.add(new Card("3", 'h'));

        Hand hand = new Hand(cards);

        cards = new ArrayList<>();

        cards.add(new Card("a", 's'));
        cards.add(new Card("2", 's'));
        cards.add(new Card("4", 's'));
        cards.add(new Card("5", 's'));
        cards.add(new Card("6", 's'));
        cards.add(new Card("7", 's'));
        cards.add(new Card("a", 'h'));
        cards.add(new Card("3", 'h'));
        cards.add(new Card("6", 'h'));
        cards.add(new Card("9", 'h'));

        Hand sortedHand = new Hand(cards);

        Comparator<Card> c = new SortByRankAceLow();

        assertEquals(sortedHand.toString(), hand.sortedHand(c).toString());
    }

    @Test
    void fullCheck() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("4", 's'));
        cards.add(new Card("6", 's'));
        cards.add(new Card("7", 's'));
        cards.add(new Card("q", 'h'));
        cards.add(new Card("k", 's'));
        cards.add(new Card("5", 's'));
        cards.add(new Card("a", 's'));
        cards.add(new Card("2", 's'));
        cards.add(new Card("6", 'h'));
        cards.add(new Card("q", 's'));
        cards.add(new Card("9", 'h'));
        cards.add(new Card("a", 'h'));
        cards.add(new Card("3", 'h'));

        Hand hand = new Hand(cards);

        cards = new ArrayList<>();

        cards.add(new Card("a", 's'));
        cards.add(new Card("2", 's'));
        cards.add(new Card("4", 's'));
        cards.add(new Card("5", 's'));
        cards.add(new Card("6", 's'));
        cards.add(new Card("7", 's'));
        cards.add(new Card("q", 's'));
        cards.add(new Card("k", 's'));
        cards.add(new Card("a", 'h'));
        cards.add(new Card("3", 'h'));
        cards.add(new Card("6", 'h'));
        cards.add(new Card("9", 'h'));
        cards.add(new Card("q", 'h'));

        Hand sortedHand = new Hand(cards);

        Comparator<Card> c = new SortByRankAceLow();

        assertEquals(sortedHand.toString(), hand.sortedHand(c).toString());
    }

}
