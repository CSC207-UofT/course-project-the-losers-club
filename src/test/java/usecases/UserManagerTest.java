package usecases;

import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import usecases.usermanagement.UserDatabaseAccess;
import usecases.usermanagement.UserManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager usrManager;

    @BeforeEach
    void setUp() {
        User usr1 = new User("usrname");
        User usr2 = new User("gamerboy123");
        HashMap<String, User> users = new HashMap<>();
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
        } catch (UserManager.UserNotFoundException e) {
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

    @Nested
    class DatabaseImport {
        UserDatabaseAccess udb;

        @BeforeEach
        void setUp() {
            this.udb = new FakeDatabase();
        }

        @Test
        void maintainedStatistics() {
            UserManager userManager = UserManager.importFromUserDatabase(this.udb);

            assertAll(
                    // alpha
                    () -> assertAll(
                            () -> assertEquals(123, userManager.getGamesPlayed("alpha")),
                            () -> assertEquals(456, userManager.getWins("alpha")),
                            () -> assertEquals(789, userManager.getGamesTied("alpha"))
                    ),
                    // beta
                    () -> assertAll(
                            () -> assertEquals(987, userManager.getGamesPlayed("beta")),
                            () -> assertEquals(654, userManager.getWins("beta")),
                            () -> assertEquals(321, userManager.getGamesTied("beta"))
                    )
            );
        }

        class FakeDatabase implements UserDatabaseAccess {

            @Override
            public Set<String> getAllUsernames() {
                return Set.of("alpha", "beta");
            }

            @Override
            public boolean addUser(String username) {
                return false;
            }

            @Override
            public boolean userExists(String username) {
                return false;
            }

            @Override
            public void removeUser(String username) {

            }

            @Override
            public void setUserStatistics(String username, Map<String, Integer> statistics) {

            }

            @Override
            public void addUserStatistics(String username, Map<String, Integer> statistics) {

            }

            @Override
            public void addUserStatistics(String username, Collection<String> statistics) {

            }

            @Override
            public HashMap<String, Integer> getUserStatistics(String username, Collection<String> statistics) {
                return null;
            }

            @Override
            public HashMap<String, Integer> getUserStatistics(String username) {
                switch (username) {
                    case "alpha":
                        return new HashMap<>(Map.of("gamesPlayed", 123, "gamesWon", 456, "gamesTied", 789));
                    case "beta":
                        return new HashMap<>(Map.of("gamesPlayed", 987, "gamesWon", 654, "gamesTied", 321));
                }

                return new HashMap<>();
            }

            @Override
            public void close() {

            }
        }
    }

    @Nested
    class DatabaseExport {
        UserManager userManager;

        @BeforeEach
        void setUp() {
            this.userManager = new UserManager(new HashMap<>(Map.of(
                    "alpha", new User("alpha", 123, 456, 789),
                    "beta", new User("beta", 987, 654, 321))
            ));
        }

        @Test
        void export() {
            UserDatabaseAccess db = new FakeDatabase();
            this.userManager.exportToUserDatabase(db);

            Map<String, Integer> alphaStats;
            try {
                alphaStats = db.getUserStatistics("alpha");
            } catch (UserDatabaseAccess.UserNotFoundException e) {
                fail("Could not get alpha's statistics");
                return;
            }

            Map<String, Integer> betaStats;
            try {
                betaStats = db.getUserStatistics("beta");
            } catch (UserDatabaseAccess.UserNotFoundException e) {
                fail("Could not get beta's statistics");
                return;
            }

            assertAll(
                    // alpha
                    () -> assertAll(
                            () -> assertEquals(123, alphaStats.get("gamesPlayed")),
                            () -> assertEquals(456, alphaStats.get("gamesWon")),
                            () -> assertEquals(789, alphaStats.get("gamesTied"))
                    ),
                    // beta
                    () -> assertAll(
                            () -> assertEquals(987, betaStats.get("gamesPlayed")),
                            () -> assertEquals(654, betaStats.get("gamesWon")),
                            () -> assertEquals(321, betaStats.get("gamesTied"))
                    )
            );
        }

        class FakeDatabase implements UserDatabaseAccess {

            Map<String, HashMap<String, Integer>> users;

            FakeDatabase() {
                this.users = new HashMap<>();
            }

            @Override
            public Set<String> getAllUsernames() {
                return null;
            }

            @Override
            public boolean addUser(String username) {
                if (this.userExists(username)) {
                    return false;
                } else {
                    this.users.put(username, new HashMap<>());
                    return true;
                }
            }

            @Override
            public boolean userExists(String username) {
                return this.users.containsKey(username);
            }

            @Override
            public void removeUser(String username) {

            }

            @Override
            public void setUserStatistics(String username, Map<String, Integer> statistics) throws UserNotFoundException {
                if (this.userExists(username)) {
                    this.users.put(username, (HashMap<String, Integer>) statistics);
                } else {
                    throw new UserNotFoundException("User of " + username + " not found.");
                }
            }

            @Override
            public void addUserStatistics(String username, Map<String, Integer> statistics) {

            }

            @Override
            public void addUserStatistics(String username, Collection<String> statistics) {

            }

            @Override
            public HashMap<String, Integer> getUserStatistics(String username, Collection<String> statistics) {
                return null;
            }

            @Override
            public HashMap<String, Integer> getUserStatistics(String username) {
                return this.users.get(username);
            }

            @Override
            public void close() {

            }
        }
    }
}