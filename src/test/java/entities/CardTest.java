package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void getRank(){
        Card c1 = new Card("K", 'H');
        assertEquals(c1.getRank(), "K");
    }

    @Test
    void getSuit(){
        Card c1 = new Card("K", 'C');
        assertEquals(c1.getSuit(), 'C');
    }

    @Test
    void isFace(){
        Card c1 = new Card("K", 'C');
        assertTrue(c1.isFace());
    }

    @Test
    void testToString(){
        Card c1 = new Card("10", 'C');
        assertEquals(c1.toString(), "10C");
    }
    @Test
    void testEquals(){
        Card c1 = new Card("10", 'C');
        Card c2 = new Card("K", 'C');
        assertNotEquals(c1, c2);
    }

}