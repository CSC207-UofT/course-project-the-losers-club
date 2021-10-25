package usecases;

import entities.Card;
import entities.Hand;

import java.util.*;

public class War extends Game{
    private final ArrayList[] playingField = new ArrayList[]{new ArrayList<Card>(), new ArrayList<Card>()};
    private final Input gameInput;
    private final Output gameOutput;
    private final static String[] Hierarchy = new String[] {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};


    public War(Input gameInput, Output gameOutput) {
        super(2);
        this.gameInput = gameInput;
        this.gameOutput = gameOutput;
        this.currPlayerIndex = 0;
        this.deck.shuffle();
        for (Player player : this.players) {
            for (int i = 0; i < 26; i++) {
                player.addToHand(this.deck.drawCard());
            }
        }
    }

    @Override
    public void startGame() {
        int turnCounter = 1;
        boolean inWar = false;
        while (!checkWin()) {
            this.currPlayer = this.players[this.currPlayerIndex];
            Card card = null;
            String crd;
            boolean looped = false;
            this.gameOutput.sendOutput("---------------------------------------");
            this.gameOutput.sendOutput("Turn " + turnCounter + ". Pile sizes: " + playingField[0].size());
            this.gameOutput.sendOutput("---------------------------------------");
            for (int i = 0; i < 2; i++) {
                if (playingField[i].isEmpty()) {
                    this.gameOutput.sendOutput(players[i].getName() + "'s Pile is empty");
                } else {
                    this.gameOutput.sendOutput(players[i].getName() + "'s Top Card is: " + playingField[i].get(playingField[i].size() - 1));
                }
                this.gameOutput.sendOutput("---------------------------------------");
            }
            gameInput.stall();

            makeMove(currPlayer.getHand().removeCard());
            currPlayerIndex = 1;
            currPlayer = players[currPlayerIndex];
            makeMove(currPlayer.getHand().removeCard());
            currPlayerIndex = 0;
            currPlayer = players[currPlayerIndex];
            if (inWar) {
                makeMove(currPlayer.getHand().removeCard());
                currPlayerIndex = 1;
                currPlayer = players[currPlayerIndex];
                makeMove(currPlayer.getHand().removeCard());
                currPlayerIndex = 0;
                currPlayer = players[currPlayerIndex];
            }

        }
    }

    @Override
    public boolean checkMove(Card card) {
        return true;
    }

    @Override
    public void makeMove(Card card) {
        this.playingField[currPlayerIndex].add(card);
    }

    @Override
    public boolean checkWin() {
        return (players[0].isHandEmpty() || players[1].isHandEmpty());
    }
}
