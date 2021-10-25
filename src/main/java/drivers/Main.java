package drivers;

import presenters.console.Input;
import presenters.console.Output;
import usecases.CrazyEights;
import usecases.Game;

public class Main {
    public static void main(String[] args) {
        Game.Input cIn = new Input();
        Game.Output cOut = new Output();

        CrazyEights cEight = new CrazyEights(2, cIn, cOut);
        cEight.startGame();
    }
}
