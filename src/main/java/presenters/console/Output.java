package presenters.console;

import usecases.Game;


/**
 * Output uses System.out to display information to the user.
 * <p>
 * It is intended to be used as a basic command line interface.
 *
 * @see Game.Output
 */
public class Output implements Game.Output {

    /**
     * Instantiate a new Output instance.
     */
    public Output() {
    }

    /**
     * Prints the specified object to the system console by calling the <code>s.toString()</code> method.
     *
     * @param s An Object that can be somehow outputted to the user.
     */
    @Override
    public void sendOutput(Object s) {
        System.out.println(s.toString());
    }

}
