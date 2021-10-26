package presenters.console;

import usecases.GameTemplate;


/**
 * Output uses System.out to display information to the user.
 * <p>
 * It is intended to be used as a basic command line interface.
 *
 * @see GameTemplate.Output
 */
public class Output implements GameTemplate.Output {

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
