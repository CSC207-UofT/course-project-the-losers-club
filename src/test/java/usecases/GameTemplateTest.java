package usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import usecases.usermanagement.UserManager;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTemplateTest {

    List<String> usernames;
    UserManager userManager;
    GameTemplate.Input input;
    GameTemplate.Output output;

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

        this.input = new Input();
        this.output = new Output();
    }

    static class Input implements GameTemplate.Input {

        @Override
        public String getCard() {
            return null;
        }

        @Override
        public boolean drawCard() {
            return false;
        }

        @Override
        public char getSuit() {
            return 0;
        }

        @Override
        public String getRank() {
            return null;
        }

        @Override
        public String getPlayerUsername(String currPlayerUsername, List<String> usernames) {
            return null;
        }

        @Override
        public boolean stall() {
            return false;
        }
    }

    static class Output implements GameTemplate.Output {

        @Override
        public void sendOutput(Object s) {

        }
    }

    static class FakeGame extends GameTemplate {

        protected FakeGame(List<String> usernames, UserManager userManager, Input gameInput, Output gameOutput) {
            super(usernames, userManager, gameInput, gameOutput);
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
            this.game = new FakeGame(usernames, userManager, input, output);
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
            this.game = new FakeGame(usernames, userManager, input, output);
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