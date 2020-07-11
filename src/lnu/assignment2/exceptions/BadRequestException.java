package lnu.assignment2.exceptions;

/**
 * An exception class for bad requests. It extends the base Exception.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class BadRequestException extends BaseException{


    /**
     * A constructor to construct an Exception Object.
     *
     * @param classification the classification of the exception.
     */
    public BadRequestException(Classification classification) {
        super(classification);
    }
}
