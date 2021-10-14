package usecases;
import java.util.ArrayList;

import entities.Deck;
import entities.Card;

public abstract class Game {

    public interface InOut {
        String getCard();

        void sendOutput();
    }

    private ArrayList<Player> players;
    private Deck deck;
    private Player currPlayer;

    public Game(ArrayList<Player> players){}

    public abstract void checkMove();

    public abstract void makeMove(int move);

    public abstract boolean checkWin();

}
