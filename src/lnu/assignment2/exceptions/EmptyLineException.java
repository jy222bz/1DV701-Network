package lnu.assignment2.exceptions;

/**
 * This class extends the BaseException, it is for empty lines.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class EmptyLineException extends BaseException{
    /**
     * A constructor to construct an Exception Object.
     *
     * @param classification the classification of the exception.
     */
    public EmptyLineException(Classification classification) {
        super(classification);
    }
}
