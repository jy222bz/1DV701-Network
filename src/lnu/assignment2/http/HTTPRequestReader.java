package lnu.assignment2.http;

import lnu.assignment2.exceptions.*;
import lnu.assignment2.static_data.Method;
import lnu.assignment2.static_data.Tags;
import lnu.assignment2.validators.MethodValidator;

import java.io.*;

/**
 * A class to read the HTTP Request..
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public class HTTPRequestReader {
    /**
     * A private field for the boolean, whether the connection should kept alive.
     */
    private boolean terminate;

    /**
     * A private field for the method.
     */
    private Method method;

    /**
     * A private field for the input stream.
     */
    private InputStream inputStream;

    /**
     * A private field for the request line.
     */
    private String[] requestLine;

    /**
     * It reads the data and parses it.
     *
     * @param inputStream input stream.
     * @return Request.
     * @throws IOException, If an IO error occurs.
     */
    public Request getRequest(InputStream inputStream) throws IOException, BaseException {
        this.inputStream = inputStream;
        parseHeaders();
        return new Request(requestLine, terminate, method);
    }


    /**
     * It parses the request one line after another and it collects the important info.
     * Since only a GET method implemented, only one thing is collected which is whether the connection
     * should closed or not.
     */
    private void parseHeaders() throws BaseException, IOException {
        String[] lines = processLines();
        requestLine = readRequestLine(lines[0]);
        for (int index = 1; index < lines.length; index++) {
            if (lines[index].endsWith(Tags.CLOSE)) {
                terminate = true;
                break;
            }
        }
    }

    /**
     * It reads the lines and returns String array.
     *
     * @return String[]
     * @throws IOException, IF an IO error occurs.
     */
    private String[] processLines() throws IOException {
        StringBuilder data = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty())
                break;
            data.append(line).append(Tags.CRLF);
        }
        return data.toString().split(Tags.CRLF);
    }


    /**
     * It reads the first line which is the request line and returns it as String array.
     *
     * @return String[] the status line.
     * @throws BaseException, If the request is invalid, request line is nul, incorrect amount of argument or
     *                        unsupported HTTP version.
     */
    private String[] readRequestLine(String line) throws BaseException {
        BaseException baseException;
        if (line.isEmpty()) {
            baseException = new EmptyLineException(Classification.EMPTY_LINE);
            throw baseException;
        }
        String[] parts = line.split(Tags.WHITE_SPACE);
        if (parts.length != 3 || !parts[2].equals(Tags.HTTP_VERSION)) {
            baseException = new BadRequestException(Classification.BAD_REQUEST);
            throw baseException;
        }
        this.method = MethodValidator.getMethod(parts[0]);
        return parts;
    }
}
