package presenters.console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class InputTest {

    private InputStream stdin = System.in;

    private Input cin;

    @BeforeEach
    void setUp() {
        this.stdin = System.in;
        this.cin = new Input();
    }

    @AfterEach
    void tearDown() {
        System.setIn(this.stdin);
    }

    @Test
    void getCardSimple() {
        String data = "10S\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cin = new Input();
        assertEquals("S10", this.cin.getCard());
    }

    @Test
    void getCardInvalidRank() {
        String data = "25d\n404c\n10S\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cin = new Input();
        assertEquals("S10", this.cin.getCard());
    }

    @Test
    void getCardInvalidSuit() {
        String data = "10x\n10y\n6A\n5S\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cin = new Input();
        assertEquals("S5", this.cin.getCard());
    }

    @Test
    void drawCardYes() {
        String data = "y\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cin = new Input();
        assertTrue(this.cin.drawCard());
    }

    @Test
    void drawCardNo() {
        String data = "n\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cin = new Input();
        assertFalse(this.cin.drawCard());
    }

    @Test
    void getSuitClubs() {
        String data = "c\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cin = new Input();
        assertEquals('C', this.cin.getSuit());
    }

    @Test
    void getSuitDiamonds() {
        String data = "D\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cin = new Input();
        assertEquals('D', this.cin.getSuit());
    }

    @Test
    void getSuitHearts() {
        String data = "h\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cin = new Input();
        assertEquals('H', this.cin.getSuit());
    }

    @Test
    void getSuitSpades() {
        String data = "S\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cin = new Input();
        assertEquals('S', this.cin.getSuit());
    }

    @Test
    void getSuitInvalidSuit() {
        String data = "a\nb\nc\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cin = new Input();
        assertEquals('C', this.cin.getSuit());
    }

    @Test
    void getSuitInvalidLength() {
        String data = "abc\ncba\nc\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cin = new Input();
        assertEquals('C', this.cin.getSuit());
    }
}