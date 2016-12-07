package milo.controllers.utils;

/**
 * Class name:  LOG
 * Description: This class contains the methods used for logging
 *              It might be useful later
 */

public class LOG {
    private final static String LOG_TAG = "Music Hub: ";

    /**
     * Function name:   e
     * Usage:   to shout out the error message to the terminal
     *
     * @param   errorMessage    message to be shouted out
     */

    public static void e(String errorMessage) {
        System.out.println(LOG_TAG + "(ERROR): " + errorMessage);
    }
}
