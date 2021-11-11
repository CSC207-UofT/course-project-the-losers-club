package presenters.console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

    @Nested
    class GetCard {
        @Test
        void getCardSimple() {
            String data = "10S\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals("S10", cin.getCard());
        }

        @Test
        void getCardInvalidRank() {
            String data = "25d\n404c\n10S\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals("S10", cin.getCard());
        }

        @Test
        void getCardInvalidSuit() {
            String data = "10x\n10y\n6A\n5S\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals("S5", cin.getCard());
        }
    }

    @Nested
    class DrawCard {
        @Test
        void drawCardYes() {
            String data = "y\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertTrue(cin.drawCard());
        }

        @Test
        void drawCardNo() {
            String data = "n\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertFalse(cin.drawCard());
        }

        @Test
        void drawCardEmpty() {
            String data = "\nn\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertFalse(cin.drawCard());
        }

        @Test
        void drawCardMultipleCharacter() {
            String data = "asdkufhasdf\nn\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertFalse(cin.drawCard());
        }

        @Test
        void drawCardNumber() {
            String data = "13874234\nn\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertFalse(cin.drawCard());
        }
    }

    @Nested
    class GetSuit {
        @Test
        void getSuitClubs() {
            String data = "c\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals('C', cin.getSuit());
        }

        @Test
        void getSuitDiamonds() {
            String data = "D\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals('D', cin.getSuit());
        }

        @Test
        void getSuitHearts() {
            String data = "h\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals('H', cin.getSuit());
        }

        @Test
        void getSuitSpades() {
            String data = "S\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals('S', cin.getSuit());
        }

        @Test
        void getSuitInvalidSuit() {
            String data = "a\nb\nc\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals('C', cin.getSuit());
        }

        @Test
        void getSuitInvalidLength() {
            String data = "abc\ncba\nc\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals('C', cin.getSuit());
        }
    }

    @Nested
    class Stall {
        @Test
        void stallSimple() {
            String data = "\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertTrue(cin.stall());
        }

        @Test
        void stallWithText() {
            String data = "adsflkuhysadf\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertTrue(cin.stall());
        }
    }

    @Nested
    class GetUserSelection {
        @Test
        void getUserSelectionSingleDigit() {
            String data = "1\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals(1, cin.getUserSelection());
        }

        @Test
        void getUserSelectionMultipleDigit() {
            String data = "43856\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals(43856, cin.getUserSelection());
        }

        @Test
        void getUserSelectionEmpty() {
            String data = "\n1\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals(1, cin.getUserSelection());
        }

        @Test
        void getUserSelectionCharacters() {
            String data = "akjfdh\n1\n";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals(1, cin.getUserSelection());
        }
    }

    @Nested
    class GetUsername {
        @Test
        void getUsernameShort() {
            String data = "a";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals("a", cin.getUsername(5));
        }

        @Test
        void getUsernameLong() {
            String data = "abcdefghikj";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            cin = new Input();
            assertEquals("abcdefghikj", cin.getUsername(5));
        }
    }


}