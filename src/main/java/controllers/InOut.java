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

    void sendOutput(Object s);

}
