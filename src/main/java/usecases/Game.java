package usecases;
import java.util.ArrayList;

import entities.Deck;
import entities.Card;

public abstract class Game {

    protected Player[] players;
    protected Deck deck;
    protected Player currPlayer;
    protected int currPlayerIndex;

    public Game(int numPlayers){
        this.players = new Player[numPlayers];
        for (int i=0; i < numPlayers; i++) {
            Player newPlayer = new Player("Player " + (i + 1));
            this.players[i] = newPlayer;
        }
        this.currPlayer = this.players[0];
        this.deck = new Deck();
    }

    public abstract void startGame();

    public abstract boolean checkMove(Card card);

    public abstract void makeMove(Card card);

    public abstract boolean checkWin();

}
