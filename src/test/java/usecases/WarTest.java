package usecases;

import entities.Card;
import presenters.console.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WarTest {
    private UserManager userManager;
    public War war;

    @BeforeEach void setUp(){
        Input input = new Input();
        Output output = new Output();
        List<String> usernames = new ArrayList<String>();
        usernames.add("Daniel");
        usernames.add("Bradley");
        Random seed = new Random(12345);
        this.war = new War(input, output, usernames, this.userManager, seed);
    }

    @Test
    void flipTest(){
        Card p1Card = this.war.players[0].getHand().getCards().get(0);
        Card p2Card = this.war.players[1].getHand().getCards().get(0);
        this.war.flipCards();
        Card p1actual = (Card) this.war.playingField[0].get(this.war.playingField[0].size() - 1);
        Card p2actual = (Card) this.war.playingField[1].get(this.war.playingField[1].size() - 1);
        assert(p1Card.equals(p1actual) && p2Card.equals(p2actual));

    }

    @Test
    void decideTest1(){
        Card p1Card = this.war.players[0].getHand().getCards().get(0);
        Card p2Card = this.war.players[1].getHand().getCards().get(0);
        int result = this.war.decideRoundWinner(p1Card, p2Card,false);
        assertEquals(0, result);
    }

    @Test
    void decideTest2(){
        Card p1Card = new Card("2", 'H');
        Card p2Card = new Card("2", 'H');
        int result = this.war.decideRoundWinner(p1Card, p2Card,false);
        assertEquals(2, result);
    }

    @Test
    void decideTest3(){
        Card p1Card = new Card("A", 'H');
        Card p2Card = new Card("2", 'H');
        int result = this.war.decideRoundWinner(p1Card, p2Card,false);
        assertEquals(1, result);
    }

    @Test
    void decideTest4(){
        Card p1Card = new Card("3", 'H');
        Card p2Card = new Card("2", 'H');
        int result = this.war.decideRoundWinner(p1Card, p2Card,false);
        assertEquals(0, result);
    }

    @Test
    void makeMoveTest1(){
        this.war.currPlayerIndex = 0;
        Card newCard = new Card("3", 'H');
        this.war.makeMove(newCard);
        Card expected = new Card("3", 'H');
        Card actual = (Card) this.war.playingField[0].get(this.war.playingField[0].size() - 1);
        assert(expected.equals(actual));

    }

    @Test
    void makeMoveTest2(){
        this.war.currPlayerIndex = 1;
        Card newCard = new Card("3", 'H');
        this.war.makeMove(newCard);
        Card expected = new Card("2", 'H');
        Card actual = (Card) this.war.playingField[1].get(this.war.playingField[1].size() - 1);
        assert(!(expected.equals(actual)));

    }
}
