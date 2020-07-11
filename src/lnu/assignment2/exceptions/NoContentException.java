package lnu.assignment2.exceptions;

/**
 * An exception class for no content when the client sends post or put with no content.
 * It extends the base Exception.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class NoContentException extends BaseException{
    /**
     * A constructor to construct an Exception Object.
     *
     * @param classification the classification of the exception.
     */
    public NoContentException(Classification classification) {
        super(classification);
    }
}
