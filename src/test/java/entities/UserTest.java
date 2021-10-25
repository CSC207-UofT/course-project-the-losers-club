package entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User testUser;

    @BeforeEach
    void setUp() {
        this.testUser = new User("Ted", "jigglewagon", "abcd");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getName() {
        assertEquals("Ted", testUser.getName());
    }

    @Test
    void getNameWrong() {
        assertNotEquals("Teddy", testUser.getName());
    }

    @Test
    void getUsername() {
        assertEquals("jigglewagon", testUser.getUsername());
    }

    @Test
    void getUsernameWrong() {
        assertNotEquals("jigglediggle", testUser.getUsername());
    }

    @Test
    void checkPassword() {
        assertTrue(testUser.checkPassword("abcd"));
    }

    @Test
    void checkPasswordWrong() {
        assertFalse(testUser.checkPassword("blahblah"));
    }

    @Test
    void addWin() {
        assertEquals(1, testUser.addWin());
    }

    @Test
    void addPlayed() {
        assertEquals(1, testUser.addPlayed());
    }
}