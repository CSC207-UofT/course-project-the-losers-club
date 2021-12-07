package drivers;

import controllers.MainMenu;
import controllers.MainMenuIO;
import presenters.gui.MainMenuGUI;
import usecases.usermanagement.UserDatabaseAccess;
import userdatabases.SQLiteUserDatabase;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        MainMenuIO mainMenuGUI = new MainMenuGUI();
        MainMenu mainMenu = new MainMenu(mainMenuGUI, new String[]{"Crazy Eights", "War", "Go Fish", "Bura"});

        try (UserDatabaseAccess db = new SQLiteUserDatabase("db/users.db")) {
            mainMenu.run(db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}