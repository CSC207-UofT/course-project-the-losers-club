package usecases;

import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager usrManager;

    @BeforeEach
    void setUp() {
        User usr1 = new User("usrname");
        User usr2 = new User("gamerboy123");
        HashMap<String, User> users = new HashMap<String, User>();
        users.put("usrname", usr1);
        users.put("gamerboy123", usr2);

        usrManager = new UserManager(users);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addUser() {
        try {
            usrManager.addUser("poggers");
        } catch (UserManager.UserAlreadyExistsException e) {
            e.printStackTrace();
        }
        assertTrue(usrManager.hasUser("poggers"));
    }

    @Test
    void deleteUser() {
        try {
            usrManager.deleteUser("gamerboy123");
        }
        catch (UserManager.UserNotFoundException e){
            e.printStackTrace();
        }
        assertFalse(usrManager.hasUser("gamerboy123"));
    }

    @Test
    void addGamesPlayed() {
        try {
            usrManager.addGamesPlayed("usrname", 1);
            usrManager.addGamesPlayed("usrname", 0);
            assertEquals(2, usrManager.getGamesPlayed("usrname"));
            assertEquals(1, usrManager.getWins("usrname"));
            assertEquals(1, usrManager.getGamesTied("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getWins() {
        try {
            usrManager.addGamesPlayed("usrname", 1);
            usrManager.addGamesPlayed("usrname", 1);
            assertEquals(2, usrManager.getWins("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getWinsv2() {
        try {
            usrManager.addGamesPlayed("usrname", -1);
            usrManager.addGamesPlayed("usrname", -1);
            assertEquals(0, usrManager.getWins("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getWinsv3() {
        try {
            usrManager.addGamesPlayed("usrname", -1);
            usrManager.addGamesPlayed("usrname", 0);
            usrManager.addGamesPlayed("usrname", 1);
            assertEquals(1, usrManager.getWins("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getGamesPlayed() {
        try {
            assertEquals(0, usrManager.getGamesPlayed("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getGamesTied() {
        try {
            assertEquals(0, usrManager.getGamesPlayed("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void hasUser() {
        assertTrue(usrManager.hasUser("gamerboy123"));
    }

    @Test
    void login() {
            assertTrue(usrManager.login("usrname"));
            assertFalse(usrManager.login("random"));
    }
}