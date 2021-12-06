package controllers;

import usecases.usermanagement.UserManager;

import java.text.DecimalFormat;
import java.util.List;

/**
 * UserDisplay is a class that displays the stats for a given user
 */
public class UserDisplay {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final UserDisplayIO displayIO;
    private final UserManager userManager;

    /**
     * Instantiate a new <code>UserDisplay</code> to display user statistics.
     *
     * @param userManager user management object
     * @param displayIO   display object for presenting user statistics
     */
    public UserDisplay(UserManager userManager, UserDisplayIO displayIO) {
        this.userManager = userManager;
        this.displayIO = displayIO;
    }

    /**
     * Run this <code>UserDisplay</code> to display user statistics.
     */
    public void run() {
        String username = this.displayIO.getUsername();

        while (!username.equalsIgnoreCase("exit")) {
            int gamesPlayed;
            int wins;
            int ties;

            try {
                gamesPlayed = this.userManager.getGamesPlayed(username);
                wins = this.userManager.getWins(username);
                ties = this.userManager.getGamesTied(username);
            } catch (UserManager.UserNotFoundException e) {
                this.displayIO.invalidUsername("The user was not found. Please enter a existing user.\n");
                username = this.displayIO.getUsername();
                continue;
            }

            double wpct;
            if (gamesPlayed == 0) {
                wpct = 0.00;
            } else {
                wpct = ((double) wins / (double) gamesPlayed) * 100.0;
            }

            this.displayIO.showStats(List.of(new String[]{"Games Won", String.valueOf(wins)},
                    new String[]{"Games Tied", String.valueOf(ties)},
                    new String[]{"Games Played", String.valueOf(gamesPlayed)},
                    new String[]{"Win %", df.format(wpct)}));

            username = this.displayIO.getUsername();

        }
        this.displayIO.close();

    }
}
