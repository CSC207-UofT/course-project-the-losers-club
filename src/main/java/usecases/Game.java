package usecases;
import java.util.ArrayList;
import java.util.List;

import entities.Deck;
import entities.Card;

public abstract class Game {

    protected Player[] players;
    protected Deck deck;
    protected Player currPlayer;
    protected int currPlayerIndex;
    private static final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static final char[] SUITS = {'H', 'S', 'D', 'C'};

    public Game(int numPlayers){
        this.players = new Player[numPlayers];
        for (int i=0; i < numPlayers; i++) {
            Player newPlayer = new Player("Player " + (i + 1));
            this.players[i] = newPlayer;
        }
        this.currPlayer = this.players[0];

        // Creates a deck with the cards created outside the Deck class
        List<Card> cardList = new ArrayList<>();
        for (String i : RANKS){
            for (char j : SUITS){
                cardList.add(new Card(i, j));
            }
        }
        this.deck = new Deck(cardList);
    }

    public abstract void startGame();

    public abstract boolean checkMove(Card card);

    public abstract void makeMove(Card card);

    public abstract boolean checkWin();

}
