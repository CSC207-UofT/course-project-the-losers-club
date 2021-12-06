package controllers;

import java.util.List;

/**
 * This class defines an interface for the user statistics display. Any class that implements this interface should be
 * able to be seamlessly swapped out in the user display code, and it should work perfectly.
 */
public interface UserDisplayIO {

    /**
     * Retrieve a username to get the statistics for.
     *
     * @return a valid username to retrieve various gameplay statistics for
     */
    String getUsername();

    /**
     * Show a popup telling the user that they have entered an invalid username.
     *
     * @param username the invalid username
     */
    void invalidUsername(String username);

    /**
     * Display the specified statistics.
     * <p>
     * Statistics to support (first elements in the arrays):
     * <ul>
     *     <li>"Games Played"</li>
     *     <li>"Games Won"</li>
     *     <li>"Games Tied"</li>
     *     <li>"Win Percentage"</li>
     * </ul>
     *
     * @param statistics list containing arrays of statistic names to their values.
     *                   The first element must be the statistic name, and the second element must be the statistic value.
     *                   See description for supported statistics.
     */
    void showStats(List<String[]> statistics);

    /**
     * This method should close the GUI when called.
     */
    void close();
}
