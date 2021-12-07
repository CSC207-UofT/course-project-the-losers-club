package helpers;

import java.util.regex.Pattern;

/**
 * UsernameCheck defines a useful standalone method for checking the validity of a username.
 */
public class UsernameCheck {

    /**
     * Produces a Pattern to check username strings against. If they follow the Pattern, it is a valid username.
     */
    private static final Pattern USERNAME_CHECK = Pattern.compile("^([a-zA-Z0-9][-_]?){0,15}[a-zA-Z0-9]$");

    /**
     * Checks if a username is valid
     *
     * @param s Username of a potential user
     * @return boolean that is true iff the username is valid
     */
    public static boolean checkUsername(String s) {
        return USERNAME_CHECK.matcher(s).matches();
    }
}
