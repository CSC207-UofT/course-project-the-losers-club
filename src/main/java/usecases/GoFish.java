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
            fish(currPlayer);
            if (!this.deck.isEmpty()) {
                this.currPlayer.addToHand(this.deck.drawCard());
            }
            this.currPlayerIndex = (this.currPlayerIndex + 1) % this.players.length;

        }
        checkWin();
    }

    public boolean checkCatch() {
        return false;
    }

    public void fish(Player player) {

    }

    @Override
    public boolean checkMove(Card card) {
        return false;
    }

    @Override
    public void makeMove(Card card) {

    }

    public boolean gameEnd() {
        return false;
    }

    @Override
    public boolean checkWin() {
        return false;
    }
}
