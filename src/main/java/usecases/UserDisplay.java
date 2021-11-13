package usecases;

/**
 * UserDisplay is a class that displays the stats for a given user
 */
public class UserDisplay {

    private final UserDisplay.Input displayInput;
    private final UserDisplay.Output displayOutput;
    private final UserManager userManager;

    public UserDisplay(UserManager userManager, UserDisplay.Input displayInput, UserDisplay.Output displayOutput){
        this.userManager = userManager;
        this.displayInput = displayInput;
        this.displayOutput = displayOutput;
    }

    public void run(){
        this.displayOutput.sendOutput("\n\n\n\n\n");
        this.displayOutput.sendOutput("Check your stats by entering your username.\n");
        this.displayOutput.sendOutput("Enter exit to return to the game selection menu.\n");

        String usrname = this.displayInput.getUsername();

        while(!usrname.equalsIgnoreCase("exit")) {
            try {
                int gamesPlayed = this.userManager.getGamesPlayed(usrname);
                int wins = this.userManager.getWins(usrname);
                int ties = this.userManager.getGamesTied(usrname);
                double wpct = Math.round(((double) wins / (double) gamesPlayed) * 100);

                this.displayOutput.sendOutput("\n\n\n\n\n");
                this.displayOutput.sendOutput(usrname + "'s Stats\n");
                this.displayOutput.sendOutput("Games Played: " + gamesPlayed + "\n");
                this.displayOutput.sendOutput("Games Won: " + wins + "\n");
                this.displayOutput.sendOutput("Games Tied: " + ties + "\n");
                this.displayOutput.sendOutput("Win Percentage: " + wpct + "%\n");

                usrname = this.displayInput.getUsername();
            } catch (UserManager.UserNotFoundException e) {
                this.displayOutput.sendOutput("The user was not found. Please enter a existing user.\n");
                usrname = this.displayInput.getUsername();
            }
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