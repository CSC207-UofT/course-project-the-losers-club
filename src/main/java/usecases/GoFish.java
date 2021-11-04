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
                if (bookPresent()) {
                    scoreTracker.put(currPlayer, scoreTracker.get(currPlayer) + 1);
                }
            }
            if (!this.deck.isEmpty()) {
                this.currPlayer.addToHand(this.deck.drawCard());
            }
            this.currPlayerIndex = (this.currPlayerIndex + 1) % this.players.length;
        }
        outputWinner();
    }

    public void fish() {
        String rank;
        Player chosenPlayer;
        boolean loopedRankChoice = false;
        boolean loopedFish = false;
        int initialHandSize;
        this.gameOutput.sendOutput("---------------------------------------\n");
        this.gameOutput.sendOutput(this.currPlayer.getName() + "'s Turn\n");
        this.gameOutput.sendOutput("---------------------------------------\n");
        do {
            if (loopedFish) {
                this.gameOutput.sendOutput("Successful catch! Your turn continues!");
            }

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

            this.gameOutput.sendOutput("Who would like to request cards from?");
            chosenPlayer = this.gameInput.getPlayer(currPlayer, players);

            initialHandSize = this.currPlayer.getHand().getSize();
            this.currPlayer.addToHand(chosenPlayer.removeFromHand(rank));

            if (initialHandSize != this.currPlayer.getHand().getSize()) {
                loopedFish = true;
            }

            if (bookPresent()) {
                scoreTracker.put(this.currPlayer, scoreTracker.get(this.currPlayer) + 1);
            }
        } while (initialHandSize != this.currPlayer.getHand().getSize());
    }

    public boolean bookPresent() {
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
