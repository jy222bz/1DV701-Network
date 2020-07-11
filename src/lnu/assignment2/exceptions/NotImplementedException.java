package lnu.assignment2.exceptions;

/**
 * An exception class for requesting methods that are not implemented.
 * It extends the base Exception.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class NotImplementedException extends BaseException {


    /**
     * A constructor to construct an Exception Object.
     *
     * @param classification the classification of the exception.
     */
    public NotImplementedException(Classification classification) {
        super(classification);
    }
}
