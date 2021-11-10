package usecases;

import entities.User;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserManagerExportImportTest {

    static final String FILE_PATH = "usermanager-" + (new Date()).getTime() + (new Random()).nextInt() + ".ser";
    UserManager userManager;
    HashMap<String, User> users;

    @AfterAll
    static void teardown() {
        if (!(new File(FILE_PATH)).delete()) {
            fail("File could not be deleted, is it still in use?");
        }
    }

    @BeforeEach
    void setUp() {
        this.users = new HashMap<>(Map.of(
                "raymond", new User("raymond"),
                "hermione", new User("hermione")
        ));
        this.userManager = new UserManager(users);
    }

    @Test
    @Order(1)
    void exportUserManager() {
        assertEquals(0, (new File(FILE_PATH)).length());
        try {
            UserManagerExporter.exportUserManager(this.userManager, FILE_PATH);
        } catch (IOException e) {
            fail("An IOException was thrown on export");
        }
        assertTrue((new File(FILE_PATH)).length() > 0);
    }

    @Test
    @Order(2)
    void importUserManager() {
        assertTrue((new File(FILE_PATH)).length() > 0);
        UserManager importedUserManager;
        try {
            importedUserManager = UserManagerImporter.importUserManager(FILE_PATH);
        } catch (IOException | ClassNotFoundException e) {
            fail("IOException or ClassNotFoundException thrown");
            return;
        }

        for (String k : this.users.keySet()) {
            assertTrue(importedUserManager.hasUser(k));
            assertAll("User manager information not preserved through export/import",
                    () -> assertEquals(this.userManager.getGamesPlayed(k), importedUserManager.getGamesPlayed(k)),
                    () -> assertEquals(this.userManager.getWins(k), importedUserManager.getWins(k)),
                    () -> assertEquals(this.userManager.getGamesTied(k), importedUserManager.getGamesTied(k))
            );
        }
    }
}