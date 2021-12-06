package controllers;

import presenters.gui.GameGUIFactory;
import presenters.gui.UserDisplayGUI;
import usecases.GameTemplate;
import usecases.IOInterfaces.GameIO;
import usecases.usermanagement.UserDatabaseAccess;
import usecases.usermanagement.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * MainMenu is a class controlling the selection of games. It interacts with the main menu IO as well as the game
 * template IO to allow the user to select games and users, and displays game windows when they are played.
 */
public class MainMenu {

    private static final int WIDTH = 39;
    private final String[] GAMES;
    private final MainMenuIO MM_IO;

    /**
     * Instantiate a MainMenu.
     *
     * @param mmIO  main menu IO to retrieve user selections and output information
     * @param games Strings representing the Games to create
     */
    public MainMenu(MainMenuIO mmIO,
                    String[] games) {
        this.MM_IO = mmIO;
        this.GAMES = games;
    }

    /**
     * Run this MainMenu.
     * <p>
     * MainMenu allows a user to select the game they wish to play and runs that game.
     *
     * @param userDatabase user database
     */
    public void run(UserDatabaseAccess userDatabase) {
        UserManager userManager = UserManager.importFromUserDatabase(userDatabase);

        while (true) {
            int sel = this.MM_IO.getUserSelection(this.GAMES);
            while (!checkValidity(sel)) {
                this.MM_IO.sendPopup("Invalid menu selection.\n");
                sel = this.MM_IO.getUserSelection(this.GAMES);
            }

            if (sel == 9) {
                UserDisplay userDisplay = new UserDisplay(userManager, new UserDisplayGUI());
                userDisplay.run();
            } else {
                List<String> usernames = getUsernames(userManager, this.GAMES[sel]);

                handleUserSelection(sel, usernames, userManager);
            }

            userManager.exportToUserDatabase(userDatabase);
        }
    }

    /**
     * Handle the user's selection. This method will be the main runner of the selected Game.
     * Handle the user's selection.
     * <p>
     * This method will be the main runner of the selected Game.
     *
     * @param sel         the user's selection. Must be greater than 0.
     * @param usernames   usernames that are playing the game
     * @param userManager user management vessel
     */
    private void handleUserSelection(int sel, List<String> usernames, UserManager userManager) {
        String gameString = this.GAMES[sel];

        GameIO gameIO = GameGUIFactory.gameGUIFactory(gameString);
        GameTemplate game = GameTemplate.gameFactory(gameString, usernames, userManager, gameIO);
        game.startGame();
    }

    /**
     * Checks if the user's selection is a valid selection.
     *
     * @param sel the user's selection. Must be greater than 0.
     * @return false when the selection is invalid or true when the selection is valid
     */
    private boolean checkValidity(int sel) {
        return (sel == 9 || (sel <= this.GAMES.length && sel >= 0));
    }

    /**
     * Prompt the user for the usernames of the Users that are playing the game
     *
     * @param userManager user management vessel
     * @param game        the game that is to be played. Used to enforce minimum and maximum number of players
     * @return a list of usernames
     */
    private List<String> getUsernames(UserManager userManager, String game) {
        List<String> usernames = new ArrayList<>();

        int maxPlayers = GameTemplate.getMaxPlayers(game);
        int minPlayers = GameTemplate.getMinPlayers(game);

        if (maxPlayers == minPlayers) {
            this.MM_IO.sendPopup("Input " + maxPlayers + " usernames for " +
                    "players playing the game. Enter 'done' to finish.\n");
        } else {
            this.MM_IO.sendPopup("Input at least " + minPlayers + " usernames and up to " + maxPlayers + " usernames for " +
                    "players playing the game. Enter 'done' to finish.\n");
        }

        String username = this.MM_IO.getUsername();

        while ((!username.equalsIgnoreCase("done") && usernames.size() < maxPlayers) || usernames.size() < minPlayers) {
            if (username.equalsIgnoreCase("done") && usernames.size() < minPlayers) {
                this.MM_IO.sendPopup("Please enter at least " + minPlayers + " usernames!\n");
            } else if (usernames.contains(username)) {
                this.MM_IO.sendPopup("This username has already been added. Please enter a new username!");
            } else {
                usernames.add(username);
                try {
                    userManager.addUser(username);
                } catch (UserManager.UserAlreadyExistsException ignored) {
                    // normal to reach here, no exception handling necessary
                }
            }

            if (usernames.size() != maxPlayers) {
                username = this.MM_IO.getUsername();
            }
        }
        return usernames;
    }
}
