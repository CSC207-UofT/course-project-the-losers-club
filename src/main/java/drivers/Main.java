package drivers;

import controllers.InOut;
import entities.Card;
import entities.Hand;
import usecases.CrazyEights;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        InOut cio = new ConsoleInOut();

        CrazyEights cEight = new CrazyEights(2, cio);
        cEight.startGame();
    }
}
