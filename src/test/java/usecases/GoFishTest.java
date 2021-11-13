package usecases;

import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenters.console.Input;
import presenters.console.Output;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GoFishTest {
    GoFish game;
    List<String> usernames;
    UserManager usermanager;

    @BeforeEach
    void setUp() throws UserManager.UserAlreadyExistsException {
        usernames = new ArrayList<>();
        usernames.add("jigglewagon");
        usernames.add("blah");
        usermanager = new UserManager();
        usermanager.addUser("jigglewagon");
        usermanager.addUser("blah");
        game = new GoFish(usernames, usermanager, new Input(), new Output());
    }
    @Test
    void TestToString() {
        assertEquals("Go Fish", game.toString());
    }
}
