package helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardHashTest {

    @Test
    void testGetCardHashCodeClubs() {
        assertEquals(101, CardHash.getCardHashCode('C', "A"));
    }

    @Test
    void testGetCardHashCodeDiamonds() {
        assertEquals(201, CardHash.getCardHashCode('D', "A"));
    }

    @Test
    void testGetCardHashCodeHearts() {
        assertEquals(301, CardHash.getCardHashCode('H', "A"));
    }

    @Test
    void testGetCardHashCodeSpades() {
        assertEquals(401, CardHash.getCardHashCode('S', "A"));
    }

    @Test
    void testGetCardHashCodeIllegalSuit() {
        assertThrows(IllegalArgumentException.class, () -> CardHash.getCardHashCode('X', "A"));
    }

    @Test
    void testGetCardHashCodeThree() {
        assertEquals(403, CardHash.getCardHashCode('S', "3"));
    }

    @Test
    void testGetCardHashCodeTen() {
        assertEquals(410, CardHash.getCardHashCode('S', "10"));
    }

    @Test
    void testGetCardHashCodeKing() {
        assertEquals(413, CardHash.getCardHashCode('S', "K"));
    }

    @Test
    void testGetCardHashCodeIllegalRank() {
        assertThrows(IllegalArgumentException.class, () -> CardHash.getCardHashCode('S', "X"));
    }

    @Test
    void testGetCardHashCodeIllegalNumericRank() {
        assertThrows(IllegalArgumentException.class, () -> CardHash.getCardHashCode('S', "404"));
    }
}