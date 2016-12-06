package milo.controllers.utils;

import milo.data.Constants;

/**
 * Class name:  LOG
 * Description: This class contains the methods used for logging
 *              It might be useful later
 */

public class LOG {

    /**
     * Function name:   e
     * Usage:   to shout out the error message to the terminal
     *
     * @param   errorMessage    message to be shouted out
     */

    public static void e(String errorMessage) {
        System.out.println(Constants.getLogTag() + "(ERROR): " + errorMessage);
    }
}
