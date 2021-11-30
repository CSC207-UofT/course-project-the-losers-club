package userdata;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SQLiteUserDatabaseTest {

    SQLiteUserDatabase db;
    String filePath;

    @BeforeEach
    void setUp() {
        this.filePath = "usermanager-" + (new Date()).getTime() + (new Random()).nextInt() + ".db";
        this.db = new SQLiteUserDatabase(filePath);
    }

    @AfterEach
    void tearDown() {
        this.db.close();
        if (!(new File(this.filePath)).delete()) {
            fail("File could not be deleted, is it still in use?");
        }
    }

    @Nested
    class getAllUsernames {

        Set<String> usernames = Set.of("alpha", "beta", "gamma", "delta");

        @BeforeEach
        void setUp() {
            for (String username : usernames) {
                db.addUser(username);
            }
        }

        @Test
        void getAll() {
            assertEquals(usernames, db.getAllUsernames());
        }
    }


    @Nested
    class SetUserStatistics {
        @BeforeEach
        void setUp() {
            db.addUser("alpha");
            db.addUser("beta");
        }

        @Test
        void values() {
            Map<String, Integer> origStatistics = Map.of("gamesPlayed", 57, "gamesWon", 632, "gamesTied", 3328);

            try {
                db.setUserStatistics("alpha", origStatistics);
            } catch (UserDatabaseGateway.UserNotFoundException e) {
                fail("UserNotFoundException thrown when user should exist.");
            }

            Map<String, Integer> retStatistics;
            try {
                retStatistics = db.getUserStatistics("alpha", origStatistics.keySet());
            } catch (UserDatabaseGateway.UserNotFoundException e) {
                fail("UserNotFoundException thrown when user should exist.");
                return;
            }

            assertEquals(origStatistics, retStatistics);
        }

        @Test
        void zero() {
            Map<String, Integer> origStatistics = Map.of("gamesPlayed", 0, "gamesWon", 0, "gamesTied", 0);

            try {
                db.setUserStatistics("alpha", origStatistics);
            } catch (UserDatabaseGateway.UserNotFoundException e) {
                fail("UserNotFoundException thrown when user should exist.");
            }

            Map<String, Integer> retStatistics;
            try {
                retStatistics = db.getUserStatistics("alpha", origStatistics.keySet());
            } catch (UserDatabaseGateway.UserNotFoundException e) {
                fail("UserNotFoundException thrown when user should exist.");
                return;
            }

            assertEquals(origStatistics, retStatistics);
        }

        @Test
        void badUser() {
            assertThrows(UserDatabaseGateway.UserNotFoundException.class,
                    () -> db.setUserStatistics("non greek letter", Map.of("gamesPlayed", 10)));
        }
    }

    @Nested
    class AddUserStatistics {

        String user = "alpha";
        String stat = "gamesPlayed";
        int origValue = 76;

        @BeforeEach
        void setUp() {
            db.addUser(this.user);

            try {
                db.setUserStatistics(this.user, Map.of(this.stat, this.origValue));
            } catch (UserDatabaseGateway.UserNotFoundException e) {
                fail("UserNotFoundException thrown when user should exist.");
            }
        }

        @Nested
        class Increment {
            @Test
            void valid() {
                try {
                    db.addUserStatistics(user, Set.of(stat));
                } catch (UserDatabaseGateway.UserNotFoundException e) {
                    fail("UserNotFoundException thrown when user should exist.");
                }

                try {
                    assertEquals(origValue + 1, db.getUserStatistics(user, Set.of(stat)).get(stat));
                } catch (UserDatabaseGateway.UserNotFoundException e) {
                    fail("UserNotFoundException thrown when user should exist.");
                }
            }

            @Test
            void invalid() {
                assertThrows(UserDatabaseGateway.UserNotFoundException.class,
                        () -> db.addUserStatistics("greek letter", Set.of(stat)));
            }
        }

        @Nested
        class SpecificValue {

            int increase = 23;

            @Test
            void valid() {
                try {
                    db.addUserStatistics(user, Map.of(stat, increase));
                } catch (UserDatabaseGateway.UserNotFoundException e) {
                    fail("UserNotFoundException thrown when user should exist.");
                }

                try {
                    assertEquals(origValue + increase, db.getUserStatistics(user, Set.of(stat)).get(stat));
                } catch (UserDatabaseGateway.UserNotFoundException e) {
                    fail("UserNotFoundException thrown when user should exist.");
                }
            }

            @Test
            void invalid() {
                assertThrows(UserDatabaseGateway.UserNotFoundException.class,
                        () -> db.addUserStatistics("greek letter", Set.of(stat)));
            }
        }
    }

    @Nested
    class GetUserStatistics {

        Map<String, Integer> alphaStat;
        Map<String, Integer> betaStat;

        @BeforeEach
        void setUp() {
            db.addUser("alpha");
            db.addUser("beta");

            this.alphaStat = Map.of("gamesPlayed", 101, "gamesWon", 200);
            this.betaStat = Map.of("gamesPlayed", 0, "gamesWon", 0);

            try {
                db.setUserStatistics("alpha", this.alphaStat);
            } catch (UserDatabaseGateway.UserNotFoundException e) {
                fail("UserNotFoundException thrown when user should exist.");
            }
        }

        @Test
        void invalid() {
            assertThrows(UserDatabaseGateway.UserNotFoundException.class, () -> db.getUserStatistics("greek letter"));
        }

        @Nested
        class SpecificColumns {
            @Test
            void getUserStatistics() {
                HashMap<String, Integer> resultStatistics;
                try {
                    resultStatistics = db.getUserStatistics("alpha", alphaStat.keySet());
                } catch (UserDatabaseGateway.UserNotFoundException e) {
                    fail("UserNotFoundException thrown when user should exist.");
                    return;
                }

                assertEquals(resultStatistics, alphaStat);
            }
        }
    }

    @Nested
    class RemoveUser {
        @BeforeEach
        void setUp() {
            db.addUser("alpha");
            db.addUser("beta");
        }

        @Test
        void valid() {
            try {
                db.removeUser("alpha");
            } catch (UserDatabaseGateway.UserNotFoundException e) {
                fail("UserNotFoundException thrown when user should exist.");
            }

            assertFalse(db.userExists("alpha"));
            assertTrue(db.userExists("beta"));
        }

        @Test
        void invalid() {
            assertThrows(UserDatabaseGateway.UserNotFoundException.class, () -> db.removeUser("gamma"));
        }
    }

    @Nested
    class UserExists {
        @BeforeEach
        void setUp() {
            db.addUser("alpha");
        }

        @Test
        void userExistsTrue() {
            assertAll(
                    () -> assertTrue(db.userExists("alpha")),
                    () -> assertTrue(db.userExists("aLphA"))
            );
        }

        @Test
        void userExistsFalse() {
            assertAll(
                    () -> assertFalse(db.userExists("beta")),
                    () -> assertFalse(db.userExists("BEtA")),
                    () -> assertFalse(db.userExists(""))
            );
        }
    }

    @Nested
    class AddUser {
        @Test
        void addUser() {
            assertTrue(db.addUser("ALpHA"));
            assertFalse(db.addUser("alpha"));

            assertTrue(db.addUser("beta"));
            assertFalse(db.addUser("beTA"));

            assertTrue(db.userExists("alpha"));
            assertTrue(db.userExists("beta"));
        }
    }

}