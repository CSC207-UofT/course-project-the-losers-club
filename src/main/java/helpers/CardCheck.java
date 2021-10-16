package helpers;

import java.util.regex.Pattern;

/**
 * CardCheck defines methods for checking the validity of a card String.
 */
public class CardCheck {

    private static final Pattern CARD_PATTERN = Pattern.compile("(?i)^(10|\\d|[ajqk])([cdhs])$");

    public static boolean checkCard(String s) {
        return CARD_PATTERN.matcher(s).matches();
    }
}
