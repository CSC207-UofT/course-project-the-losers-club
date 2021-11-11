package entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User testUser;

    @BeforeEach
    void setUp() {
        this.testUser = new User("jigglewagon");
    }

    @AfterEach
    void tearDown() {
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
    void addWin() {
        assertEquals(1, testUser.addWin());
    }

    @Test
    void addPlayed() {
        assertEquals(1, testUser.addPlayed());
    }

    @Test
    void addTie() {
        assertEquals(1, testUser.addTied());
    }

    @Test
    void getTie() {
        assertEquals(0, testUser.getGamesTied());
    }

    @Test
    void getPlayed() {
        assertEquals(0, testUser.getGamesPlayed());
    }

    @Test
    void getWon() {
        assertEquals(0, testUser.getGamesWon());
    }
}