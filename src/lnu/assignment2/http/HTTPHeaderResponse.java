package lnu.assignment2.http;

import lnu.assignment2.static_data.StatusLine;
import lnu.assignment2.static_data.Tags;

import java.util.Date;

/**
 * A class for the HTTP responses.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public class HTTPHeaderResponse {

    /**
     * A private constructor to prevent creating instances.
     */
    private HTTPHeaderResponse() {
    }

    /**
     * It returns the response, status line, header and info about the data.
     *
     * @param dataType the type of the content.
     * @return String
     */
    public static String getHeader(int code, String dataType, long length) {
        StatusLine statusLine = new StatusLine();
        return statusLine.getStatusLine(code) + Tags.CRLF +
                "Content-Length: " + length + Tags.CRLF +
                "Content-Type: " + dataType + Tags.CRLF +
                "Time: " + new Date(System.currentTimeMillis()) + Tags.CRLF  + Tags.CRLF;
    }
}
