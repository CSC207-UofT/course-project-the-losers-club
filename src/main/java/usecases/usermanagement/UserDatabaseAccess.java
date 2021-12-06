package usecases.usermanagement;

import java.io.Closeable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Gives methods that allow objects interfacing to interact with the user database. Allows objects that implement to
 * access and edit the users in the database, as well as changing statistics of users on the database.
 */
public interface UserDatabaseAccess extends AutoCloseable, Closeable {

    /**
     * Return a set of all usernames in this user database
     *
     * @return all the usernames stored
     */
    Set<String> getAllUsernames();

    /**
     * Add a user to this database.
     *
     * @param username the user's username
     * @return true if a new user was created (user did not previously exist), false otherwise
     */
    boolean addUser(String username);

    /**
     * Return whether a user with the given username exists.
     *
     * @param username check for the existence of this username
     * @return whether a user with the given username exists
     */
    boolean userExists(String username);

    /**
     * Remove a user from this database
     *
     * @param username the username of the user to remove
     * @throws UserNotFoundException if the user to remove was not found
     */
    void removeUser(String username) throws UserNotFoundException;

    /**
     * Set the specified statistics to the given amount for the given user.
     * <p>
     * Implementations must support the following statistics:
     * <ul>
     *     <li>gamesPlayed</li>
     *     <li>gamesWon</li>
     *     <li>gamesTied</li>
     * </ul>
     *
     * @param username   the username of the user to set statistics for
     * @param statistics the new values of the statistics. Should be a mapping of <code>{statistic: new value}</code>.
     * @throws UserNotFoundException if the user to set statistics for was not found
     */
    void setUserStatistics(String username, Map<String, Integer> statistics) throws UserNotFoundException;

    /**
     * Increment the specified statistics by the given amount for the given user.
     * <p>
     * Implementations must support the following statistics:
     * <ul>
     *     <li>gamesPlayed</li>
     *     <li>gamesWon</li>
     *     <li>gamesTied</li>
     * </ul>
     *
     * @param username   the username of the user to add statistics to
     * @param statistics the statistics to add. Should be a mapping of <code>{statistic: increment}</code>.
     * @throws UserNotFoundException if the user to add statistics to was not found
     */
    void addUserStatistics(String username, Map<String, Integer> statistics) throws UserNotFoundException;

    /**
     * Increment the specified statistics by 1 for the given user.
     * <p>
     * Implementations must support the following statistics:
     * <ul>
     *     <li>gamesPlayed</li>
     *     <li>gamesWon</li>
     *     <li>gamesTied</li>
     * </ul>
     *
     * @param username   the username of the user to add statistics to
     * @param statistics the statistics to add. Should be a mapping of <code>{statistic: increment}</code>.
     * @throws UserNotFoundException if the user to add statistics to was not found
     */
    void addUserStatistics(String username, Collection<String> statistics) throws UserNotFoundException;

    /**
     * Return the selected statistics for the given user.
     * <p>
     * Implementations must support the following statistics:
     * <ul>
     *     <li>gamesPlayed</li>
     *     <li>gamesWon</li>
     *     <li>gamesTied</li>
     * </ul>
     *
     * @param username   the username of the user to retrieve statistics for
     * @param statistics the statistics to retrieve
     * @return a mapping of statistic <code>{name: value}</code>.
     * @throws UserNotFoundException if the user to add statistics to was not found
     */
    HashMap<String, Integer> getUserStatistics(String username, Collection<String> statistics) throws UserNotFoundException;

    /**
     * Return all the statistics for the given user.
     *
     * @param username the username of the user to retrieve statistics for
     * @return a mapping of statistic <code>{name: value}</code>.
     * @throws UserNotFoundException if the user to add statistics to was not found
     */
    HashMap<String, Integer> getUserStatistics(String username) throws UserNotFoundException;

    /**
     * An exception representing the event where a given username is not found.
     */
    class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    /**
     * An exception representing the event where a provided statistic is invalid.
     */
    class InvalidStatisticError extends Error {
        public InvalidStatisticError(String statistic) {
            super("Statistic of: " + statistic + ", is invalid.");
        }
    }

}
