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
    public boolean get_isFace(){
        return this.isFace;
    }

}
