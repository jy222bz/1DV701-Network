package lnu.assignment3.models;

/**
 * An enum class for the requests.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-03-07
 */
public enum Request {

    /**
     * The last request types i.e. TRANSFER_IS_NOT_DONE and INITIAL, it have nothing
     * to do with the client nor the standard requests.
     * They are useful internally.
     */
    OPERATION_READ_REQUEST(1),
    OPERATION_WRITE_REQUEST(2),
    OPERATION_DATA(3),
    OPERATION_ACKNOWLEDGMENT_REQUEST(4),
    OPERATION_ERROR(5),
    TRANSFER_IS_NOT_DONE(-100),
    INITIAL(-100);

    /**
     * A private field for the code of the request.
     */
    private final int code;

    /**
     * It constructs the object.
     *
     * @param code the code of the request.
     */
    Request(int code) {
        this.code = code;
    }

    /**
     * it returns the code.
     *
     * @return code
     */
    public int getCode() {
        return code;
    }
}
