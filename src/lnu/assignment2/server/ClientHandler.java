package lnu.assignment2.server;

import lnu.assignment2.exceptions.BaseException;
import lnu.assignment2.exceptions.EmptyLineException;
import lnu.assignment2.http.*;
import lnu.assignment2.resource.SharedFolder;
import lnu.assignment2.static_data.Tags;
import lnu.assignment2.utility.Utility;

import java.io.*;
import java.net.Socket;

/**
 * The Handler class extends the Thread class.
 * It designed to handle each client connection separately as its own thread.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class ClientHandler extends Thread {

    /**
     * A private field for the helper.
     */
    private Utility helper;

    /**
     * A private field for the http response.
     */
    private HTTPResponseWriter httpResponseWriter;

    /**
     * A private field for the socket.
     */
    private final Socket socket;

    /**
     * A private field for the shared folder.
     */
    private final SharedFolder sharedFolder;

    /**
     * A private field for the output stream.
     */
    private DataOutputStream outputStream;

    /**
     * A private field for the input stream.
     */
    private DataInputStream inputStream;

    /**
     * A private field for the buffer.
     */
    private byte[] buffer;

    /**
     * A private field for the request reader.
     */
    private HTTPRequestReader httpRequestReader;

    /**
     * A constructor, to construct an object.
     *
     * @param socket       the socket.
     * @param sharedFolder the shared directory.
     */
    public ClientHandler(Socket socket, SharedFolder sharedFolder) {
        this.socket = socket;
        this.sharedFolder = sharedFolder;
    }

    /**
     * It runs the thread to echo back the messages.
     * The thread and the connection will close once the host stops sending and closes its connection.
     */
    @Override
    public void run() {
        try {
            init();
            runConnection();
        } catch (IOException e) {
            helper.log(e.toString());
        }
        closeConnection();
    }

    /**
     * It runs the connection.
     * If the client request for has one of the following errors: File not found, Unauthorized access
     * it will send a respond to the client and then it closes the connection.
     * If the client sends an empty line, the connection will be closed.
     */
    private void runConnection() throws IOException {
        boolean isAlive = true;
        try {
            while (socket.isBound() && !socket.isClosed() && isAlive) {
                Request request = httpRequestReader.getRequest(inputStream);
                HTTPResponse httpResponse = new HTTPResponse(sharedFolder, request);
                httpResponseWriter.write(httpResponse.getCode(), httpResponse.getFile(), httpResponse.getDataType(),
                        outputStream, buffer, httpResponse.getDataSize());
                if (httpResponse.shouldClose()) isAlive = false;
            }
        } catch (EmptyLineException exception) {
            closeConnection();
        } catch (BaseException | IOException exception) {
            httpResponseWriter.writeError(outputStream, buffer, exception);
        }
    }

    /**
     * It initializes the values.
     * The path does not have to validated since the server have validated and passed it on
     * but it just to adopt a better practice, the method will validate it again.
     */
    private void init() throws IOException {
        helper = new Utility();
        httpResponseWriter = new HTTPResponseWriter();
        setBuffer();
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());
        httpRequestReader = new HTTPRequestReader();
    }

    /**
     * It sets the buffer.
     */
    private void setBuffer() {
        try {
            buffer = new byte[Tags.DEFAULT_BUFFER_SIZE];
        } catch (OutOfMemoryError e) {
            helper.log(e.toString());
        }
    }

    /**
     * It closes the IO streams and the socket.
     */
    private void closeConnection() {
        try {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            helper.log(e.toString());
        }
    }
}