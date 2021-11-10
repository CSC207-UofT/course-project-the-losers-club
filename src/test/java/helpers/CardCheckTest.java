package helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardCheckTest {

    @Test
    void checkCardValidNumRank() {
        assertTrue(CardCheck.checkCard("5D"));
    }

    @Test
    void checkCardValidTenRank() {
        assertTrue(CardCheck.checkCard("10C"));
    }

    @Test
    void checkCardValidFace() {
        assertTrue(CardCheck.checkCard("Qs"));
    }

    @Test
    void checkCardValidLowercaseTen() {
        assertTrue(CardCheck.checkCard("10h"));
    }

    @Test
    void checkCardValidLowercaseFace() {
        assertTrue(CardCheck.checkCard("ks"));
    }

    @Test
    void checkCardInvalidRank() {
        assertFalse(CardCheck.checkCard("xD"));
    }

    @Test
    void checkCardInvalidSuit() {
        assertFalse(CardCheck.checkCard("10i"));
    }

    @Test
    void checkCardInvalidRankSuit() {
        assertFalse(CardCheck.checkCard("ll"));
    }

    @Test
    void checkCardLongInput() {
        assertFalse(CardCheck.checkCard("5dx"));
    }

    @Test
    void checkRankZero() {
        assertFalse(CardCheck.checkCard("0s"));
    }

    @Test
    void checkRankOne() {
        assertFalse(CardCheck.checkCard("1s"));
    }

}