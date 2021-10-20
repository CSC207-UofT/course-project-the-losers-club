package presenters.console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class OutputTest {

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    OutputStream stdout = System.out;

    Output cOut;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));

        this.cOut = new Output();
    }

    @AfterEach
    void tearDown() {
        System.setOut((PrintStream) this.stdout);
    }


    @Test
    void sendOutput() {
        this.cOut.sendOutput("hello world");
        assertEquals("hello world", this.outputStream.toString().trim());
    }
}