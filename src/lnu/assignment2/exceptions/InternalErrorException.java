package lnu.assignment2.exceptions;

/**
 * An exception class for server internal error. It extends the base Exception.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class InternalErrorException extends BaseException {
    /**
     * A constructor to construct an Exception Object.
     *
     * @param classification the classification of the exception.
     */
    public InternalErrorException(Classification classification) {
        super(classification);
    }
}
