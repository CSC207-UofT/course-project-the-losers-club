package entities;


public class User {

    private String name;
    private String username;
    private String password;
    private int gamesWon;
    private int gamesPlayed;

    /**
     * Constructs a User with the given name, username, and password.
     * @param name The name of the User
     * @param username The username of the User displayed by the game
     * @param password The password of the User
     */
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.gamesWon = 0;
        this.gamesPlayed = 0;
    }

    /**
     * Returns this User's name
     * @return the name of the User
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns this User's username
     * @return the username of the User
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Checks if the given password matches the User's password
     * @return true if password matches User's password, false otherwise
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Add a win to the User's statistics
     * @return the current number of wins of the User
     */
    public int addWin() {
        this.gamesWon += 1;
        return this.gamesWon;
    }

    /**
     * Add a game played to the User's statistics
     * @return the current number of games played of the User
     */
    public int addPlayed() {
        this.gamesPlayed += 1;
        return this.gamesPlayed;
    }

    /**
     * Returns this User's games won
     * @return number of games won by the User
     */
    public int getGamesWon() { return this.gamesWon; }

    /**
     * Return this User's games played
     * @return number of games played by the User
     */
    public int getGamesPlayed() { return this.gamesPlayed; }
}
