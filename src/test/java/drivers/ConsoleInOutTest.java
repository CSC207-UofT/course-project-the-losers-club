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
        String data = "10\nS\n";
        this.stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        this.cio = new ConsoleInOut();

        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setIn(this.stdin);
        System.setOut((PrintStream) this.stdout);
    }

    @Test
    void getCard() {
        assertEquals("S10", this.cio.getCard());
    }

    @Test
    void sendOutput() {
        this.cio.sendOutput("hello world");
        assertEquals("hello world", this.outputStream.toString().trim());
    }


}