package usecases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecases.IOInterfaces.GoFishIO;
import usecases.usermanagement.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GoFishTest {
    GoFish game;
    List<String> usernames;
    UserManager usermanager;

    @BeforeEach
    void setUp() {
        usernames = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            usernames.add("Test User-" + i);
        }

        usermanager = new UserManager();
        for (String username : usernames) {
            try {
                usermanager.addUser(username);
            } catch (UserManager.UserAlreadyExistsException e) {
                fail("User already exists, check test inputs");
            }
        }
        game = new GoFish(usernames, usermanager, new FakeGoFishGUI(), new Random(12345));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TestToString() {
        assertEquals("Go Fish", game.toString());
    }

    @Test
    void TestStartGame() {
        game.startGame();
        // after the game is done, every player's hand is empty, deck is empty, and the sum of the points in the
        // scoreTracker is equal to 13.
        for (Player player : game.players) {
            assertTrue(player.isHandEmpty());
        }
        assertTrue(game.deck.isEmpty());
        int scoreSum = 0;
        for (int score : game.SCORE_TRACKER.values()) {
            scoreSum += score;
        }
        assertEquals(13, scoreSum);
    }

    static class FakeGoFishGUI implements GoFishIO {

        protected static final String p1 = "Test User-1";
        protected static final String p2 = "Test User-2";
        protected static final String p3 = "Test User-3";
        protected static final String p4 = "Test User-4";
        protected static final String p5 = "Test User-5";
        protected static final String p6 = "Test User-6";
        protected static final String p7 = "Test User-7";
        String[] getRankSequence = {"8", "8", "6", "6", "Q", "Q", "Q", "4", "4", "5", "5", "5", "J", "J", "7", "7", "10",
                "10", "10", "2", "2", "2", "K", "K", "K", "9", "9", "3", "3", "3", "A", "A"};
        int currRankIndex = 0;
        String[] getUsernameSequence = {p5, p6, p4, p5, p3, p6, p7, p2, p4, p3, p5, p6, p7, p2, p4, p7, p4, p5, p6, p3,
                p4, p7, p3, p7, p1, p4, p7, p5, p6, p7, p5, p6};
        int currUsernameSequence = 0;

        @Override
        public void changePlayer(String username) {
            System.out.println("CHANGE PLAYER username = " + username);
        }

        @Override
        public void sendPopup(String message) {
            System.out.println("POPUP message = " + message);
        }

        @Override
        public void showHand(String hand) {
            System.out.println("SHOW HAND hand = " + hand);
        }

        @Override
        public String getRank() {
            String chosenRank = getRankSequence[currRankIndex];
            currRankIndex += 1;
            return chosenRank;
        }

        @Override
        public String getPlayerUsername(String currPlayer, List<String> usernames) {
            String chosenUsername = getUsernameSequence[currUsernameSequence];
            currUsernameSequence += 1;
            return chosenUsername;
        }

        @Override
        public void closeMessage(String message) {
            System.out.println("CLOSE w/ message = " + message);
        }
    }

}
