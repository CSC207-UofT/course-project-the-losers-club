package usecases;

import entities.Card;

import java.util.HashMap;


public class GoFish extends GameTemplate {
    private final Input gameInput;
    private final Output gameOutput;
    private HashMap<Player, Integer> scoreTracker;

    public GoFish(int numPlayers, Input gameInput, Output gameOutput) {
        super(numPlayers);
        this.gameInput = gameInput;
        this.gameOutput = gameOutput;
        this.scoreTracker = new HashMap<Player, Integer>();
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
                fish(currPlayer);
            }
            if (!this.deck.isEmpty()) {
                this.currPlayer.addToHand(this.deck.drawCard());
            }
            this.currPlayerIndex = (this.currPlayerIndex + 1) % this.players.length;
        }
        outputWinner();
    }

    public void fish(Player player) {
        String rank;
        boolean loopedRankChoice = false;
        boolean loopedPlayerChoice = false;
        this.gameOutput.sendOutput("---------------------------------------\n");
        this.gameOutput.sendOutput(this.currPlayer.getName() + "'s Turn\n");
        this.gameOutput.sendOutput("---------------------------------------\n");

        do {
            if (loopedRankChoice) {
                this.gameOutput.sendOutput("Invalid rank chosen. Try again.");
            }

            this.gameOutput.sendOutput("Which card rank would you like to request?");
            rank = this.gameInput.getRank();

            if (!validRank(rank)) {
                loopedRankChoice = true;
            }

        } while (!validRank(rank));

        do {
            if (loopedPlayerChoice) {
                this.gameOutput.sendOutput("Invalid player to request from. Try again.");
            }

            this.gameOutput.sendOutput("Who would like to request cards from?");

        } while (false);
    }

    public boolean checkBook() {
        return false;
    }


    public boolean checkCatch() {
        return false;
    }

    public boolean validRank(String rank) {
        return false;
    }


    public boolean gameEnd() {
        return false;
    }

    public void outputWinner() {
    }

}
