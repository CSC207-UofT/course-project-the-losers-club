package drivers;

import controllers.MainMenu;
import presenters.console.Input;
import presenters.console.Output;
import usecases.GameTemplate;

public class Main {
    public static void main(String[] args) {
        MainMenu.Input selectorInput = new Input();
        MainMenu.Output selectorOutput = new Output();

        MainMenu selector = new MainMenu(selectorInput, selectorOutput, new String[]{"Crazy Eights", "War", "Go Fish", "Bura"},
                (GameTemplate.Input) selectorInput, (GameTemplate.Output) selectorOutput);

        String inputFile = "userManager.ser";
        String outputFile = "userManager.ser";
        selector.run(inputFile, outputFile);
    }
}