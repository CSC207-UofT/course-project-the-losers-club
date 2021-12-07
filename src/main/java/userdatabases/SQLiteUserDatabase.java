package userdatabases;

import usecases.usermanagement.UserDatabaseAccess;

import java.io.Closeable;
import java.io.File;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;

/**
 * Class which interacts with the SQLite database containing user information. Interacts with the tables contained in
 * the actual database, and is able to update statistics of specific users.
 */
public class SQLiteUserDatabase implements UserDatabaseAccess, AutoCloseable, Closeable {

    private static final Set<String> STATISTICS_COLUMNS = Set.of("gamesPlayed", "gamesWon", "gamesTied");
    private final Connection CONN;

    /**
     * Instantiate a new SQLiteUserDatabase. It is encouraged to instantiate this class using
     * a <code>try</code>-with-resources block.
     *
     * @param filePath filePath to the SQLite database
     */
    public SQLiteUserDatabase(String filePath) {
        Path p = Path.of(filePath);
        p = p.toAbsolutePath();

        File parent = p.getParent().toFile();
        boolean madeParent = parent.mkdirs();

        String connectionString = "jdbc:sqlite:" + new File(filePath).getAbsolutePath();
        try {
            this.CONN = DriverManager.getConnection(connectionString);
            this.CONN.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseConnectionError("Failed to connect to SQLite database");
        }

        this.createTables();
    }

    /**
     * Check whether a column string is valid and return the table's column name.
     *
     * @param col column string to check the validity of
     * @return the user table's column name
     */
    private static String getActualColName(String col) {
        switch (col.toLowerCase()) {
            case "gamesplayed":
                return "gamesPlayed";
            case "gameswon":
                return "gamesWon";
            case "gamestied":
                return "gamesTied";
            default:
                throw new InvalidStatisticError(col);
        }
    }

    /**
     * Create the required tables for this SQLiteUserDatabase.
     */
    private void createTables() {
        String createString = "CREATE TABLE IF NOT EXISTS users(" +
                "username TEXT PRIMARY KEY ASC ON CONFLICT ABORT UNIQUE COLLATE NOCASE," +
                "gamesPlayed INTEGER DEFAULT 0," +
                "gamesWon INTEGER DEFAULT 0," +
                "gamesTied INTEGER DEFAULT 0" +
                ")";
        try (Statement stmt = this.CONN.createStatement()) {
            stmt.executeUpdate(createString);
            this.CONN.commit();
        } catch (SQLException e) {
            throw new DatabaseConnectionError("Error creating tables for this SQLiteUserDatabase.");
        }
    }

    /**
     * Return a set of all usernames in this user database
     *
     * @return all the usernames stored
     */
    @Override
    public Set<String> getAllUsernames() {
        Set<String> set = new HashSet<>();

        String query = "SELECT username FROM users";
        try (PreparedStatement stmt = this.CONN.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                set.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new UnexpectedSQLExceptionError("Could not retrieve all usernames: " + e.getMessage());
        }

        return set;
    }

    /**
     * Add a user to this database.
     *
     * @param username the user's username
     * @return true if a new user was created (user did not previously exist), false otherwise
     */
    @Override
    public boolean addUser(String username) {
        String insertString = "INSERT INTO users (username) VALUES(?)";
        try (PreparedStatement stmt = this.CONN.prepareStatement(insertString)) {
            stmt.setString(1, username);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated != 1) {
                this.CONN.rollback();
                return false;
            } else {
                this.CONN.commit();
                return true;
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 19) { // SQLITE_CONSTRAINT
                return false;
            }
            throw new UnexpectedSQLExceptionError("User could not be added to the database: " + e.getMessage());
        }
    }

    /**
     * Return whether a user with the given username exists.
     *
     * @param username check for the existence of this username
     * @return whether a user with the given username exists
     */
    @Override
    public boolean userExists(String username) {
        String query = "SELECT * FROM users WHERE username LIKE ?";
        try (PreparedStatement stmt = this.CONN.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new UnexpectedSQLExceptionError("Could not check if username exists: " + e.getMessage());
        }
    }

    /**
     * Remove a user from this database
     *
     * @param username the username of the user to remove
     * @throws UserNotFoundException if the user to remove was not found
     */
    @Override
    public void removeUser(String username) throws UserNotFoundException {
        String removeStr = "DELETE FROM users WHERE username LIKE ?";
        try (PreparedStatement stmt = this.CONN.prepareStatement(removeStr)) {
            stmt.setString(1, username);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new UserNotFoundException("User with username: " + username + ", was not found.");
            } else {
                this.CONN.commit();
            }
        } catch (SQLException e) {
            throw new UnexpectedSQLExceptionError("Could not remove user: " + e.getMessage());
        }
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
        if (statistics.isEmpty()) {
            return;
        }

        String update1 = "UPDATE OR ABORT users SET ";
        String update2 = " = ? WHERE username LIKE ?";
        for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
            try (PreparedStatement stmt = this.CONN.prepareStatement(update1 + getActualColName(entry.getKey()) + update2)) {
                stmt.setInt(1, entry.getValue());
                stmt.setString(2, username);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new UserNotFoundException("User of: " + username + ", not found.");
                }
                this.CONN.commit();
            } catch (SQLException e) {
                throw new UnexpectedSQLExceptionError("Could not set user statistics: " + e.getMessage());
            }
        }
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
        HashMap<String, Integer> origStatistics = this.getUserStatistics(username, statistics.keySet());
        origStatistics.replaceAll((k, v) -> v + statistics.get(k));

        this.setUserStatistics(username, origStatistics);
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
        HashMap<String, Integer> map = new HashMap<>();

        for (String statistic : statistics) {
            map.put(statistic, 1); // default increment by 1
        }

        this.addUserStatistics(username, map);
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
        HashMap<String, Integer> map = new HashMap<>();

        String query1 = "SELECT ";
        String query2 = " FROM users WHERE username LIKE ?";
        for (String statistic : statistics) {
            try (PreparedStatement stmt = this.CONN.prepareStatement(query1 + getActualColName(statistic) + query2)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    map.put(statistic, rs.getInt(statistic));
                } else {
                    throw new UserNotFoundException("User of: " + username + ", not found.");
                }
            } catch (SQLException e) {
                throw new UnexpectedSQLExceptionError("Could not retrieve statistics for the given user: " + e.getMessage());
            }
        }

        return map;
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
        return this.getUserStatistics(username, STATISTICS_COLUMNS);
    }

    /**
     * Rollback any active transactions and close this SQLite database connection.
     */
    @Override
    public void close() {
        try {
            this.CONN.rollback();
            this.CONN.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionError("Failed to close SQLite database connection");
        }
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
