package lnu.assignment2.static_data;

/**
 * An enum class for the method and its values; the name and the boolean value whether the method is implemented.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public enum Method {

    GET("GET", true),
    HEAD("HEAD", false),
    POST("POST", false),
    PUT("PUT", false),
    DELETE("DELETE", false),
    TRACE("TRACE", false),
    CONNECT("CONNECT", false);

    /**
     * A private field for the name of the method.
     */
    private final String methodName;

    /**
     * A private field for the boolean.
     */
    private final boolean isImplemented;

    /**
     * A constructor to construct the object.
     *
     * @param method        the name of the method.
     * @param isImplemented whether is implemented.
     */
    Method(String method, boolean isImplemented) {
        this.methodName = method;
        this.isImplemented = isImplemented;
    }

    /**
     * It returns the name.
     *
     * @return String
     */
    public String getMethod() {
        return methodName;
    }

    /**
     * It returns the boolean.
     *
     * @return boolean
     */
    public boolean isImplemented() {
        return isImplemented;
    }
}
