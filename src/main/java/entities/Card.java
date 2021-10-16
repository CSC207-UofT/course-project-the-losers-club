package entities;

/**
 * Represents a card in a standard 52-card playing deck. Each card has a rank and suit corresponding to the
 * possible rank and suit combination in a standard 52-card playing deck.
 */
public class Card {
    // Instances attributes
    private final String rank;
    private final char suit;
    private boolean isFace;

    /**
     * Constructs a card with its given rank, suit, and checks if it's a face card or not
     *
     * @param rank the rank of the card
     * @param suit the suit of the card
     */
    public Card(String rank, char suit){
        this.rank = rank;
        this.suit = suit;
        this.isFace = false;

        // Checks if the card is a face card
        String[] faces = new String[]{"K", "Q", "J"};
        for (String element : faces){
            if (element.equals(this.rank)){
                this.isFace = true;
                break;
            }
        }
    }
    /**
     * Returns the rank of the card
     *
     * @return the rank of the card
     */
    public String getRank(){
        return this.rank;
    }

    /**
     * Returns the suit of the card
     *
     * @return the suit of the card
     */
    public char getSuit(){ return this.suit; }

    /**
     * Returns whether the card is a face card
     *
     * @return a boolean representing if the card is a face card or not
     */
    public boolean checkFace(){ return this.isFace; }

    /**
     * Returns the card as a String
     *
     * @return a String representing the card
     */
    @Override
    public String toString() {
        return this.rank + this.suit;
    }

    /**
     * Compares if an object is equal a card
     *
     * @param obj An object that is being compared to the card
     * @return a boolean representing if the object is equal to the card
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card c = (Card) obj;
            return this.suit == c.getSuit() && this.rank.equals(c.getRank());
        } else {
            return false;
        }
    }

}
