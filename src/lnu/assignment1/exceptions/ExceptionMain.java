package lnu.assignment1.exceptions;

/**
 * This class extends Exception.
 * To provide customized exceptions.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class ExceptionMain extends Exception {


    /**
     * A constructor to construct an Exception Object.
     *
     * @param classification the classification of the exception.
     */
    public ExceptionMain(Classification classification) {
        super(classification.getDescription());
    }
}
