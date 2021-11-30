package usecases;

import entities.Card;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenters.console.Input;
import presenters.console.Output;
import userdata.SQLiteUserDatabase;
import userdata.UserDatabaseGateway;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WarTest {
    private UserDatabaseGateway userDatabaseGateway;
    public War war;
    String filePath;

    @BeforeEach
    void setUp() {
        this.filePath = "usermanager-" + (new Date()).getTime() + (new Random()).nextInt() + ".db";
        this.userDatabaseGateway = new SQLiteUserDatabase(this.filePath);

        Input input = new Input();
        Output output = new Output();
        List<String> usernames = new ArrayList<>();
        usernames.add("Daniel");
        usernames.add("Bradley");
        Random seed = new Random(12345);
        this.war = new War(usernames, this.userDatabaseGateway, input, output, seed);
    }

    @AfterEach
    void tearDown() {
        try {
            this.userDatabaseGateway.close();
        } catch (IOException e) {
            fail("Could not close manager connection.");
        }
        if (!(new File(this.filePath)).delete()) {
            fail("File could not be deleted, is it still in use?");
        }
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
}
