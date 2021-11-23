package userdata;

import java.io.Closeable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SQLiteUserDatabase implements UserManager, AutoCloseable, Closeable {


    /**
     * Instantiate a new SQLiteUserDatabase. It is encouraged to instantiate this class using
     * a <code>try</code>-with-resources block.
     *
     * @param filePath filePath to the SQLite database
     */
    public SQLiteUserDatabase(String filePath) {
    }

    /**
     * Check whether a column string is valid and return the table's column name.
     *
     * @param col column string to check the validity of
     * @return the user table's column name
     */
    private static String getActualColName(String col) {
        return "";
    }

    /**
     * Create the required tables for this SQLiteUserDatabase.
     */
    private void createTables() {
    }

    /**
     * Add a user to this database.
     *
     * @param username the user's username
     * @return true if a new user was created (user did not previously exist), false otherwise
     */
    @Override
    public boolean addUser(String username) {
        return false;
    }

    /**
     * Return whether a user with the given username exists.
     *
     * @param username check for the existence of this username
     * @return whether a user with the given username exists
     */
    @Override
    public boolean userExists(String username) {
        return false;
    }

    /**
     * Remove a user from this database
     *
     * @param username the username of the user to remove
     * @throws UserNotFoundException if the user to remove was not found
     */
    @Override
    public void removeUser(String username) throws UserNotFoundException {
    }

    /**
     * Set the specified statistics to the given amount for the given user.
     *
     * @param username   the username of the user to set statistics for
     * @param statistics the new values of the statistics. Should be a mapping of <code>{statistic: new value}</code>.
     * @throws UserNotFoundException if the user to set statistics for was not found
     */
    @Override
    public void setUserStatistics(String username, Map<String, Integer> statistics) throws UserNotFoundException {
    }

    /**
     * Increment the specified statistics by the given amount for the given user.
     *
     * @param username   the username of the user to add statistics to
     * @param statistics the statistics to add. Should be a mapping of <code>{statistic: increment}</code>.
     * @throws UserNotFoundException if the user to add statistics to was not found
     */
    @Override
    public void addUserStatistics(String username, Map<String, Integer> statistics) throws UserNotFoundException {
    }

    /**
     * Increment the specified statistics by 1 for the given user.
     *
     * @param username   the username of the user to add statistics to
     * @param statistics the statistics to add. Should be a mapping of <code>{statistic: increment}</code>.
     * @throws UserNotFoundException if the user to add statistics to was not found
     */
    @Override
    public void addUserStatistics(String username, Collection<String> statistics) throws UserNotFoundException {
    }

    /**
     * Return the selected statistics for the given user.
     *
     * @param username   the username of the user to retrieve statistics for
     * @param statistics the statistics to retrieve
     * @return a mapping of statistic <code>{name: value}</code>.
     * @throws UserNotFoundException if the user to add statistics to was not found
     */
    @Override
    public HashMap<String, Integer> getUserStatistics(String username, Collection<String> statistics) throws UserNotFoundException {
        return new HashMap<>();
    }

    /**
     * Return all the statistics for the given user.
     *
     * @param username the username of the user to retrieve statistics for
     * @return a mapping of statistic <code>{name: value}</code>.
     * @throws UserNotFoundException if the user to add statistics to was not found
     */
    @Override
    public HashMap<String, Integer> getUserStatistics(String username) throws UserNotFoundException {
        return new HashMap<>();
    }

    /**
     * Rollback any active transactions and close this SQLite database connection.
     */
    @Override
    public void close() {
    }

    /**
     * An error thrown when an unexpected SQL exception is raised by one of the methods in this class.
     */
    static class UnexpectedSQLExceptionError extends Error {
        public UnexpectedSQLExceptionError(String message) {
            super(message);
        }
    }

    /**
     * An error thrown when there is a problem connecting with the SQLite database.
     */
    static class DatabaseConnectionError extends Error {
        public DatabaseConnectionError(String message) {
            super(message);
        }
    }
}
