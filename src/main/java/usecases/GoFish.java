package usecases;

import entities.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GoFish extends GameTemplate {
    private final Input gameInput;
    private final Output gameOutput;
    private HashMap<Player, Integer> scoreTracker;
    private Integer handSize;

    public GoFish(List<String> usernames, UserManager userManager, Input gameInput, Output gameOutput) {
        super(usernames, userManager);
        this.gameInput = gameInput;
        this.gameOutput = gameOutput;
        this.scoreTracker = new HashMap<>();
        this.currPlayerIndex = 0;
        this.deck.shuffle();
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

    public boolean fish() {
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

            this.gameOutput.sendOutput("Who would like to request cards from?\n");
            chosenPlayer = this.gameInput.getPlayer(currPlayer, players);

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

    public void checkEveryoneForBook() {
        for (Player player : players) {
            this.currPlayer = player;
            checkForBook();
        }
    }

    public void checkForBook() {
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


    public boolean validRank(String rank) {
        return currPlayer.getHandString().contains(rank);
    }


    public boolean gameEnd() {
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

    public void outputWinner() {
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
        System.out.println(String.format("Winner(s): %s with %s points!\n", winners, maxScore));
    }

}
