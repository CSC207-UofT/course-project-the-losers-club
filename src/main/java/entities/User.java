package entities;


import java.io.Serializable;

public class User implements Serializable {

    private final String username;
    private int gamesWon;
    private int gamesPlayed;
    private int gamesTied;

    /**
     * Constructs a User with the given username.
     *
     * @param username The username of the User displayed by the game
     */
    public User(String username) {
        this.username = username;
        this.gamesWon = 0;
        this.gamesPlayed = 0;
        this.gamesTied = 0;
    }

    /**
     * Constructs a User with the given username and statistics.
     *
     * @param username    The username of the User displayed by the game
     * @param gamesWon    number of games won
     * @param gamesPlayed number of games played
     * @param gamesTied   number of games tied
     */
    public User(String username, int gamesPlayed, int gamesWon, int gamesTied) {
        this.username = username;
        this.gamesWon = gamesWon;
        this.gamesPlayed = gamesPlayed;
        this.gamesTied = gamesTied;
    }

    /**
     * Returns this User's username
     *
     * @return the username of the User
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Add a win to the User's statistics
     *
     * @return the current number of wins of the User
     */
    public int addWin() {
        this.gamesWon += 1;
        return this.gamesWon;
    }

    /**
     * Add a game played to the User's statistics
     *
     * @return the current number of games played of the User
     */
    public int addPlayed() {
        this.gamesPlayed += 1;
        return this.gamesPlayed;
    }

    /**
     * Add a tie to the User's statistics
     *
     * @return the current number of ties of the User
     */
    public int addTied() {
        this.gamesTied += 1;
        return this.gamesTied;
    }

    /**
     * Returns this User's games won
     *
     * @return number of games won by the User
     */
    public int getGamesWon() {
        return this.gamesWon;
    }

    /**
     * Return this User's games played
     *
     * @return number of games played by the User
     */
    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    /**
     * Returns this User's games tied
     *
     * @return number of ties of the User
     */
    public int getGamesTied() {
        return this.gamesTied;
    }
}