package entities;

public class Card {
    // Instances attributes
    private String rank;
    private char suit;
    private boolean isFace;

    // Constructor Method
    public Card(String rank, char suit){
        this.rank = rank;
        this.suit = suit;
        this.isFace = false;

        // Checks if the card is a face card
        String[] faces = new String[]{"K", "Q", "J"};
        for (String element : faces){
            if (element == this.rank){
                this.isFace = true;
            }
        }
    }
    // Returns the rank of the card
    public String get_rank(){
        return this.rank;
    }

    // Returns the suit of the card
    public char get_suit(){
        return this.suit;
    }

    // Returns if the card is a face card or not
    public boolean isFace(){
        return this.isFace;
    }

    public String toString() {
        StringBuilder s = new StringBuilder().append(this.get_rank()).append(this.get_suit());
        return s.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card c = (Card) obj;
            return this.suit == c.get_suit() && this.rank.equals(c.get_rank());
        } else {
            return false;
        }
    }

}
