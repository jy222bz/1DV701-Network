package lnu.assignment2.exceptions;

/**
 * An exception class which is the super class and the base for the other sub-classes.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class BaseException extends Exception {

    /**
     * A constructor to construct an Exception Object.
     *
     * @param classification the classification of the exception.
     */
    public BaseException(Classification classification) {
        super(classification.getDescription());
    }
}
