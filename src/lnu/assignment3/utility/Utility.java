package lnu.assignment3.utility;

import lnu.assignment3.exceptions.BaseException;
import lnu.assignment3.exceptions.Classification;
import lnu.assignment3.validators.BufferValidator;

import java.util.Arrays;

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

    /**
     * It rests the buffer to the target value.
     * It fills the array with that value.
     *
     * @param val    the target value.
     * @param buffer buffer.
     */
    public void fillBuffer(int val, byte[] buffer) {
        Arrays.fill(buffer, (byte) val);
    }

    /**
     * It sets the buffer. If the targeted value exceeds the memory capacity,
     * then it throws an exception.
     *
     * @param bufferSize the buffer size.
     * @return byte[]
     * @throws BaseException if the the size exceeds the memory capacity.
     */
    public byte[] setupBuffer(int bufferSize) throws BaseException {
        byte[] buffer = BufferValidator.getBuffer(bufferSize);
        if (buffer.length == 0)
            throw new BaseException(Classification.BUFFER_SIZE);
        return buffer;
    }
}
