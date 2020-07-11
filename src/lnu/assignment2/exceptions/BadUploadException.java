package lnu.assignment2.exceptions;

/**
 * An exception class for bad upload, when the client tries to post on existing file.
 * It extends the base Exception.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class BadUploadException extends BaseException {
    /**
     * A constructor to construct an Exception Object.
     *
     * @param classification the classification of the exception.
     */
    public BadUploadException(Classification classification) {
        super(classification);
    }
}
