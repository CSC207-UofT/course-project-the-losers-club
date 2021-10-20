package presenters.console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class InputTest {

    InputStream stdin = System.in;

    Input cin;

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
}