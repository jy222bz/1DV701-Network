package lnu.assignment2.exceptions;

/**
 * An exception class for files that do not exist in the server system.
 * It extends the base Exception.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class FileDoesNotExist extends BaseException {
    /**
     * A constructor to construct an Exception Object.
     *
     * @param classification the classification of the exception.
     */
    public FileDoesNotExist(Classification classification) {
        super(classification);
    }
}
