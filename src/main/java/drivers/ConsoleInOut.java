package drivers;

import controllers.InOut;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


/**
 * ConsoleInOut uses System.in and System.out to gather input from the user and display information to the user.
 *
 * It is intended to be used as a basic command line interface.
 *
 * @see InOut
 */
public class ConsoleInOut implements InOut {

    private final Scanner inp;
    private static final HashSet<Character> SUITS = new HashSet<>(Arrays.asList('C', 'D', 'H', 'S'));
    private static final HashSet<String> RANKS = new HashSet<>(Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"));

    /**
     * Instantiate a new ConsoleInOut instance.
     */
    public ConsoleInOut() {
        this.inp = new Scanner(System.in);
    }

    /**
     * Prompt user to specify which card they wish to select.
     *
     * @return a String encoding the selected card. The first character of the String is always the suit
     *  of the selected card (one of 'C', 'D', 'H', 'S'), while the remaining characters are used to signify the
     *  rank of the selected card (one of "A", "2", "3", "4", "5", "6", "7", "8', "9", "10", "J", "Q", "K").
     */
    @Override
    public String getCard() {
        String card = "";

        String rank = this.getRank();
        card += this.getSuit();

        return card + rank;
    }

    /**
     * Prompt user to select a card suit.
     *
     * @return return an uppercase String representing the selected suit
     */
    private String getSuit() {
        String line;
        do {
            System.out.print("Enter the suit of your card (\"C\", \"D\", \"H\", \"S\"): " );
            line = this.inp.nextLine().trim().toUpperCase();
        } while (line.length() != 1 || !ConsoleInOut.SUITS.contains(line.charAt(0)));

        return line;
    }

    /**
     * Prompt user to select a card rank.
     *
     * @return return an uppercase String representing the selected rank
     */
    private String getRank() {
        String line;
        do {
            System.out.print("Enter the rank of your card (\"A\", \"2\", \"3\", ..., \"10\", \"J\", \"Q\", \"K\"): ");
            line = this.inp.nextLine().trim().toUpperCase();
        } while ((line.length() != 1 && line.length() != 2) || !ConsoleInOut.RANKS.contains(line));

        return line;
    }

    /**
     * Prompt user to indicate whether they wish to draw a card from the deck.
     *
     * @return a boolean representing whether the user wishes to draw a card from the deck
     */
    @Override
    public boolean drawCard() {
        String line;
        do {
            System.out.print("Do you want to draw a card (\"Y\", \"N\"): ");
            line = this.inp.nextLine().trim().toUpperCase();
        } while (line.length() != 1 || (!line.equals("Y") && !line.equals("N")));

        return line.equals("Y");
    }

    /**
     * Prints the specified object to the system console by calling the <code>s.toString()</code> method.
     *
     * @param s An Object that can be somehow outputted to the user.
     */
    @Override
    public void sendOutput(Object s) {
        System.out.println(s.toString());
    }

}
