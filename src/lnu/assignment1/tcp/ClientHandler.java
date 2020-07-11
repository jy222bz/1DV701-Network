package lnu.assignment1.tcp;


import lnu.assignment1.utility.Utility;
import lnu.assignment1.tags.Tags;
import lnu.assignment1.validators.BufferValidator;

import java.io.*;
import java.net.Socket;


/**
 * The TCP Echo Handler class, it extends the Thread class.
 * It designed to handle each client connection separately as its own thread.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class ClientHandler extends Thread {

    /**
     * A private field for the helper.
     */
    private final Utility helper;

    /**
     * A private field for the socket.
     */
    private final Socket socket;

    /**
     * A private field for the thread identification.
     */
    private final int id;

    /**
     * A private field for the input stream.
     */
    private InputStream inputStream;

    /**
     * A private field for the output stream.
     */
    private OutputStream outputStream;

    /**
     * A private field for the buffer size.
     */
    private final int bufferSize;

    /**
     * A private field for the buffer.
     */
    byte[] buffer;

    /**
     * A constructor, to construct an object.
     *
     * @param socket      the socket.
     * @param id          an identification for the thread.
     * @param bufferSize, the buffer size.
     */
    public ClientHandler(Socket socket, int id, int bufferSize) {
        this.socket = socket;
        this.id = id;
        this.bufferSize = bufferSize;
        helper = new Utility();
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
            closeConnection();
        } catch (Exception e) {
            helper.log(e.toString());
            closeConnection();
        }
    }

    /**
     * It runs the connection.
     * It collects the sub-strings that has been streamed from the client.
     * It echoes it back.
     *
     * @throws IOException it throws an exception.
     */
    private void runConnection() throws IOException {
        do {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb1 = new StringBuilder();
            do {
                int br = inputStream.read(buffer, 0, buffer.length);
                if (br > 0) sb.append(new String(buffer, 0, br));
                else break;
            } while (inputStream.available() > 0);
            if (!sb.toString().isEmpty()) {
                sb1.append(Tags.THREAD_INITIAL_MESSAGE).append(id).append(Tags.ECHOING).append(sb.toString());
                helper.log(sb1.toString());
                outputStream.write(sb.toString().getBytes(), 0, sb.toString().length());
            } else {
                inputStream.close();
                outputStream.close();
                break;
            }
        } while (!socket.isClosed());
    }

    /**
     * It initializes the values.
     *
     * @throws IOException, if an IO error occurs.
     */
    private void init() throws IOException {
        buffer = BufferValidator.getBuffer(bufferSize);
        if (buffer.length == 0) {
            closeConnection();
            helper.terminateProgram(Tags.SHUT_DOWN_ERROR);
        }
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * It closes the connection.
     */
    private void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                helper.log(e.toString());
                helper.terminateProgram(Tags.SHUT_DOWN_ERROR);
            }
        }
    }
}
