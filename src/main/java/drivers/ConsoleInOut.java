package drivers;

import controllers.InOut;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class ConsoleInOut implements InOut {

    private final Scanner inp;
    private static final HashSet<Character> SUITS = new HashSet<>(Arrays.asList('C', 'D', 'H', 'S'));
    private static final HashSet<String> RANKS = new HashSet<>(Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"));

    public ConsoleInOut() {
        this.inp = new Scanner(System.in);
    }

    @Override
    public String getCard() {
        String card = "";

        card += this.getSuit().toUpperCase();
        card += this.getRank().toUpperCase();

        return card;
    }

    private String getSuit() {
        String line;
        do {
            System.out.print("Enter the suit of your card (\"C\", \"D\", \"H\", \"S\"): " );
            line = this.inp.nextLine();
            System.out.println();
        } while (line.length() != 1 && !ConsoleInOut.SUITS.contains(line.charAt(0)));

        return line;
    }

    private String getRank() {
        String line;
        do {
            System.out.print("Enter the rank of your card (\"A\", \"2\", \"3\", ..., \"10\", \"J\", \"Q\", \"K\"):" );
            line = this.inp.nextLine();
            System.out.println();
        } while (line.length() != 1 && line.length() != 2 && !ConsoleInOut.RANKS.contains(line));

        return line;
    }

    @Override
    public void sendOutput(Object s) {
        System.out.println(s.toString());
    }

}
