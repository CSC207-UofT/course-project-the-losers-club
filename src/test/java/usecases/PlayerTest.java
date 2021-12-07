package usecases;

import entities.Card;
import entities.Hand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class PlayerTest {
     Player player;

     @BeforeEach
    void setUp(){
         this.player = new Player("Daniel");
     }

     @Test
    void getUsername(){
         assertEquals(this.player.getUsername(), "Daniel");
     }

     @Test
    void addCard(){
         Card c1 = new Card("K", 'H');
         this.player.addToHand(c1);
         List<Card> cards = new ArrayList<>();
         cards.add(c1);
         assertEquals(this.player.getHand().getCards(), cards);
     }

    @Test
    void addCards(){
        Card c2 = new Card("K", 'C');
        Card c3 = new Card("A", 'C');
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(c2);
        cards.add(c3);
        this.player.addToHand(cards);
        assertEquals(this.player.getHand().getCards(), cards);
    }

    @Test
    void getHand(){
        Card c2 = new Card("K", 'C');
        Card c3 = new Card("A", 'C');
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(c2);
        cards.add(c3);
        Hand hand = new Hand(cards);
        this.player.addToHand(cards);
        assertEquals(this.player.getHand().getCards(), hand.getCards());
    }

    @Test
    void isHandEmpty(){
         assertTrue(this.player.isHandEmpty());
    }


}
