package usecases;

import entities.Card;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecases.IOInterfaces.WarIO;
import usecases.usermanagement.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarTest {
    public War war;

    @BeforeEach
    void setUp() {
        UserManager userManager = new UserManager();

        List<String> usernames = new ArrayList<>();
        usernames.add("Daniel");
        usernames.add("Bradley");
        Random seed = new Random(12345);
        this.war = new War(usernames, userManager, new FakeWarGUI(), seed);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void flipTest() {
        Card p1Card = this.war.players[0].getHand().getCards().get(0);
        Card p2Card = this.war.players[1].getHand().getCards().get(0);
        this.war.flipCards();
        Card p1actual = this.war.returnTopCard(0);
        Card p2actual = this.war.returnTopCard(1);
        assert (p1Card.equals(p1actual) && p2Card.equals(p2actual));

    }

    @Test
    void decideTest1() {
        Card p1Card = this.war.players[0].getHand().getCards().get(0);
        Card p2Card = this.war.players[1].getHand().getCards().get(0);
        int result = this.war.decideRoundWinner(p1Card, p2Card, false);
        assertEquals(0, result);
    }

    @Test
    void decideTest2() {
        Card p1Card = new Card("2", 'H');
        Card p2Card = new Card("2", 'H');
        int result = this.war.decideRoundWinner(p1Card, p2Card, false);
        assertEquals(2, result);
    }

    @Test
    void decideTest3() {
        Card p1Card = new Card("A", 'H');
        Card p2Card = new Card("2", 'H');
        int result = this.war.decideRoundWinner(p1Card, p2Card, false);
        assertEquals(1, result);
    }

    @Test
    void decideTest4() {
        Card p1Card = new Card("3", 'H');
        Card p2Card = new Card("2", 'H');
        int result = this.war.decideRoundWinner(p1Card, p2Card, false);
        assertEquals(0, result);
    }

    @Test
    void makeMoveTest1() {
        this.war.currPlayerIndex = 0;
        Card newCard = new Card("3", 'H');
        this.war.makeMove(newCard);
        Card expected = new Card("3", 'H');
        Card actual = this.war.returnTopCard(0);
        assert (expected.equals(actual));

    }

    @Test
    void makeMoveTest2() {
        this.war.currPlayerIndex = 1;
        Card newCard = new Card("3", 'H');
        this.war.makeMove(newCard);
        Card expected = new Card("2", 'H');
        Card actual = this.war.returnTopCard(1);
        assert (!(expected.equals(actual)));

    }

    static class FakeWarGUI implements WarIO {

        @Override
        public void changePlayer(String username) {
            System.out.println("Player changed to: " + username);
        }

        @Override
        public void displayBoard(String card1, String card2, int pileSize, String username1, String username2) {
            System.out.println("BOARD DISPLAY");
            System.out.println("card1 = " + card1);
            System.out.println("card2 = " + card2);
            System.out.println("pileSize = " + pileSize);
            System.out.println("username1 = " + username1);
            System.out.println("username2 = " + username2);
        }

        @Override
        public void stall() {
            System.out.println("STALL");
        }

        @Override
        public void sendPopup(String message) {
            System.out.println("POPUP: " + message);
        }

        @Override
        public void close() {
            System.out.println("CLOSE");
        }

        @Override
        public void closeMessage(String message) {
            System.out.println("CLOSE w/ message = " + message);
        }
    }
}
