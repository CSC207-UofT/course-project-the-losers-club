package presenters.console;

import helpers.CardCheck;
import usecases.Game;

import java.util.Scanner;


/**
 * Input uses System.in to gather input from the user.
 *
 * It is intended to be used as a basic command line interface.
 *
 * @see Game.Input
 */
public class Input implements Game.Input {

    private final Scanner inp;

    /**
     * Instantiate a new Input instance.
     */
    public Input() {
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
        System.out.print("Enter the rank of your desired card followed by the suit (e.g. \"10s\" for the 10 of Spades): ");
        String line = this.inp.nextLine().trim();

        // if invalid card
        while (!CardCheck.checkCard(line)) {
            System.out.print("Invalid selection: ");
            line = this.inp.nextLine().trim();
        }

        line = line.toUpperCase();
        return line.charAt(line.length() - 1) + line.substring(0, line.length() - 1);
    }

    /**
     * Prompt user to indicate whether they wish to draw a card from the deck.
     *
     * @return a boolean representing whether the user wishes to draw a card from the deck
     */
    @Override
    public boolean drawCard() {
        System.out.print("Do you want to draw a card (\"Y\", \"N\"): ");
        String line = this.inp.nextLine().trim().toUpperCase();

        // if invalid card selection
        while (line.length() != 1 || (!line.equals("Y") && !line.equals("N"))) {
            System.out.print("Invalid selection (\"Y\", \"N\"): ");
            line = this.inp.nextLine().trim().toUpperCase();
        }

        return line.equals("Y");
    }

}
