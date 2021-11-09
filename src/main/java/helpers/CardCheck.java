package helpers;

import java.util.regex.Pattern;

/**
 * CardCheck defines methods for checking the validity of a card String.
 *
 * This done by compiling a regular expression using the built-in Pattern class.
 *
 * The string representations of cards are then considered valid if they are an element of the produced language.
 */
public class CardCheck {

    //Produces a Pattern to check card strings against. If they follow the Pattern, it is a valid card.
    private static final Pattern CARD_PATTERN = Pattern.compile("(?i)^(10|[2-9]|[ajqk])([cdhs])$");
    /**
     * A method that checks if a given string s is a valid representation of a card as a string.
     * @param s - The string that is being checked as a valid representation of a card as a string.
     * @return True if the string is a valid representation of a card as a string, false otherwise.
     */
    public static boolean checkCard(String s) {
        //Creates a Matcher object and then asks if the given string s matches the CARD_PATTERN
        return CARD_PATTERN.matcher(s).matches();
    }
}