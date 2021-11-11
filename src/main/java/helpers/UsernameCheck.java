package helpers;

import java.util.regex.Pattern;

/**
 * UsernameCheck defines methods for checking the validity of a username.
 */
public class UsernameCheck {

    private static final Pattern USERNAME_CHECK = Pattern.compile("^([a-zA-Z0-9][-_]?){0,15}[a-zA-Z0-9]$");

    public static boolean checkUsername(String s) {
        return USERNAME_CHECK.matcher(s).matches();
    }
}
