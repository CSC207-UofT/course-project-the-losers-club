package usecases;

import entities.Card;

import java.util.*;

public class War extends Game{
    private final ArrayList[] playingField = new ArrayList[] {new ArrayList<Card>(), new ArrayList<Card>()};
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
            if (!checkWin()) {
                if (inWar) {
                    makeMove(currPlayer.getHand().removeCard());
                    currPlayerIndex = 1;
                    currPlayer = players[currPlayerIndex];
                    makeMove(currPlayer.getHand().removeCard());
                    currPlayerIndex = 0;
                    currPlayer = players[currPlayerIndex];
                }
            }
            Card topCard0 = (Card) playingField[0].get(playingField[0].size() - 1);
            Card topCard1 = (Card) playingField[1].get(playingField[1].size() - 1);

            this.gameOutput.sendOutput("---------------------------------------");
            this.gameOutput.sendOutput(players[0].getName() + " flipped the card " + topCard0);
            this.gameOutput.sendOutput(players[1].getName() + " flipped the card " + topCard1);

            int topCardHierarchy0 = java.util.Arrays.asList(Hierarchy).indexOf(topCard0.getRank());
            int topCardHierarchy1 = java.util.Arrays.asList(Hierarchy).indexOf(topCard1.getRank());
            int winner = 2;

            if (topCardHierarchy0 == topCardHierarchy1) {
                if (inWar) {
                    this.gameOutput.sendOutput("Both players have flipped the same rank of card, so war continues!");
                }
                inWar = true;
                this.gameOutput.sendOutput("Both players have flipped the same rank of card, so war begins!");

            } else if (topCardHierarchy0 < topCardHierarchy1) {
                this.gameOutput.sendOutput( players[1].getName() + "'s card outranks their opponents and they win the battle!");
                winner = 1;
            } else {
                this.gameOutput.sendOutput( players[0].getName() + "'s card outranks their opponents and they win the battle!");
                winner = 0;
            }

            if (winner < 2) {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < playingField[j].size(); k++) {
                        players[winner].addToHand((Card) playingField[j].get(k));
                    }
                }
            }
            this.gameOutput.sendOutput("---------------------------------------");

            gameInput.stall();
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
