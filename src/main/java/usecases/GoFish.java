package usecases;

import entities.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class GoFish extends GameTemplate {
    private final Input gameInput;
    private final Output gameOutput;
    protected HashMap<Player, Integer> scoreTracker;
    private final Integer handSize;

    /**
     * Instantiate a new GoFish game instance.
     *
     * @param usernames the list of usernames of players that are playing the game.
     * @param userManager a usermanager that manages the user entities
     * @param gameInput A Game.Input object allowing for player input.
     * @param gameOutput A Game.Output object allowing for output to the player.
     */
    public GoFish(List<String> usernames, UserManager userManager, Input gameInput, Output gameOutput) {
        this(usernames, userManager, gameInput, gameOutput, new Random());
    }

    /**
     * Instantiate a new GoFish game instance. This constructor allows the deck to be seeded with a state.
     *
     * @param usernames the list of usernames of players that are playing the game.
     * @param userManager a usermanager that manages the user entities
     * @param gameInput A Game.Input object allowing for player input.
     * @param gameOutput A Game.Output object allowing for output to the player.
     * @param rand a Random object for creating deterministic behaviour.
     */
    public GoFish(List<String> usernames, UserManager userManager, Input gameInput, Output gameOutput, Random rand) {
        super(usernames, userManager);
        this.gameInput = gameInput;
        this.gameOutput = gameOutput;
        this.scoreTracker = new HashMap<>();
        this.currPlayerIndex = 0;
        this.deck.shuffle(rand);
        if (usernames.size() <= 3) {
            handSize = 7;
        } else {
            handSize = 5;
        }
        for (Player player : this.players) {
            for (int i = 0; i < 7; i++) {
                player.addToHand(this.deck.drawCard());
            }
            this.scoreTracker.put(player, 0);
        }
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
            this.gameOutput.sendOutput("---------------------------------------\n");
            this.gameOutput.sendOutput(this.currPlayer.getUsername() + "'s Turn\n");
            this.gameOutput.sendOutput("---------------------------------------\n");
            if (!currPlayer.isHandEmpty()) {
                if (!fish()) {
                    this.gameOutput.sendOutput("Go Fish! No matches.\n");
                }
            }
            if (!this.deck.isEmpty()) {
                this.currPlayer.addToHand(this.deck.drawCard());
                this.gameOutput.sendOutput("Drawing a card from the deck. \n");
                this.gameOutput.sendOutput(this.currPlayer.getUsername() + "'s Hand: " + this.currPlayer.getHandString() + "\n");
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
                this.gameOutput.sendOutput("Your turn continues.\n");
            }
            this.gameOutput.sendOutput(this.currPlayer.getUsername() + "'s Hand: " + this.currPlayer.getHandString() + "\n");
            do {
                if (loopedRankChoice) {
                    this.gameOutput.sendOutput("Invalid rank chosen. Try again.\n");
                }

                this.gameOutput.sendOutput("Which card rank would you like to request?\n");
                rank = this.gameInput.getRank();

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
                this.gameOutput.sendOutput(String.format("Successful catch!\n%s moved from %s's hand to %s's hand.\n",
                        cardCatch.toString(), chosenPlayer.getUsername(), currPlayer.getUsername()));
                loopedFish = true;
            }

            checkForBook();

        } while (initialHandSize != finalHandSize && !this.currPlayer.isHandEmpty());
        return initialHandSize != finalHandSize;
    }

    /**
     * Prompts the user to choose a username (from a list of usernames) that they would like to request cards from.
     * @return a Player object corresponding to the username chosen by the user.
     */
    private Player requestPlayer() {
        this.gameOutput.sendOutput("Who would like to request cards from?\n");
        String chosenUsername = this.gameInput.getPlayerUsername(this.currPlayer.getUsername(), this.usernames);
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
                scoreTracker.put(this.currPlayer, scoreTracker.get(this.currPlayer) + 1);
                currPlayer.removeFromHand(rank);
                this.gameOutput.sendOutput(String.format("A book is found in %1$s's hand! The following cards are " +
                        "removed: %2$sH, %2$sS, %2$sD, %2$sC\n", this.currPlayer.getUsername(), rank));
                if (this.currPlayer.isHandEmpty() && !this.deck.isEmpty()) {
                    this.gameOutput.sendOutput("Hand is empty after removing the book. Drawing a card from deck.\n");
                    this.currPlayer.addToHand(this.deck.drawCard());
                    this.gameOutput.sendOutput(this.currPlayer.getUsername() + "'s Hand: " + this.currPlayer.getHandString() + "\n");
                }
            }
        }
    }

    /**
     * Checks whether the chosen rank of card is valid. Rank is valid if the current player's hand contains at least one
     * card of the chosen rank.
     * @param rank the rank of the cards that the user wants to request from other player.
     * @return true if the rank is valid (hand contains a card of the said rank); otherwise, return false.
     */
    private boolean validRank(String rank) {
        return this.currPlayer.getHandString().contains(rank);
    }

    /**
     * Checks whether the game has ended. Game ends when all players' hands are empty AND the deck is empty.
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
            if (scoreTracker.get(player) == maxScore) {
                winners.add(player.getUsername());
            } else if (scoreTracker.get(player) > maxScore) {
                maxScore = scoreTracker.get(player);
                winners.clear();
                winners.add(player.getUsername());
            }
        }
        for (String username : this.usernames) {
            if (winners.contains(username)) {
                try {
                    this.userManager.addGamesPlayed(username, 1);
                } catch (UserManager.UserNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    this.userManager.addGamesPlayed(username, -1);
                } catch (UserManager.UserNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        this.gameOutput.sendOutput(String.format("Winner(s): %s with %s points!\n", winners, maxScore));
    }
}
