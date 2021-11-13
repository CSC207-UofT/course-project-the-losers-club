package controllers;

import usecases.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * GameSelector is a class controlling the selection of games.
 */
public class GameSelector {

    private static final int WIDTH = 39;
    private final String[] games;
    private final GameSelector.Input selectorInput;
    private final GameSelector.Output selectorOutput;
    private final GameTemplate.Input gameInput;
    private final GameTemplate.Output gameOutput;
    private final String dashes;

    /**
     * Instantiate a GameSelector.
     *
     * @param selectorInput  implementor of GameSelector.Input used for gathering input from the user
     * @param selectorOutput implementor of GameSelector.Output used for sending output back to the user
     * @param games          Strings representing the Games to create
     * @param gameInput      implementor of GameTemplate.Input used for gathering game specific input from the user
     * @param gameOutput     implementor of GameTemplate.Output used for sending output back to the user
     */
    public GameSelector(GameSelector.Input selectorInput,
                        GameSelector.Output selectorOutput,
                        String[] games,
                        GameTemplate.Input gameInput,
                        GameTemplate.Output gameOutput) {
        this.selectorInput = selectorInput;
        this.selectorOutput = selectorOutput;

        this.gameInput = gameInput;
        this.gameOutput = gameOutput;

        this.games = games;

        this.dashes = "=".repeat(WIDTH);
    }

    /**
     * Run this GameSelector.
     * <p>
     * GameSelector allows a user to select the game they wish to play and runs that game.
     *
     * @param userManagerInputFile  a String representing a file with a serialized <code>UserManager</code> to import
     * @param userManagerOutputFile a String representing a file to serialize a <code>UserManager</code> to
     */
    public void run(String userManagerInputFile, String userManagerOutputFile) {

        UserManager userManager = this.importUserManager(userManagerInputFile);

        while (true) {
            displayMenu();

            int sel = this.selectorInput.getUserSelection();
            while (!checkValidity(sel)) {
                this.selectorOutput.sendOutput("Invalid menu selection.\n");
                sel = this.selectorInput.getUserSelection();
            }

            if (sel == 0) {
                this.exportUserManager(userManager, userManagerOutputFile);
                return;
            } else if (sel == 9) {
                UserDisplay userDisplay = new UserDisplay(userManager, (UserDisplay.Input) this.selectorInput,
                        (UserDisplay.Output) this.selectorOutput);
                userDisplay.run();
            } else {
                List<String> usernames = getUsernames(userManager, this.games[sel - 1]);

                handleUserSelection(sel, usernames, userManager);
                this.exportUserManager(userManager, userManagerOutputFile);
            }
        }
    }

    /**
     * Display the game menu.
     */
    private void displayMenu() {
        this.selectorOutput.sendOutput("" + this.dashes + "\n              GAME SELECT              \n" + this.dashes + "\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.games.length; i++) {
            // [#] GAME NAME
            sb.append("[").append(i + 1).append("] ").append(this.games[i]).append("\n");
        }
        this.selectorOutput.sendOutput(sb.toString());
        this.selectorOutput.sendOutput("[9] CHECK STATS\n");
        this.selectorOutput.sendOutput("[0] EXIT\n");
        this.selectorOutput.sendOutput(dashes + "\n");
    }

    /**
     * Handle the user's selection.
     * <p>
     * This method will be the main runner of the selected Game.
     *
     * @param sel the user's selection. Must be greater than 0.
     */
    private void handleUserSelection(int sel, List<String> usernames, UserManager userManager) {
        String gameString = this.games[sel - 1];
        this.selectorOutput.sendOutput(dashes + "\n\n\n\n\n" + dashes + "\n");

        this.selectorOutput.sendOutput(String.format("%-" + (WIDTH / 2 - gameString.length() / 2) + "s", " ") + gameString + "\n");
        this.selectorOutput.sendOutput(this.dashes + "\n\n\n\n\n");
        GameTemplate game = GameTemplate.gameFactory(gameString, usernames, userManager, this.gameInput, this.gameOutput);
        game.startGame();
        this.selectorOutput.sendOutput("\n\n\n\n\n");
    }

    /**
     * Checks if the user's selection is a valid selection.
     *
     * @param sel the user's selection. Must be greater than 0.
     * @return false when the selection is invalid or true when the selection is valid
     */
    private boolean checkValidity(int sel) {
        return (sel == 0 || sel == 9) || (sel <= this.games.length && sel > 0);
    }

    /**
     * Prompt the user for the usernames of the <code>User</code>s that playing the game
     *
     * @param userManager <code>UserManager</code> to add users to
     * @param game        the game that is to be played. Used to enforce minimum and maximum number of players
     * @return a list of usernames
     */
    private List<String> getUsernames(UserManager userManager, String game) {
        List<String> usernames = new ArrayList<>();

        int maxPlayers = GameTemplate.getMaxPlayers(game);
        int minPlayers = GameTemplate.getMinPlayers(game);

        if (maxPlayers == minPlayers) {
            this.selectorOutput.sendOutput("Input " + maxPlayers + " usernames for " +
                    "players playing the game. Enter 'done' to finish.\n");
        } else {
            this.selectorOutput.sendOutput("Input at least " + minPlayers + " usernames and up to " + maxPlayers + " usernames for " +
                    "players playing the game. Enter 'done' to finish.\n");
        }

        String username = this.selectorInput.getUsername();
        while ((!username.equalsIgnoreCase("done") && usernames.size() < maxPlayers) || usernames.size() < minPlayers) {
            if (username.equalsIgnoreCase("done") && usernames.size() < minPlayers) {
                this.selectorOutput.sendOutput("Please enter at least " + minPlayers + " usernames!\n");
            } else if (usernames.contains(username)) {
                this.selectorOutput.sendOutput("This username has already been added. Please enter a new username!\n");
            } else {
                usernames.add(username);
                try {
                    userManager.addUser(username);
                } catch (UserManager.UserAlreadyExistsException ignored) {
                }
            }

            if (usernames.size() != maxPlayers) {
                username = this.selectorInput.getUsername();
            }
        }
        return usernames;
    }

    /**
     * Import and return a <code>UserManager</code> serialized in the specified <code>inputFilePath</code>.
     *
     * @param inputFilePath file path to a serialized <code>UserManager</code>
     * @return the deserialized <code>UserManager</code>
     */
    private UserManager importUserManager(String inputFilePath) {
        UserManager userManager;
        try {
            userManager = UserManagerImporter.importUserManager(inputFilePath);
        } catch (IOException | ClassNotFoundException ignored) {
            userManager = new UserManager();  // ignore input file if exception thrown
        }
        return userManager;
    }

    /**
     * Export the given <code>UserManager</code> to the specified <code>outputFilePath</code>
     *
     * @param userManager    the <code>UserManager</code> to serialize
     * @param outputFilePath the file to serialize to
     */
    private void exportUserManager(UserManager userManager, String outputFilePath) {
        try {
            UserManagerExporter.exportUserManager(userManager, outputFilePath);
        } catch (IOException e) {
            // dump UserManager to a unique file (should be unique since using current system time)
            Date currDate = new Date();
            try {
                UserManagerExporter.exportUserManager(userManager, "usermanager-" + currDate.getTime() + ".ser");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
