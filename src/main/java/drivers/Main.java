package drivers;

import controllers.MainMenu;
import presenters.console.Input;
import presenters.console.Output;
import usecases.GameTemplate;
import usecases.usermanagement.UserDatabaseAccess;
import userdatabases.SQLiteUserDatabase;

public class Main {
    public static void main(String[] args) {
        MainMenu.Input selectorInput = new Input();
        MainMenu.Output selectorOutput = new Output();

        MainMenu selector = new MainMenu(selectorInput, selectorOutput, new String[]{"Crazy Eights", "War", "Go Fish"},
                (GameTemplate.Input) selectorInput, (GameTemplate.Output) selectorOutput);

        UserDatabaseAccess db = new SQLiteUserDatabase("db/users.db");
        selector.run(db);
    }
}