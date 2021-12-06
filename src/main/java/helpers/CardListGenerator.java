package helpers;

import entities.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for generating lists of cards with differing sizes for the purposes of creating <code>Deck</code>s.
 */
public class CardListGenerator {

    private static final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static final char[] SUITS = {'H', 'S', 'D', 'C'};

    /**
     * Get a list of cards based on the selected subset.
     * <p>
     * Valid subsets:
     * <ul>
     *     <li>"BOTTOM HALF": the bottom half of the ranks (A, 2, 3, 4, 5, 6, 7) [28 cards]</li>
     *     <li>"TOP HALF": the top half of the ranks (7, 8, 9, 10, J, Q, K) [28 cards]</li>
     *     <li>"ALL": the entire deck (all ranks) [52 cards]</li>
     *     <li>"BURA: the specialized Bura deck</li>
     * </ul>
     *
     * @param subset the selected subset. See method description for valid subsets.
     * @return the requested subset of <code>Card</code>s.
     */
    public static List<Card> getCards(String subset) {
        switch (subset.toUpperCase()) {
            case "ALL":
                return getSubset(RANKS, SUITS);
            case "TOP HALF":
                return getSubset(new String[]{"A", "2", "3", "4", "5", "6", "7"}, SUITS);
            case "BOTTOM HALF":
                return getSubset(new String[]{"7", "8", "9", "10", "J", "Q", "K"}, SUITS);
            case "BURA":
                return getSubset(new String[]{"A", "6", "7", "8", "9", "10", "J", "Q", "K"}, SUITS);
            default:
                throw new IllegalArgumentException("Subset " + subset + " is invalid.");
        }
    }

    /**
     * Get the requested subset of cards.
     *
     * @param ranks ranks to generate
     * @param suits suits to generate
     * @return a list of <code>ranks.length * suits.length</code> cards.
     */
    protected static List<Card> getSubset(String[] ranks, char[] suits) {
        List<Card> cardList = new ArrayList<>();
        for (String r : ranks) {
            for (char s : suits) {
                cardList.add(new Card(r, s));
            }
        }
        return cardList;
    }
}
