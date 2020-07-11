package lnu.assignment2.exceptions;

/**
 * This class extends Exception.
 * To provide customized exceptions, it is meant for the arguments.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class IllegalArgumentsException extends BaseException{


    /**
     * A constructor to construct an Exception Object.
     *
     * @param classification the classification of the exception.
     */
    public IllegalArgumentsException(Classification classification) {
        super(classification);
    }
}