package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandTest {

    Hand hand;

    @BeforeEach
    void setUp(){
        Card c1 = new Card("K", 'H');
        Card c2 = new Card("K", 'C');
        Card c3 = new Card("A", 'C');
        List<Card> cards = new ArrayList<>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        this.hand = new Hand(cards);
    }

    @Test
    void getCards(){
        Card c1 = new Card("K", 'H');
        Card c2 = new Card("K", 'C');
        Card c3 = new Card("A", 'C');
        List<Card> card2 = new ArrayList<>();
        card2.add(c1);
        card2.add(c2);
        card2.add(c3);
        assertEquals(this.hand.getCards(), card2);
    }

    @Test
    void addCard(){
        Card newCard = new Card("2",'C');
        Card c1 = new Card("K", 'H');
        Card c2 = new Card("K", 'C');
        Card c3 = new Card("A", 'C');
        List<Card> card2 = new ArrayList<>();
        card2.add(c1);
        card2.add(c2);
        card2.add(c3);
        card2.add(newCard);
        this.hand.addCard(newCard);
        assertEquals(this.hand.getCards(), card2);
    }

    @Test
    void removeCard(){
        Card c1 = new Card("K", 'H');
        assertEquals(this.hand.removeCard(), c1);
    }

    @Test
    void removeCardData(){
        Card c2 = new Card("K", 'C');
        assertEquals(this.hand.removeCard("K",'C'), c2);
    }

    @Test
    void removeCardCard(){
        Card c2 = new Card("K", 'C');
        assertEquals(this.hand.removeCard(c2), c2);
    }

    @Test
    void removeCardArray(){
        Card c1 = new Card("K", 'H');
        Card c2 = new Card("K", 'C');
        List<Card> card2 = new ArrayList<>();
        card2.add(c1);
        card2.add(c2);
        assertEquals(this.hand.removeCard("K"), card2);
    }

    @Test
    void getSize(){
        assertEquals(this.hand.getSize(), 3);
    }

    @Test
    void isEmpty(){
        this.hand.removeCard();
        this.hand.removeCard();
        this.hand.removeCard();
        assertTrue(this.hand.isEmpty());
    }
}
