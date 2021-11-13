package presenters.console;

import controllers.GameSelector;
import helpers.CardCheck;
import usecases.GameTemplate;
import usecases.Player;

import java.util.*;
import java.util.regex.Pattern;

import static helpers.UsernameCheck.checkUsername;

/**
 * Input uses System.in to gather input from the user.
 * <p>
 * It is intended to be used as a basic command line interface.
 *
 * @see GameSelector.Input
 * @see GameTemplate.Input
 */
public class Input implements GameSelector.Input, GameTemplate.Input {

    private final Scanner inp;

    /**
     * Instantiate a new Input instance.
     */
    public Input() {
        this.inp = new Scanner(System.in);
    }

    /**
     * Prompt user to specify which card they wish to select.
     *
     * @return a String encoding the selected card. The first character of the String is always the suit
     * of the selected card (one of 'C', 'D', 'H', 'S'), while the remaining characters are used to signify the
     * rank of the selected card (one of "A", "2", "3", "4", "5", "6", "7", "8', "9", "10", "J", "Q", "K").
     */
    @Override
    public String getCard() {
        System.out.print("Enter the rank of your desired card followed by the suit (e.g. \"10s\" for the 10 of Spades): ");
        String line = this.inp.nextLine().trim();

        // if invalid card
        while (!CardCheck.checkCard(line)) {
            System.out.print("Invalid selection, try again: ");
            line = this.inp.nextLine().trim();
        }

        line = line.toUpperCase();
        return line.charAt(line.length() - 1) + line.substring(0, line.length() - 1);
    }

    /**
     * Prompt user to indicate whether they wish to draw a card from the deck.
     *
     * @return a boolean representing whether the user wishes to draw a card from the deck
     */
    @Override
    public boolean drawCard() {
        System.out.print("Do you want to draw a card (\"Y\", \"N\"): ");
        String line = this.inp.nextLine().trim().toUpperCase();

        // if invalid card selection
        while (line.length() != 1 || (!line.equals("Y") && !line.equals("N"))) {
            System.out.print("Invalid selection (\"Y\", \"N\"), try again: ");
            line = this.inp.nextLine().trim().toUpperCase();
        }

        return line.equals("Y");
    }

    /**
     * Prompt user to select a suit.
     *
     * @return a character encoding the selected suit. The returned character will be one of {'C', 'D', 'H', 'S'}
     * representing Clubs, Diamonds, Hearts, Spades respectively.
     */
    @Override
    public char getSuit() {
        System.out.print("Pick a suit (one of 'C', 'D', 'H', 'S'): ");
        String line = this.inp.nextLine().trim().toUpperCase();

        // check for invalid suit selection
        while (line.length() != 1 || (line.charAt(0) != 'C' && line.charAt(0) != 'D' && line.charAt(0) != 'H' && line.charAt(0) != 'S')) {
            System.out.print("Invalid selection ('C', 'D', 'H', 'S'), try again: ");
            line = this.inp.nextLine().trim().toUpperCase();
        }

        return line.charAt(0);
    }

    /**
     * Prompt user to select a rank.
     *
     * @return a string encoding the selected rank. The returned string will be one of
     * {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"}
     */
    public String getRank() {
        String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        System.out.print("Pick a rank (one of " + Arrays.toString(RANKS) + "): ");
        String line = this.inp.nextLine().trim().toUpperCase();

        while (!Arrays.asList(RANKS).contains(line)) {
            System.out.print("Invalid selection (" + Arrays.toString(RANKS) + "), try again: ");
            line = this.inp.nextLine().trim().toUpperCase();
        }
        return line;
    }

    /**
     * Prompt user to select a player.
     *
     * @param currPlayerUsername the current player username in the game and is not in the list of player usernames displayed to the user.
     * @param usernames the list of all the player usernames in the game.
     * @return a String representing the username selected by the user. This username is in the list of all usernames.
     */
    public String getPlayerUsername(String currPlayerUsername, List<String> usernames) {
        List<String> usernamesForDisplay = new ArrayList<>();
        for (String username : usernames) {
            if (!username.equals(currPlayerUsername)) {
                usernamesForDisplay.add(username);
            }
        }
        System.out.print("Pick a player (one of " + usernamesForDisplay + "): ");
        String username = this.inp.nextLine().trim();

        while (!usernamesForDisplay.contains(username)) {
            System.out.print("Invalid selection (" + usernamesForDisplay + "), try again: ");
            username = this.inp.nextLine().trim();
        }
        return username;
    }

    /**
     * Prompt the user to press enter to continue.
     *
     * @return a boolean representing whether execution should continue. Note that this method will ALWAYS return true.
     */
    public boolean stall() {
        System.out.print("Press enter to continue: ");
        this.inp.nextLine();  // do nothing with input

        return true;
    }


    /***
     * Prompt the user to enter a menu selection and return their selection.
     *
     * @return an integer representing the user's menu selection
     */
    @Override
    public int getUserSelection() {
        System.out.print("Make a selection: ");
        String line = this.inp.nextLine().trim();

        while (!Pattern.matches("^\\d+$", line)) {
            System.out.print("Invalid selection, try again: ");
            line = this.inp.nextLine().trim();
        }

        return Integer.parseInt(line);
    }

    /**
     * Promp the user to enter a username and return the username.
     *
     * @return a String representing the user's username
     */
    @Override
    public String getUsername() {
        System.out.println("Input all usernames for players playing the game. Enter 'done' to finish.");
        System.out.print("Enter a Username: ");

        String line = this.inp.nextLine().trim();

        while (!checkUsername(line)){
            System.out.print("Invalid username, try again: ");
            line = this.inp.nextLine().trim();
        }

        return line;
    }
}
