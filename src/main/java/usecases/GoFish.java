package usecases;

import entities.Card;

import java.util.ArrayList;
import java.util.HashMap;


public class GoFish extends GameTemplate {
    private final Input gameInput;
    private final Output gameOutput;
    private HashMap<Player, Integer> scoreTracker;

    public GoFish(int numPlayers, Input gameInput, Output gameOutput) {
        super(numPlayers);
        this.gameInput = gameInput;
        this.gameOutput = gameOutput;
        this.scoreTracker = new HashMap<>();
        this.currPlayerIndex = 0;
        this.deck.shuffle();
        if (numPlayers <= 3) {
            for (Player player : this.players) {
                for (int i=0; i < 7; i++) {
                    player.addToHand(this.deck.drawCard());
                }
                this.scoreTracker.put(player, 0);
            }
        } else {
            for (Player player : this.players) {
                for (int i=0; i < 5; i++) {
                    player.addToHand(this.deck.drawCard());
                }
                this.scoreTracker.put(player, 0);
            }
        }
    }

    @Override
    public void startGame() {
        while (!gameEnd()) {
            this.currPlayer = this.players[this.currPlayerIndex];
            if (!currPlayer.isHandEmpty()) {
                fish();
                this.gameOutput.sendOutput("Go Fish! No matches.\n");
            }
            if (!this.deck.isEmpty()) {
                this.currPlayer.addToHand(this.deck.drawCard());
                this.gameOutput.sendOutput("Drawing a card from the deck. \n");
                this.gameOutput.sendOutput(this.currPlayer.getName() + "'s Hand: " + this.currPlayer.getHandString() + "\n");
            }

            checkForBook();

            this.currPlayerIndex = (this.currPlayerIndex + 1) % this.players.length;
        }
        outputWinner();
    }

    public void fish() {
        String rank;
        Player chosenPlayer;
        boolean loopedFish = false;
        int initialHandSize;
        this.gameOutput.sendOutput("---------------------------------------\n");
        this.gameOutput.sendOutput(this.currPlayer.getName() + "'s Turn\n");
        this.gameOutput.sendOutput("---------------------------------------\n");
        do {
            checkForBook();

            boolean loopedRankChoice = false;

            if (loopedFish) {
                this.gameOutput.sendOutput("Successful catch! Your turn continues!\n");
            }
            this.gameOutput.sendOutput(this.currPlayer.getName() + "'s Hand: " + this.currPlayer.getHandString() + "\n");
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
            this.currPlayer.addToHand(chosenPlayer.removeFromHand(rank));

            if (initialHandSize != this.currPlayer.getHand().getSize()) {
                loopedFish = true;
            }

        } while (initialHandSize != this.currPlayer.getHand().getSize());
    }

    public void checkForBook() {
        HashMap<String, Integer> numRanks = new HashMap<>();
        for (String rank : RANKS) {
            numRanks.put(rank, 0);
        }
        for (Card card : currPlayer.getHand().getCards()) {
            numRanks.put(card.getRank(), numRanks.get(card.getRank()) + 1);
        }
        for (String rank : RANKS) {
            if (numRanks.get(rank) == 4) {
                scoreTracker.put(this.currPlayer, scoreTracker.get(this.currPlayer) + 1);
                currPlayer.removeFromHand(rank);
                this.gameOutput.sendOutput("A book is found in the hand! The following cards are removed: " +
                        rank + "H, " + rank + "S, " + rank + "D, " + rank + "C.");
                this.gameOutput.sendOutput(this.currPlayer.getName() + "'s Hand: " + this.currPlayer.getHandString() + "\n");
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
        ArrayList<Player> winners = new ArrayList<>();
        int maxScore = 0;
        for (Player player : players) {
            if (scoreTracker.get(player) == maxScore) {
                winners.add(player);
            } else if (scoreTracker.get(player) > maxScore) {
                maxScore = scoreTracker.get(player);
                winners.clear();
                winners.add(player);
            }
        }
        System.out.println("Winner(s): " + winners + "\n");
    }

}
