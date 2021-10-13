package helpers;

public class CardHash {

    /**
     * Return a basic integer hash for a card specified by suit and rank.
     *
     * @param suit card's suit. Use the first letter of the suit's name (e.g. use 'C' for the suit "Clubs").
     * @param rank card's rank. Use the 1 or 2 letter representation of the rank (e.g. use "A" for "Ace",
     *             "3" for 3, and "10" for 10).
     * @return a basic integer hash corresponding to the given suit and rank
     */
    public static int getCardHashCode(char suit, String rank) {
        int code = 0;

        // generate suit code
        switch (Character.toUpperCase(suit)) {
            case 'C':
                code += 100;
                break;

            case 'D':
                code += 200;
                break;

            case 'H':
                code += 300;
                break;

            case 'S':
                code += 400;
                break;

            default:
                throw new IllegalArgumentException("Invalid suit of " + suit + " (not a suit)");
        }

        // generate rank code
        // A = 1, 2-10 = 2-10, J = 11, Q = 12, K = 13
        switch (rank.toUpperCase()) {
            case "A":
                code += 1;
                break;

            case "J":
                code += 11;
                break;

            case "Q":
                code += 12;
                break;

            case "K":
                code += 13;
                break;

            default:
                int r;
                try {  // try numeric cases
                    r = Integer.parseInt(rank);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid rank of " + rank + " (not a rank)");
                }

                if (r <= 10 && r >= 2) {  // check numeric bounds
                    code += r;
                } else {
                    throw new IllegalArgumentException("Invalid rank of " + rank + " (invalid numeric rank)");
                }
                break;

        }

        return code;

    }
}
