package drivers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleInOutTest {

    InputStream stdin = System.in;

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    OutputStream stdout = System.out;

    ConsoleInOut cio;

    @BeforeEach
    void setUp() {
        this.stdin = System.in;
        System.setOut(new PrintStream(outputStream));

        this.cio = new ConsoleInOut();
    }

    @AfterEach
    void tearDown() {
        System.setIn(this.stdin);
        System.setOut((PrintStream) this.stdout);
    }

    @Test
    void getCardSimple() {
        String data = "10\nS\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cio = new ConsoleInOut();
        assertEquals("S10", this.cio.getCard());
    }

    @Test
    void getCardInvalidRank() {
        String data = "25\n404\n10\nS\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cio = new ConsoleInOut();
        assertEquals("S10", this.cio.getCard());
    }

    @Test
    void getCardInvalidSuit() {
        String data = "10\nA\nB\nE\nS\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cio = new ConsoleInOut();
        assertEquals("S10", this.cio.getCard());
    }

    @Test
    void sendOutput() {
        this.cio.sendOutput("hello world");
        assertEquals("hello world", this.outputStream.toString().trim());
    }


    @Test
    void drawCardYes() {
        String data = "y\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cio = new ConsoleInOut();
        assertTrue(this.cio.drawCard());
    }

    @Test
    void drawCardNo() {
        String data = "n\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cio = new ConsoleInOut();
        assertFalse(this.cio.drawCard());
    }
}