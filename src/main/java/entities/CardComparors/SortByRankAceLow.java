package entities.CardComparors;

import entities.Card;

import java.util.Comparator;

/**
 * This class implements Comparator for the Card class. It compares the cards based on their rank with ace
 * being the lowest card.
 */
public class SortByRankAceLow implements Comparator<Card> {
    /**
     * Converts the value of the String into a number to be used in the compare method
     * @param rank The rank of the card as a string
     * @return returns an int value for each rank
     */
    private int rankToInt(String rank){
        switch(rank){
            case "j": return 11;
            case "q": return 12;
            case "k": return 13;
            case "a": return 1;
            default: return Integer.parseInt(rank);
        }
    }

    @Override
    public int compare(Card o1, Card o2) {
        return rankToInt(o1.getRank()) - rankToInt(o2.getRank());
    }
}