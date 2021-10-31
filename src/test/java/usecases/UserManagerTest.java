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
        User usr1 = new User("User 1", "usrname", "1234");
        User usr2 = new User("User 2", "gamerboy123", "5678");
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
            usr_manager.addUser("User 3", "poggers", "pswd");
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
            usr_manager.addGamesPlayed("usrname", true);
            usr_manager.addGamesPlayed("usrname", false);
            assertEquals(2, usr_manager.getGamesPlayed("usrname"));
            assertEquals(1, usr_manager.getWins("usrname"));
        } catch (UserManager.UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getWins() {
        try {
            usr_manager.addGamesPlayed("usrname", true);
            usr_manager.addGamesPlayed("usrname", false);
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
    void hasUser() {
        assertTrue(usr_manager.hasUser("gamerboy123"));
    }

    @Test
    void login() {
        try{
            assertFalse(usr_manager.login("usrname", "5678"));
            assertTrue(usr_manager.login("usrname", "1234"));
        } catch (UserManager.UserNotFoundException e){
            e.printStackTrace();
        }
    }
}