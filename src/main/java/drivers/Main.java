package drivers;

import controllers.GameSelector;
import presenters.console.Input;
import presenters.console.Output;

import usecases.GameTemplate;

public class Main {
    public static void main(String[] args) {
        GameSelector.Input selectorInput = new Input();
        GameSelector.Output selectorOutput = new Output();

        GameSelector selector = new GameSelector(selectorInput, selectorOutput, new String[]{"Crazy Eights"},
                (GameTemplate.Input) selectorInput, (GameTemplate.Output) selectorOutput);
        selector.run();
    }
}
