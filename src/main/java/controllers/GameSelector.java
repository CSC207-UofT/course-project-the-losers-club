package controllers;

import usecases.CrazyEights;
import usecases.GameTemplate;

/**
 * GameSelector is a class controlling the selection of games.
 */
public class GameSelector {

    private final GameTemplate[] games;

    private final GameSelector.Input selectorInput;
    private final GameSelector.Output selectorOutput;

    private final GameTemplate.Input gameInput;
    private final GameTemplate.Output gameOutput;

    /**
     * Instantiate a GameSelector.
     *
     * @param selectorInput  implementor of GameSelector.Input used for gathering input from the user
     * @param selectorOutput implementor of GameSelector.Output used for sending output back to the user
     * @param gameInput      implementor of GameTemplate.Input used for gathering game specific input from the user
     * @param gameOutput     implementor of GameTemplate.Output used for sending output back to the user
     */
    public GameSelector(GameSelector.Input selectorInput,
                        GameSelector.Output selectorOutput,
                        GameTemplate.Input gameInput,
                        GameTemplate.Output gameOutput) {
        this.selectorInput = selectorInput;
        this.selectorOutput = selectorOutput;

        this.gameInput = gameInput;
        this.gameOutput = gameOutput;

        this.games = new GameTemplate[]{new CrazyEights(2, this.gameInput, this.gameOutput)};
    }

    /**
     * Run this GameSelector.
     * <p>
     * GameSelector allows a user to select the game they wish to play and runs that game.
     */
    public void run() {
        displayMenu();

        int sel = this.selectorInput.getUserSelection();

        // check for exit case
        if (sel == 0) {
            return;
        }

        while (!handleUserSelection(sel)) {
            this.selectorOutput.sendOutput("Invalid menu selection.");
            sel = this.selectorInput.getUserSelection();
        }
    }

    /**
     * Display the game menu.
     */
    private void displayMenu() {
        this.selectorOutput.sendOutput("---- MENU DISPLAY -----");
    }

    /**
     * Handle the user's selection.
     * <p>
     * This method will be the main runner of the selected Game.
     *
     * @param sel the user's selection
     * @return false when the selection is invalid or true when the execution completes
     */
    private boolean handleUserSelection(int sel) {
        return true;
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

    }

    /**
     * Output is an interface allowing this controller to output back to the user.
     */
    public interface Output {

        /**
         * Implementations should take the given Object s and handle it's output to the user.
         * How this is done depends on the implementation.
         *
         * @param o An Object that can be somehow outputted to the user.
         */
        void sendOutput(Object o);

    }
}
