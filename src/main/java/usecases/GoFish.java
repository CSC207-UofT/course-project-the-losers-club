package usecases;

import entities.Card;
import usecases.IOInterfaces.GoFishIO;
import usecases.usermanagement.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class GoFish extends GameTemplate {
    private static final int MAX_PLAYERS = 7;
    private static final int MIN_PLAYERS = 2;
    private final GoFishIO GO_FISH_IO;
    protected final HashMap<Player, Integer> SCORE_TRACKER;

    /**
     * Instantiate a new GoFish game instance.
     *
     * @param usernames   the list of usernames of players that are playing the game.
     * @param userManager user management vessel
     * @param goFishIO   A GoFishIO object allowing for player input/output and game visualization.
     */
    public GoFish(List<String> usernames, UserManager userManager, GoFishIO goFishIO) {
        this(usernames, userManager, goFishIO, new Random());
    }

    /**
     * Instantiate a new GoFish game instance. This constructor allows the deck to be seeded with a state.
     *
     * @param usernames   the list of usernames of players that are playing the game.
     * @param userManager user management vessel
     * @param goFishIO   A GoFishIO object allowing for player input/output and game visualization.
     * @param rand        a Random object for creating deterministic behaviour.
     */
    public GoFish(List<String> usernames, UserManager userManager, GoFishIO goFishIO, Random rand) {
        super(usernames, userManager, goFishIO);
        this.SCORE_TRACKER = new HashMap<>();
        this.currPlayerIndex = 0;
        this.deck.shuffle(rand);
        this.GO_FISH_IO = goFishIO;
        int handSize;
        if (usernames.size() <= 3) {
            handSize = 7;
        } else {
            handSize = 5;
        }
        for (Player player : this.players) {
            for (int i = 0; i < 7; i++) {
                player.addToHand(this.deck.drawCard());
            }
            this.SCORE_TRACKER.put(player, 0);
        }
    }

    /**
     * Return's this game's maximum number of players.
     *
     * @return the maximum number of players.
     */
    public static int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    /**
     * Return's this game's minimum number of players required to play the game.
     *
     * @return the maximum number of players.
     */
    public static int getMinPlayers() {
        return MIN_PLAYERS;
    }

    /**
     * Return a String representation of this class.
     *
     * @return the String "Go Fish"
     */
    public String toString() {
        return "Go Fish";
    }

    /**
     * The main part of the game that prompts the player to "fish" for cards, check for book in player's hand, iterate
     * over the players, and finally output winner(s).
     */
    @Override
    public void startGame() {
        checkEveryoneForBook();
        while (!gameEnd()) {
            this.currPlayer = this.players[this.currPlayerIndex];
            this.GO_FISH_IO.changePlayer(this.currPlayer.getUsername());
            if (!currPlayer.isHandEmpty()) {
                if (!fish()) {
                    this.GO_FISH_IO.sendPopup("Go Fish! No matches.");
                }
            }
            if (!this.deck.isEmpty()) {
                this.currPlayer.addToHand(this.deck.drawCard());
                this.GO_FISH_IO.sendPopup("Drawing a card from the deck.");
                this.GO_FISH_IO.showHand(this.currPlayer.getHandStringFormatted());
            }

            checkForBook();

            this.currPlayerIndex = (this.currPlayerIndex + 1) % this.players.length;
        }
        outputWinner();
    }

    /**
     * Prompts the user to select a card rank and a player to request the cards from. If there's a catch (requested
     * player has cards of the chosen rank in hand), then those cards are transferred to current player's hand. Also
     * checks for book after each attempt at "fishing".
     *
     * @return true if there is a catch and false if there is no catch.
     */
    private boolean fish() {
        String rank;
        Player chosenPlayer;
        ArrayList<Card> cardCatch;
        boolean loopedFish = false;
        int initialHandSize;
        int finalHandSize;
        do {
            boolean loopedRankChoice = false;

            if (loopedFish) {
                this.GO_FISH_IO.sendPopup("Your turn continues.\n");
            }
            this.GO_FISH_IO.showHand(this.currPlayer.getHandStringFormatted());
            do {
                if (loopedRankChoice) {
                    this.GO_FISH_IO.sendPopup("Invalid rank chosen. Try again.");
                }

                rank = this.GO_FISH_IO.getRank();

                if (!validRank(rank)) {
                    loopedRankChoice = true;
                }

            } while (!validRank(rank));

            chosenPlayer = requestPlayer();

            initialHandSize = this.currPlayer.getHand().getSize();
            cardCatch = chosenPlayer.removeFromHand(rank);
            this.currPlayer.addToHand(cardCatch);
            finalHandSize = this.currPlayer.getHand().getSize();

            if (initialHandSize != finalHandSize) {
                this.GO_FISH_IO.sendPopup(String.format("Successful catch!\n%s moved from %s's hand to %s's hand.\n",
                        cardCatch.toString(), chosenPlayer.getUsername(), currPlayer.getUsername()));
                loopedFish = true;
            }

            checkForBook();

        } while (initialHandSize != finalHandSize && !this.currPlayer.isHandEmpty());
        return initialHandSize != finalHandSize;
    }

    /**
     * Prompts the user to choose a username (from a list of usernames) that they would like to request cards from.
     *
     * @return a Player object corresponding to the username chosen by the user.
     */
    private Player requestPlayer() {
        String chosenUsername = this.GO_FISH_IO.getPlayerUsername(this.currPlayer.getUsername(), this.usernames);
        for (Player player : this.players) {
            if (player.getUsername().equals(chosenUsername)) {
                return player;
            }
        }
        throw new RuntimeException();
    }

    /**
     * Checks every player's hand for books (4 cards of the same rank) and removes if any present. This method is
     * executed at the very beginning of the game to remove the books from hands after cards have been dealt.
     */
    private void checkEveryoneForBook() {
        for (Player player : players) {
            this.currPlayer = player;
            checkForBook();
        }
    }

    /**
     * Checks for book (4 cards of the same rank) in current player's hand. If book is found, removes those cards. If
     * hand is empty after removing a book of cards, draw a card from the deck.
     */
    private void checkForBook() {
        HashMap<String, Integer> numRanks = new HashMap<>();
        for (String rank : RANKS) {
            numRanks.put(rank, 0);
        }
        for (Card card : this.currPlayer.getHand().getCards()) {
            numRanks.put(card.getRank(), numRanks.get(card.getRank()) + 1);
        }
        for (String rank : RANKS) {
            if (numRanks.get(rank) == 4) {
                SCORE_TRACKER.put(this.currPlayer, SCORE_TRACKER.get(this.currPlayer) + 1);
                currPlayer.removeFromHand(rank);
                this.GO_FISH_IO.sendPopup(String.format("A book is found in %1$s's hand! The following cards are " +
                        "removed: %2$sH, %2$sS, %2$sD, %2$sC\n", this.currPlayer.getUsername(), rank));
                if (this.currPlayer.isHandEmpty() && !this.deck.isEmpty()) {
                    this.GO_FISH_IO.sendPopup("Hand is empty after removing the book. Drawing a card from deck.\n");
                    this.currPlayer.addToHand(this.deck.drawCard());
                    this.GO_FISH_IO.showHand(this.currPlayer.getHandString());
                }
            }
        }
    }

    /**
     * Checks whether the chosen rank of card is valid. Rank is valid if the current player's hand contains at least one
     * card of the chosen rank.
     *
     * @param rank the rank of the cards that the user wants to request from other player.
     * @return true if the rank is valid (hand contains a card of the said rank); otherwise, return false.
     */
    private boolean validRank(String rank) {
        return this.currPlayer.getHandString().contains(rank);
    }

    /**
     * Checks whether the game has ended. Game ends when all players' hands are empty AND the deck is empty.
     *
     * @return true if the game has ended; otherwise, return false (if deck is not empty or there are players with cards
     * in their hands).
     */
    private boolean gameEnd() {
        if (!this.deck.isEmpty()) {
            return false;
        } else {
            for (Player player : players) {
                if (!player.isHandEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Outputs the winner(s) based on number of points (books) collected. Also updates user statistics by incrementing
     * games played by 1 and whether it's a win or a loss.
     */
    private void outputWinner() {
        ArrayList<String> winners = new ArrayList<>();
        int maxScore = 0;
        for (Player player : players) {
            if (SCORE_TRACKER.get(player) == maxScore) {
                winners.add(player.getUsername());
            } else if (SCORE_TRACKER.get(player) > maxScore) {
                maxScore = SCORE_TRACKER.get(player);
                winners.clear();
                winners.add(player.getUsername());
            }
        }

        if (winners.size() == 1) {
            this.addUserStats(winners.get(0));
        } else {
            this.addUserStats(winners);
        }

        this.GO_FISH_IO.closeMessage(String.format("Winner(s): %s with %s points!\n", winners, maxScore));
    }
}
