package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private static final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static final char[] SUITS = {'C', 'D', 'H', 'S'};

    private Deck deck;
    private List<Card> cardList;

    @BeforeEach
    void setUp() {
        this.cardList = createCardList();
        this.deck = new Deck(cardList);
    }

    List<Card> createCardList() {
        List<Card> cl = new ArrayList<>();
        for (String i : RANKS) {
            for (char j : SUITS) {
                cl.add(new Card(i, j));
            }
        }
        return cl;
    }

    @Nested
    class DrawCard {
        @Test
        void drawCardFirst() {
            assertEquals(cardList.get(0), deck.drawCard());
        }

        @Test
        void drawCardSecond() {
            Card c1 = cardList.get(0);
            Card c2 = cardList.get(1);
            assertEquals(c1, deck.drawCard());
            assertEquals(c2, deck.drawCard());
        }
    }

    @Nested
    class Shuffle {
        long seed = 123456789;

        List<Card> expected;

        @BeforeEach
        void setUp() {
            this.expected = createCardList();
            Collections.shuffle(this.expected, new Random(this.seed));
        }

        @Test
        void shuffle() {
            deck.shuffle(new Random(this.seed));
            int i = 0;
            while (!deck.isEmpty()) {
                Card c = deck.drawCard();
                assertEquals(expected.get(i), c);
                i++;
            }
        }
    }

    @Nested
    class Peek {

        @Test
        void peekOne() {
            assertEquals("AC", deck.peek().toString());
        }

        @Test
        void peekTwo() {
            deck.peek();  // check for no item removal
            assertEquals("AC", deck.peek().toString());
        }

    }

    @Nested
    class IsEmpty {

        @Test
        void isEmptyTrue() {
            deck = new Deck(new ArrayList<>());
            assertTrue(deck.isEmpty());
        }

        @Test
        void isEmptyFalse() {
            assertFalse(deck.isEmpty());
        }
    }

    @Nested
    class AddCard {

        @BeforeEach
        void setUp() {
            deck = new Deck(new ArrayList<>());
        }

        @Test
        void addCardCard() {
            Card c = new Card("A", 'c');
            deck.addCard(c);
            assertEquals(c, deck.drawCard());
        }

        @Test
        void addCardLength() {
            Card c = new Card("A", 'c');
            assertTrue(deck.isEmpty());
            deck.addCard(c);
            assertFalse(deck.isEmpty());
            deck.drawCard();
            assertTrue(deck.isEmpty());

        }
    }
}