package usecases;

import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager usr_manager;

    @BeforeEach
    void setUp() {
        User usr1 = new User("usrname");
        User usr2 = new User("gamerboy123");
        HashMap<String, User> users = new HashMap<String, User>();
        users.put("usrname", usr1);
        users.put("gamerboy123", usr2);

        usr_manager = new UserManager(users);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addUser() {
        try {
            usr_manager.addUser("poggers");
        } catch (UserManager.UserAlreadyExistsException e) {
            e.printStackTrace();
        }
        assertTrue(usr_manager.hasUser("poggers"));
    }

    @Test
    void deleteUser() {
        try {
            usr_manager.deleteUser("gamerboy123");
        }
        catch (UserManager.UserNotFoundException e){
            e.printStackTrace();
        }
        assertFalse(usr_manager.hasUser("gamerboy123"));
    }

    @Test
    void addGamesPlayed() {
        try {
            usr_manager.addGamesPlayed("usrname", 1);
            usr_manager.addGamesPlayed("usrname", 0);
            assertEquals(2, usr_manager.getGamesPlayed("usrname"));
            assertEquals(1, usr_manager.getWins("usrname"));
            assertEquals(1, usr_manager.getGamesTied("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getWins() {
        try {
            usr_manager.addGamesPlayed("usrname", 1);
            usr_manager.addGamesPlayed("usrname", 1);
            assertEquals(2, usr_manager.getWins("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getWinsv2() {
        try {
            usr_manager.addGamesPlayed("usrname", -1);
            usr_manager.addGamesPlayed("usrname", -1);
            assertEquals(0, usr_manager.getWins("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getWinsv3() {
        try {
            usr_manager.addGamesPlayed("usrname", -1);
            usr_manager.addGamesPlayed("usrname", 0);
            usr_manager.addGamesPlayed("usrname", 1);
            assertEquals(1, usr_manager.getWins("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getGamesPlayed() {
        try {
            assertEquals(0, usr_manager.getGamesPlayed("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getGamesTied() {
        try {
            assertEquals(0, usr_manager.getGamesPlayed("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void hasUser() {
        assertTrue(usr_manager.hasUser("gamerboy123"));
    }

    @Test
    void login() {
        assertTrue(usr_manager.login("usrname"));
        assertFalse(usr_manager.login("random"));
    }
}