package usecases.usermanagement;

import entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class that stores a hashmap mapping usernames to a User object. This class can add and keep track of users, and can
 * communicate with the user database to import stored users and write and take away new users to and from the database.
 * This class is also able to return the statistics of specific users by referencing their usernames.
 */
public class UserManager {

    private final Map<String, User> users;

    /**
     * Constructs a UserManager with an empty hashmap of users
     */
    public UserManager() {
        this.users = new HashMap<>();
    }

    /**
     * Constructs a UserManager with a pre-existing hashmap of username to User
     *
     * @param users hashmap that maps a username to an instance of User
     */
    public UserManager(Map<String, User> users) {
        this.users = users;
    }

    /**
     * Import users from the user database and construct a UserManager.
     *
     * @param databaseAccessor user database gateway
     * @return constructed <code>UserManager</code> from the provided database accessor
     */
    public static UserManager importFromUserDatabase(UserDatabaseAccess databaseAccessor) {
        Set<String> usernames = databaseAccessor.getAllUsernames();

        Map<String, User> users = new HashMap<>();
        for (String username : usernames) {
            Map<String, Integer> statistics;
            try {
                statistics = databaseAccessor.getUserStatistics(username);
            } catch (UserDatabaseAccess.UserNotFoundException e) {
                // should never happen, but if it does
                throw new AssertionError("User was removed from database between all username query and retrieving statistics for said user");
            }

            users.put(username, new User(username,
                    statistics.get("gamesPlayed"),
                    statistics.get("gamesWon"),
                    statistics.get("gamesTied"))
            );
        }

        return new UserManager(users);
    }

    /**
     * Export this <code>UserManager</code> to the database.
     *
     * @param databaseAccessor user database gateway
     */
    public void exportToUserDatabase(UserDatabaseAccess databaseAccessor) {
        for (Map.Entry<String, User> entry : this.users.entrySet()) {
            String username = entry.getKey();
            User user = entry.getValue();

            if (databaseAccessor.userExists(username)) {
                try {
                    databaseAccessor.setUserStatistics(username, Map.of(
                            "gamesPlayed", user.getGamesPlayed(),
                            "gamesWon", user.getGamesWon(),
                            "gamesTIed", user.getGamesTied()));
                } catch (UserDatabaseAccess.UserNotFoundException e) {
                    // should never reach here, but if it does
                    throw new AssertionError("User found to exist, but no longer does when setting statistics");
                }

            } else {
                databaseAccessor.addUser(username);
                try {
                    databaseAccessor.setUserStatistics(username, new HashMap<>(Map.of(
                            "gamesPlayed", user.getGamesPlayed(),
                            "gamesWon", user.getGamesWon(),
                            "gamesTied", user.getGamesTied())));
                } catch (UserDatabaseAccess.UserNotFoundException e) {
                    // should never reach here, but if it does
                    throw new AssertionError("User just created, but no longer exists when setting statistics");
                }
            }
        }
    }

    /**
     * Adds a user to the hashmap
     *
     * @param username username of the User
     * @return returns true if the user has been added and returns false when a user with a same username already exists
     */
    public boolean addUser(String username) throws UserAlreadyExistsException {
        if (hasUser(username)) {
            throw new UserAlreadyExistsException("Already Existing User: " + username);
        } else {
            User new_user = new User(username);
            users.put(username, new_user);
            return true;
        }
    }

    /**
     * Deletes a user from the hashmap
     *
     * @param username username of the user that needs to be deleted
     * @throws UserNotFoundException thrown when a user with a given username does not exist
     */
    public void deleteUser(String username) throws UserNotFoundException {
        if (hasUser(username)) {
            users.remove(username);
        } else {
            throw new UserNotFoundException("User Not Found: " + username);
        }
    }

    /**
     * Adds a game that has been played to a User's statistics
     *
     * @param username username for a User
     * @param result   1 if the User won, 0 if it was a tie, and -1 for a loss
     * @throws UserNotFoundException thrown when a user with a given username does not exist
     */
    public void addGamesPlayed(String username, int result) throws UserNotFoundException {
        if (hasUser(username)) {
            User user = users.get(username);
            user.addPlayed();
            if (result == 1) {
                user.addWin();
            } else if (result == 0) {
                user.addTied();
            }
        } else {
            throw new UserNotFoundException("User Not Found: " + username);
        }
    }

    /**
     * Returns the number of wins for a given User
     *
     * @param username username of the User
     * @return the number of wins for a given User
     * @throws UserNotFoundException thrown when a user with a given username does not exist
     */
    public int getWins(String username) throws UserNotFoundException {
        if (hasUser(username)) {
            return users.get(username).getGamesWon();
        } else {
            throw new UserNotFoundException("User Not Found: " + username);
        }
    }

    /**
     * Returns the number of games played for a given User
     *
     * @param username username of the User
     * @return the number of games played for a given User
     * @throws UserNotFoundException thrown when a user with a given username does not exist
     */
    public int getGamesPlayed(String username) throws UserNotFoundException {
        if (hasUser(username)) {
            return users.get(username).getGamesPlayed();
        } else {
            throw new UserNotFoundException("User Not Found: " + username);
        }
    }

    public int getGamesTied(String username) throws UserNotFoundException {
        if (hasUser(username)) {
            return users.get(username).getGamesTied();
        } else {
            throw new UserNotFoundException("User Not Found: " + username);
        }
    }

    /**
     * Checks if a User with a given username already exists
     *
     * @param username the username to check the existence of the user
     * @return true if the user exists, false if the user does not exist
     */
    public boolean hasUser(String username) {
        return users.containsKey(username);
    }

    /**
     * Checks if a User already exists, if not, creates and adds a User
     *
     * @param username username for a User
     * @return true if the User is already a user, false if a new User was created
     */
    public boolean login(String username) {
        if (hasUser(username)) {
            return true;
        } else {
            try {
                addUser(username);
            } catch (UserAlreadyExistsException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException(String errorMessage) {
            super(errorMessage);
        }
    }
}