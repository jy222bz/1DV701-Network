package lnu.assignment1.utility;


/**
 * This is a utility class with methods that can be used by other different classes.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
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
     *the
     * @param message the message to be printed out before termination.
     */
    public void terminateProgram(String message) {
        log(message);
        System.exit(-1);
    }

    /**
     * It returns  current time.
     */
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * It reruns the elapsed time.
     *
     * @param actual the actual time when the functions starts.
     * @return long
     */
    public long getElapsedTime(long actual) {
        return (System.currentTimeMillis() - actual);
    }
}
