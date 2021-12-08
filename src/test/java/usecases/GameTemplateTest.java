package usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import usecases.IOInterfaces.GameIO;
import usecases.usermanagement.UserManager;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTemplateTest {

    List<String> usernames;
    UserManager userManager;
    GameIO gameIO;

    @BeforeEach
    void setUp() {
        this.usernames = Arrays.asList("harrypotter", "hermionegranger", "ronweasley");

        this.userManager = new UserManager();
        for (String u : usernames) {
            try {
                userManager.addUser(u);
            } catch (UserManager.UserAlreadyExistsException e) {
                fail("User already exists, check for duplicate test inputs.");
            }
        }

        this.gameIO = new FakeGameIO();
    }

    static class FakeGameIO implements GameIO {

        /**
         * This method should send a popup to the user containing a <code>message</code>.
         *
         * @param message a string that is to be sent to the user
         */
        @Override
        public void sendPopup(String message) {
        }

        /**
         * This method should display a <code>message</code> to the user and then close the GUI.
         *
         * @param message a string that is to be sent to the user.
         */
        @Override
        public void closeMessage(String message) {

        }

        /**
         * This method should close the GUI when called.
         */
        @Override
        public void close() {

        }
    }

    static class FakeGame extends GameTemplate {

        protected FakeGame(List<String> usernames, UserManager userManager, GameIO gameIO) {
            super(usernames, userManager, gameIO);
        }

        @Override
        public void startGame() {

        }
    }

    @Nested
    class AddUserStatsWinner {

        GameTemplate game;

        @BeforeEach
        void setUp() {
            this.game = new FakeGame(usernames, userManager, gameIO);
        }

        @Test
        void addUserStats() {
            String winner = "hermionegranger";
            this.game.addUserStats(winner);
            for (String u : usernames) {
                try {
                    assertEquals(1, userManager.getGamesPlayed(u));
                } catch (UserManager.UserNotFoundException e) {
                    fail("User not found when getting games played");
                }

                try {
                    assertEquals(0, userManager.getGamesTied(u));
                } catch (UserManager.UserNotFoundException e) {
                    fail("User not found when getting games tied");
                }

                if (u.equals(winner)) {
                    try {
                        assertEquals(1, userManager.getWins(u));
                    } catch (UserManager.UserNotFoundException e) {
                        fail("User not found when getting winner's wins");
                    }
                } else {
                    try {
                        assertEquals(0, userManager.getWins(u));
                    } catch (UserManager.UserNotFoundException e) {
                        fail("User not found when getting non-winner's wins");
                    }
                }
            }
        }
    }

    @Nested
    class AddUserStatsTied {

        GameTemplate game;

        @BeforeEach
        void setUp() {
            this.game = new FakeGame(usernames, userManager, gameIO);
        }

        @Test
        void addUserStats() {
            List<String> tiedUsers = Arrays.asList("harrypotter", "hermionegranger");
            this.game.addUserStats(tiedUsers);
            for (String u : usernames) {
                try {
                    assertEquals(1, userManager.getGamesPlayed(u));
                } catch (UserManager.UserNotFoundException e) {
                    fail("User not found when getting games played");
                }

                try {
                    assertEquals(0, userManager.getWins(u));
                } catch (UserManager.UserNotFoundException e) {
                    fail("User not found when getting non-winner's wins");
                }

                if (tiedUsers.contains(u)) {
                    try {
                        assertEquals(1, userManager.getGamesTied(u));
                    } catch (UserManager.UserNotFoundException e) {
                        fail("User not found when getting games tied");
                    }
                } else {
                    try {
                        assertEquals(0, userManager.getGamesTied(u));
                    } catch (UserManager.UserNotFoundException e) {
                        fail("User not found when getting non-winner's wins");
                    }
                }
            }
        }
    }

    @Nested
    class MinMaxPlayers {
        @ParameterizedTest
        @ValueSource(strings = {"Crazy Eights", "War", "Go Fish"})
        void minMaxPlayers(String game) {
            int min = GameTemplate.getMinPlayers(game);
            int max = GameTemplate.getMaxPlayers(game);
            assertAll(
                    () -> assertTrue(min > 0),
                    () -> assertTrue(min <= max)
            );
        }
    }
}