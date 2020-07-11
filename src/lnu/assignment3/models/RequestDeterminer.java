package lnu.assignment3.models;

import java.util.HashMap;

/**
 * A class for the Request.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-03-07
 */
public class RequestDeterminer {

    /**
     * A private and static field for the class.
     */
    private static RequestDeterminer mRequestDeterminer;

    /**
     * A private field for the hash map list.
     */
    private final HashMap<Integer, Request> container;

    /**
     * A constructor to construct the object.
     * It is private to prevent creating instances.
     */
    private RequestDeterminer() {
        container = new HashMap<>();
        container.put(Request.OPERATION_READ_REQUEST.getCode(), Request.OPERATION_READ_REQUEST);
        container.put(Request.OPERATION_WRITE_REQUEST.getCode(), Request.OPERATION_WRITE_REQUEST);
        container.put(Request.OPERATION_DATA.getCode(), Request.OPERATION_DATA);
        container.put(Request.OPERATION_ACKNOWLEDGMENT_REQUEST.getCode(), Request.OPERATION_ACKNOWLEDGMENT_REQUEST);
        container.put(Request.OPERATION_ERROR.getCode(), Request.OPERATION_ERROR);
    }

    /**
     * It returns the instance of the object.
     *
     * @return Request
     */
    public static RequestDeterminer getInstance() {
        if (mRequestDeterminer == null)
            mRequestDeterminer = new RequestDeterminer();
        return mRequestDeterminer;
    }

    /**
     * It returns the Request.
     *
     * @param code the code of the Request.
     * @return Request
     */
    public Request getRequest(int code) {
        return container.get(code);
    }
}
