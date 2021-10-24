package drivers;

import controllers.InOut;
import usecases.CrazyEights;

public class Main {
    public static void main(String[] args) {
        InOut cio = new ConsoleInOut();

        CrazyEights cEight = new CrazyEights(2, cio);
        cEight.startGame();
    }
}
