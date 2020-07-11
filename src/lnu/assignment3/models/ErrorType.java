package lnu.assignment3.models;

/**
 * An enum class for the error type.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public enum ErrorType {

    /**
     * Since the error code zero can be used for any error.
     * It was used for two type of errors in this implementation.
     * The last error types i.e. NO_ERROR and ERROR_CLOSE_CONNECTION have nothing to do with the standard errors.
     * They are useful in terms of determining the state of the transmission.
     * That implies that they are used internally.
     */
    INVALID_MODE(0, "The mode is not an OCTET. The server supports ONLY OCTET."),
    TRANSMISSION_ERROR(0, "An error has occurred during transmission. " +
            "Either the transmission failed or the timeout has exceeded."),
    FILE_NOT_FOUND(1, "The file is not found."),
    ACCESS_VIOLATION(2, "Access Violation."),
    DISK_FULL(3, "Disk full or allocation exceeded."),
    ILLEGAL_OPERATION(4, "Illegal Trivial-File-Transfer-Protocol Operation."),
    UNKNOWN_ID(5, "Unknown transfer ID."),
    FILE_EXISTS(6, "File already exists."),
    NO_SUCH_USER(7, "No such user."),
    ERROR_CLOSE_CONNECTION(-100, "When the client sends an error."),
    NO_ERROR(-100, "Everything is okay.");

    /**
     * A private file for the string.
     */
    private final String desc;

    /**
     * A private field for the code.
     */
    private final int code;

    /**
     * A constructor, to construct an object.
     *
     * @param code the code of the error.
     * @param desc the description.
     */
    ErrorType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * It returns the description.
     *
     * @return String
     */
    public String getDescription() {
        return desc;
    }

    /**
     * It returns the code.
     *
     * @return int
     */
    public int getCode() {
        return code;
    }
}
