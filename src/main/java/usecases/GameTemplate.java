package usecases;

import entities.Card;
import entities.Deck;
import usecases.IOInterfaces.*;
import usecases.usermanagement.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class on which games are built. Contains methods that all games will need to use in their implementation
 * and interacts with the stats of the Users who play the game.
 */
public abstract class GameTemplate {

    protected static final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    protected static final char[] SUITS = {'H', 'S', 'D', 'C'};
    protected Player[] players;
    protected Deck deck;
    protected Player currPlayer;
    protected UserManager userManager;
    protected List<String> usernames;
    protected GameIO gameIO;
    protected int currPlayerIndex;

    /**
     * Construct a <code>GameTemplate</code>.
     *
     * @param usernames   usernames of those playing the game
     * @param userManager manager for storing user information
     */
    protected GameTemplate(List<String> usernames, UserManager userManager, GameIO gameIO) {
        this.userManager = userManager;
        this.usernames = usernames;

        this.gameIO = gameIO;

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
     * @param userManager user management vessel
     * @return the requested game instance
     */
    public static GameTemplate gameFactory(String name, List<String> usernames, UserManager userManager, GameIO gameIO) {
        switch (name.toUpperCase()) {
            case "BURA":
                return new Bura(usernames, userManager, (BuraIO) gameIO);
            case "CRAZY EIGHTS":
                return new CrazyEights(usernames, userManager, (CrazyEightsIO) gameIO);
            case "WAR":
                return new War(usernames, userManager, (WarIO) gameIO);
            case "GO FISH":
                return new GoFish(usernames, userManager, (GoFishIO) gameIO);
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
            case "BURA":
                return Bura.getMaxPlayers();
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
            case "BURA":
                return Bura.getMinPlayers();
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
                    this.userManager.addGamesPlayed(u, 1);
                } catch (UserManager.UserNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    this.userManager.addGamesPlayed(u, -1);
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
                    this.userManager.addGamesPlayed(u, 0);
                } catch (UserManager.UserNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    this.userManager.addGamesPlayed(u, -1);
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
     * <code>AbortGameException</code> should be thrown when a game is forcibly terminated.
     */
    public static class AbortGameException extends Exception {

    }
}