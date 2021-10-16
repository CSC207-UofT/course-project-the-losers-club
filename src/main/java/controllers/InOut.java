package controllers;

public interface InOut {

    /**
     * Implementations should return a String corresponding to a picked card.
     *
     * The first character of the String should be the suit (one of 'C', 'D', 'H', 'S'), while the
     * second and possibly third characters should be
     * the rank (one of "A", "2", "3", "4", "5", "6", "7", "8', "9", "10", "J", "Q", "K").
     *
     * @return a 2-3 character String representing the picked card.
     */
    String getCard();

    /**
     * Implementations should return a boolean representing whether a card should be drawn from the deck.
     *
     * @return true if a card should be drawn, false otherwise
     */
    boolean drawCard();


    /**
     * Implementations should take the given Object s and handle it's output to the user.
     * How this is done depends on the implementation.
     *
     * @param s An Object that can be somehow outputted to the user.
     */
    void sendOutput(Object s);

}