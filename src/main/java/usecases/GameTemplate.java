package usecases;

import entities.Card;
import entities.Deck;
import presenters.gui.PlayerGUI;
import presenters.gui.SingleCardGUI;
import userdata.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class GameTemplate {

    protected static final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    protected static final char[] SUITS = {'H', 'S', 'D', 'C'};
    protected final Input GAME_INPUT;
    protected final Output GAME_OUTPUT;
    protected Player[] players;
    protected Deck deck;
    protected Player currPlayer;
    protected UserManager userManager;
    protected List<String> usernames;
    protected int currPlayerIndex;

    /**
     * Construct a <code>GameTemplate</code>.
     *
     * @param usernames   usernames of those playing the game
     * @param userManager manager for storing user information
     * @param gameInput   an object implementing <code>GameTemplate.Input</code>
     * @param gameOutput  an object implementing <code>GameTemplate.output</code>
     */
    protected GameTemplate(List<String> usernames, UserManager userManager, Input gameInput, Output gameOutput) {
        this.userManager = userManager;
        this.usernames = usernames;

        this.GAME_INPUT = gameInput;
        this.GAME_OUTPUT = gameOutput;

        this.players = new Player[usernames.size()];
        for (int i = 0; i < players.length; i++) {
            Player newPlayer = new Player(usernames.get(i));
            this.players[i] = newPlayer;
        }
        this.currPlayer = this.players[0];

        // Creates a deck with the cards created outside the Deck class
        List<Card> cardList = new ArrayList<>();
        for (String i : RANKS) {
            for (char j : SUITS) {
                cardList.add(new Card(i, j));
            }
        }
        this.deck = new Deck(cardList);
    }

    /**
     * Create a new <code>GameTemplate</code> instance based on the given game name
     *
     * @param name        the game to create
     * @param usernames   list of usernames to play the game
     * @param userManager <code>UserManager</code> that has users for each of the given usernames
     * @param input       an object implementing <code>GameTemplate.Input</code>
     * @param output      an object implementing <code>GameTemplate.output</code>
     * @return the requested game instance
     */
    public static GameTemplate gameFactory(String name, List<String> usernames, UserManager userManager, Input input, Output output) {
        switch (name.toUpperCase()) {
            case "CRAZY EIGHTS":
                return new CrazyEights(usernames, userManager, new PlayerGUI(""), new SingleCardGUI(""));
            case "WAR":
                return new War(usernames, userManager, input, output);
            case "GO FISH":
                return new GoFish(usernames, userManager, input, output);
            default:
                throw new IllegalArgumentException("Illegal game selection of " + name + '.');
        }
    }

    /**
     * Returns a maximum number of players based on the given game.
     *
     * @param name The name of a possible game
     * @return an integer of the maximum number of players allowed to play the game
     */
    public static int getMaxPlayers(String name) {
        switch (name.toUpperCase()) {
            case "CRAZY EIGHTS":
                return CrazyEights.getMaxPlayers();
            case "WAR":
                return War.getMaxPlayers();
            case "GO FISH":
                return GoFish.getMaxPlayers();
            default:
                throw new IllegalArgumentException("Illegal game selection of " + name + '.');
        }
    }

    /**
     * Returns a minimum number of players based on the given game.
     *
     * @param name the name of a possible game
     * @return an integer of the maximum number of players allowed to play the game
     */
    public static int getMinPlayers(String name) {
        switch (name.toUpperCase()) {
            case "CRAZY EIGHTS":
                return CrazyEights.getMinPlayers();
            case "WAR":
                return War.getMinPlayers();
            case "GO FISH":
                return GoFish.getMinPlayers();
            default:
                throw new IllegalArgumentException("Illegal game selection of " + name + '.');
        }
    }

    /**
     * Add user statistics (wins/losses) to this <code>GameTemplate</code>.
     *
     * @param winnerUsername the username of the winning player
     */
    protected void addUserStats(String winnerUsername) {
        for (String u : this.usernames) {
            if (winnerUsername.equals(u)) {
                try {
                    this.userManager.addUserStatistics(u, Set.of("gamesPlayed", "gamesWon"));
                } catch (UserManager.UserNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    this.userManager.addUserStatistics(u, Set.of("gamesPlayed"));
                } catch (UserManager.UserNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Add user statistics (ties) to this <code>GameTemplate</code>.
     *
     * @param tiedPlayers the usernames of the players that tied
     */
    protected void addUserStats(List<String> tiedPlayers) {
        for (String u : this.usernames) {
            if (tiedPlayers.contains(u)) {
                try {
                    this.userManager.addUserStatistics(u, Set.of("gamesPlayed", "gamesTied"));
                } catch (UserManager.UserNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    this.userManager.addUserStatistics(u, Set.of("gamesPlayed"));
                } catch (UserManager.UserNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Run a given game.
     * <p>
     * This is likely the entry point to the main loop of this game.
     */
    public abstract void startGame();

    /**
     * Input is an interface allowing Games to retrieve input from a user.
     */
    public interface Input {

        /**
         * Implementations should return a String corresponding to a picked card.
         * <p>
         * The first character of the String should be the suit (one of 'C', 'D', 'H', 'S'), while the
         * second and possibly third characters should be
         * the rank (one of "A", "2", "3", "4", "5", "6", "7", "8', "9", "10", "J", "Q", "K").
         *
         * @return a 2-3 character String representing the picked card.
         */
        String getCard();

        /**
         * Implementations should return a boolean representing whether a card should be drawn from the deck.
         *
         * @return true if a card should be drawn, false otherwise
         */
        boolean drawCard();

        /**
         * Implementations should return a character corresponding to a picked suit.
         * <p>
         * The character must be one of {'C', 'D', 'H', 'S'} representing Clubs, Diamonds, Hearts, Spades respectively.
         */
        char getSuit();

        /**
         * Implementations should return a string corresponding to a picked rank.
         * The string must be one of {"A", "2", "3", "4", "5", "6", "7", "8', "9", "10", "J", "Q", "K"}.
         */
        String getRank();

        /**
         * Implementation should return a Player object corresponding to a picked player.
         *
         * @return a Player that is chosen by the user.
         */
        String getPlayerUsername(String currPlayerUsername, List<String> usernames);

        /**
         * Implementations should stall the output display. This can be used when the user needs to "click to continue"
         * or in the case of a command line interface, "press enter to continue".
         */
        boolean stall();

    }


    /**
     * Output is an interface allowing Games to output back to the user.
     */
    public interface Output {

        /**
         * Implementations should take the given Object s and handle it's output to the user.
         * How this is done depends on the implementation.
         *
         * @param s An Object that can be somehow outputted to the user.
         */
        void sendOutput(Object s);
    }
}