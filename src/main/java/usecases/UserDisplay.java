package usecases;

import usecases.usermanagement.UserDatabaseAccess;
import usecases.usermanagement.UserManager;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * UserDisplay is a class that displays the stats for a given user
 */
public class UserDisplay {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final UserDisplay.Input displayInput;
    private final UserDisplay.Output displayOutput;
    private final UserManager userDatabaseAccess;

    public UserDisplay(UserManager userDatabaseAccess, UserDisplay.Input displayInput, UserDisplay.Output displayOutput) {
        this.userDatabaseAccess = userDatabaseAccess;
        this.displayInput = displayInput;
        this.displayOutput = displayOutput;
    }

    /**
     * Run this <code>UserDisplay</code> to display user statistics.
     */
    public void run() {
        this.displayOutput.sendOutput("\n\n\n\n\n");
        this.displayOutput.sendOutput("Check your stats by entering your username.\n");
        this.displayOutput.sendOutput("Enter exit to return to the game selection menu.\n");

        String username = this.displayInput.getUsername();

        while (!username.equalsIgnoreCase("exit")) {
            int gamesPlayed = 0;
            int wins;
            int ties;

            try {
                gamesPlayed = this.userDatabaseAccess.getGamesPlayed(username);
                wins = this.userDatabaseAccess.getWins(username);
                ties = this.userDatabaseAccess.getGamesTied(username);
            } catch (UserManager.UserNotFoundException e) {
                this.displayOutput.sendOutput("The user was not found. Please enter a existing user.\n");
                username = this.displayInput.getUsername();
                continue;
            }

            double wpct;
            if (gamesPlayed == 0) {
                wpct = 0.00;
            } else {
                wpct = ((double) wins / (double) gamesPlayed) * 100.0;
            }

            this.displayOutput.sendOutput("\n\n\n\n\n");
            this.displayOutput.sendOutput(username + "'s Stats\n");
            this.displayOutput.sendOutput("Games Played: " + gamesPlayed + "\n");
            this.displayOutput.sendOutput("Games Won: " + wins + "\n");
            this.displayOutput.sendOutput("Games Tied: " + ties + "\n");
            this.displayOutput.sendOutput("Win Percentage: " + df.format(wpct) + "%\n");

            username = this.displayInput.getUsername();

        }
        this.displayOutput.sendOutput("\n\n\n\n\n");

    }

    /**
     * Input is an interface allowing this controller to retrieve input from a user.
     */
    public interface Input {

        String getUsername();

    }

    /**
     * Output is an interface allowing this controller to output back to the user.
     */
    public interface Output {

        void sendOutput(Object o);

    }
}
