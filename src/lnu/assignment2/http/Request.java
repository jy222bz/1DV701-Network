package lnu.assignment2.http;

import lnu.assignment2.static_data.Method;

/**
 * A class for the HTTP request.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class Request {

    /**
     * A private field for the url.
     */
    private final String url;
    /**
     * A private field for the method.
     */
    private final Method method;
    /**
     * A private field for the boolean.
     */
    private final boolean doClose;

    /**
     * A constructor to construct an object, when there is no upload it required.
     *
     * @param statusLine the status line of HTTP request.
     * @param doClose    whether the connection should be closed after the response.
     * @param method     the HTTP method.
     */
    public Request(String[] statusLine, boolean doClose, Method method) {
        this.url = statusLine[1];
        this.doClose = doClose;
        this.method = method;
    }

    /**
     * Whether the connection should close after the response.
     *
     * @return boolean
     */
    public boolean shouldClose() {
        return doClose;
    }

    /**
     * It returns the URL.
     *
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * It returns the HTTP method.
     *
     * @return Method.
     */
    public Method getMethod() {
        return method;
    }
}
