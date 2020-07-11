package lnu.assignment2.utility;

/**
 * This is a utility class with methods that can be used by other different classes.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class Utility {


    /**
     * The logger. It is good to log the networking with logger, to get the date and time in specifics.
     */
    private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());

    /**
     * It logs messages and the time.
     *
     * @param message the message to be printed out.
     */
    public void log(String message) {
        logger.info(message);
    }

    /**
     * It terminates the application.
     * It logs a message before termination.
     * the
     *
     * @param message the message to be printed out before termination.
     */
    public void terminateProgram(String message) {
        log(message);
        System.exit(-1);
    }
}
