package drivers;

import presenters.console.Input;
import presenters.console.Output;
import usecases.CrazyEights;
import usecases.GameTemplate;

public class Main {
    public static void main(String[] args) {
        GameTemplate.Input cIn = new Input();
        GameTemplate.Output cOut = new Output();

        CrazyEights cEight = new CrazyEights(2, cIn, cOut);
        cEight.startGame();
    }
}
