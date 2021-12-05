package drivers;

import controllers.MainMenu;
import presenters.console.Input;
import presenters.console.Output;
import usecases.usermanagement.UserDatabaseAccess;
import userdatabases.SQLiteUserDatabase;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        MainMenu.Input selectorInput = new Input();
        MainMenu.Output selectorOutput = new Output();

        MainMenu selector = new MainMenu(selectorInput, selectorOutput, new String[]{"Crazy Eights", "War", "Go Fish", "Bura"});

        try (UserDatabaseAccess db = new SQLiteUserDatabase("db/users.db")) {
            selector.run(db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}