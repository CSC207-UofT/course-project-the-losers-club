package usecases.IOInterfaces;

public interface CrazyEightsIO {

    /**
     * This method changes the current user to <>username</> and then informers the user of this change.
     */
    public void changePlayer(String username);

    /**
     * This method should display the new top card to the user.
     * @param card a string representation of the card to be displayed
     */
    public void changeTopCard(String card);
}
