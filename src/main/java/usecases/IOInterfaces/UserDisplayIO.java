package usecases.IOInterfaces;

import java.util.Map;

/**
 * This class defines an interface for the user statistics display. Any class that implements this interface should be
 * able to be seamlessly swapped out in the crazy eights game code, and it should work perfectly.
 */
public interface UserDisplayIO {

    /**
     * Prompt the user for a username to get the statistics for.
     *
     * @return a valid username to retrieve various gameplay statistics for
     */
    String getUsername();

    /**
     * Display the specified statistics.
     * <p>
     * Statistics to support (keys in the Map):
     * <ul>
     *     <li>"Games Played"</li>
     *     <li>"Games Won"</li>
     *     <li>"Games Tied"</li>
     *     <li>"Win Percentage"</li>
     * </ul>
     *
     * @param statistics mapping of statistic names to their values. See description for supported statistics.
     */
    void showStats(Map<String, String> statistics);
}
