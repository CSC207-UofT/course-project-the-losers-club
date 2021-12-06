package controllers;

import presenters.gui.UserDisplayGUI;
import usecases.GameTemplate;
import usecases.usermanagement.UserDatabaseAccess;
import usecases.usermanagement.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * MainMenu is a class controlling the selection of games.
 */
public class MainMenu {

    private static final int WIDTH = 39;
    private final String[] GAMES;
    private final MainMenu.Input SELECTOR_INPUT;
    private final MainMenu.Output SELECTOR_OUTPUT;
    private final GameTemplate.Input GAME_INPUT;
    private final GameTemplate.Output GAME_OUTPUT;
    private final String DASHES;

    /**
     * Instantiate a MainMenu.
     *
     * @param selectorInput  implementor of MainMenu.Input used for gathering input from the user
     * @param selectorOutput implementor of MainMenu.Output used for sending output back to the user
     * @param games          Strings representing the Games to create
     * @param gameInput      implementor of GameTemplate.Input used for gathering game specific input from the user
     * @param gameOutput     implementor of GameTemplate.Output used for sending output back to the user
     */
    public MainMenu(MainMenu.Input selectorInput,
                    MainMenu.Output selectorOutput,
                    String[] games,
                    GameTemplate.Input gameInput,
                    GameTemplate.Output gameOutput) {
        this.SELECTOR_INPUT = selectorInput;
        this.SELECTOR_OUTPUT = selectorOutput;

        this.GAME_INPUT = gameInput;
        this.GAME_OUTPUT = gameOutput;

        this.GAMES = games;

        this.DASHES = "=".repeat(WIDTH);
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
            displayMenu();

            int sel = this.SELECTOR_INPUT.getUserSelection();
            while (!checkValidity(sel)) {
                this.SELECTOR_OUTPUT.sendOutput("Invalid menu selection.\n");
                sel = this.SELECTOR_INPUT.getUserSelection();
            }

            if (sel == 0) {
                userManager.exportToUserDatabase(userDatabase);
                return;
            } else if (sel == 9) {
                UserDisplay userDisplay = new UserDisplay(userManager, new UserDisplayGUI());
                userDisplay.run();
            } else {
                List<String> usernames = getUsernames(userManager, this.GAMES[sel - 1]);

                handleUserSelection(sel, usernames, userManager);
            }

            userManager.exportToUserDatabase(userDatabase);
        }
    }

    /**
     * Display the game menu.
     */
    private void displayMenu() {
        this.SELECTOR_OUTPUT.sendOutput("" + this.DASHES + "\n              GAME SELECT              \n" + this.DASHES + "\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.GAMES.length; i++) {
            // [#] GAME NAME
            sb.append("[").append(i + 1).append("] ").append(this.GAMES[i]).append("\n");
        }
        this.SELECTOR_OUTPUT.sendOutput(sb.toString());
        this.SELECTOR_OUTPUT.sendOutput("\n[9] CHECK STATS\n");
        this.SELECTOR_OUTPUT.sendOutput("[0] EXIT\n");
        this.SELECTOR_OUTPUT.sendOutput(DASHES + "\n");
    }

    /**
     * Handle the user's selection.
     * <p>
     * This method will be the main runner of the selected Game.
     *
     * @param sel         the user's selection. Must be greater than 0.
     * @param usernames   usernames that are playing the game
     * @param userManager user management vessel
     */
    private void handleUserSelection(int sel, List<String> usernames, UserManager userManager) {
        String gameString = this.GAMES[sel - 1];
        this.SELECTOR_OUTPUT.sendOutput(DASHES + "\n\n\n\n\n" + DASHES + "\n");

        this.SELECTOR_OUTPUT.sendOutput(String.format("%-" + (WIDTH / 2 - gameString.length() / 2) + "s", " ") + gameString + "\n");
        this.SELECTOR_OUTPUT.sendOutput(this.DASHES + "\n\n\n\n\n");
        GameTemplate game = GameTemplate.gameFactory(gameString, usernames, userManager, this.GAME_INPUT, this.GAME_OUTPUT);
        game.startGame();
        this.SELECTOR_OUTPUT.sendOutput("\n\n\n\n\n");
    }

    /**
     * Checks if the user's selection is a valid selection.
     *
     * @param sel the user's selection. Must be greater than 0.
     * @return false when the selection is invalid or true when the selection is valid
     */
    private boolean checkValidity(int sel) {
        return (sel == 0 || sel == 9) || (sel <= this.GAMES.length && sel > 0);
    }

    /**
     * Prompt the user for the usernames of the <code>User</code>s that playing the game
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
            this.SELECTOR_OUTPUT.sendOutput("Input " + maxPlayers + " usernames for " +
                    "players playing the game. Enter 'done' to finish.\n");
        } else {
            this.SELECTOR_OUTPUT.sendOutput("Input at least " + minPlayers + " usernames and up to " + maxPlayers + " usernames for " +
                    "players playing the game. Enter 'done' to finish.\n");
        }

        String username = this.SELECTOR_INPUT.getUsername();
        while ((!username.equalsIgnoreCase("done") && usernames.size() < maxPlayers) || usernames.size() < minPlayers) {
            if (username.equalsIgnoreCase("done") && usernames.size() < minPlayers) {
                this.SELECTOR_OUTPUT.sendOutput("Please enter at least " + minPlayers + " usernames!\n");
            } else if (usernames.contains(username)) {
                this.SELECTOR_OUTPUT.sendOutput("This username has already been added. Please enter a new username!\n");
            } else {
                usernames.add(username);
                try {
                    userManager.addUser(username);
                } catch (UserManager.UserAlreadyExistsException ignored) {
                    // normal to reach here, no exception handling necessary
                }
            }

            if (usernames.size() != maxPlayers) {
                username = this.SELECTOR_INPUT.getUsername();
            }
        }
        return usernames;
    }

    /**
     * Input is an interface allowing this controller to retrieve input from a user.
     */
    public interface Input {

        /**
         * Implementations should return an integer representing the user's selection.
         *
         * @return an integer representing the selection
         */
        int getUserSelection();


        /**
         * Implementations should return a String representing the user's username selection
         *
         * @return a valid username representation
         * @see helpers.UsernameCheck
         */
        String getUsername();

    }

    /**
     * Output is an interface allowing this controller to output back to the user.
     */
    public interface Output {

        /**
         * Implementations should take the given Object s and handle it's output to the user.
         * How this is done depends on the implementation.
         *
         * @param o An Object that is to be output to the user.
         */
        void sendOutput(Object o);

    }
}
