package drivers;

import controllers.MainMenu;
import presenters.console.Input;
import presenters.console.Output;
import presenters.gui.UserDisplayGUI;
import usecases.GameTemplate;
import usecases.usermanagement.UserDatabaseAccess;
import userdatabases.SQLiteUserDatabase;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        MainMenu.Input selectorInput = new Input();
        MainMenu.Output selectorOutput = new Output();

        MainMenu selector = new MainMenu(selectorInput, selectorOutput, new String[]{"Crazy Eights", "War", "Go Fish", "Bura"},
                (GameTemplate.Input) selectorInput, (GameTemplate.Output) selectorOutput);

        try (UserDatabaseAccess db = new SQLiteUserDatabase("db/users.db")) {
            selector.run(db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}