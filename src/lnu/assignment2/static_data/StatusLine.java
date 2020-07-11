package lnu.assignment2.static_data;

import java.util.HashMap;

/**
 * A class for the HTTP status line.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public class StatusLine {

    /**
     * A private field for the hash map list.
     */
    private final HashMap<Integer, String> container;

    /**
     * A constructor to construct the object.
     */
    public StatusLine() {
        container = new HashMap<>();
        container.put(200, "HTTP/1.1 200 OK");
        container.put(201, "HTTP/1.1 201 CREATED");
        container.put(204, "HTTP/1.1 204 NO CONTENT");
        container.put(302, "HTTP/1.1 302 FOUND");
        container.put(400, "HTTP/1.1 400 BAD REQUEST");
        container.put(403, "HTTP/1.1 403 FORBIDDEN");
        container.put(404, "HTTP/1.1 404 NOT FOUND");
        container.put(500, "HTTP/1.1 500 INTERNAL SERVER ERROR");
        container.put(501, "HTTP/1.1 501 NOT IMPLEMENTED");
    }

    /**
     * It return the status line.
     * @param code the code of the response.
     * @return String
     */
    public String getStatusLine(int code) {
        return container.get(code);
    }
}
