package lnu.assignment3.server;

import lnu.assignment3.exceptions.BaseException;
import lnu.assignment3.tags.Tags;
import lnu.assignment3.utility.Utility;
import lnu.assignment3.validators.Validator;

import java.net.SocketException;

/**
 * The abstract class for the server.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-03-07
 */
public abstract class Server extends Utility {

    /**
     * A private field for the arguments.
     */
    private final String[] arguments;

    /**
     * A private field for the port number of the server.
     */
    private static final int SERVER_PORT_NUMBER = Tags.SERVER_PORT_NUMBER;

    /**
     * A private field for the buffer.
     */
    private byte[] buffer;

    /**
     * Constructor, to construct an object.
     *
     * @param args should be empty.
     */
    public Server(String[] args) {
        arguments = args;
    }

    /**
     * It runs the connection.
     */
    protected abstract void run();

    /**
     * It runs the connection.
     */
    protected abstract void initConnection() throws SocketException, BaseException;

    /**
     * The start method, where the server starts its connection.
     */
    public void start() {
        try {
            init(arguments);
            run();
        } catch (BaseException | SocketException e) {
            log(e.toString());
        }
        closeConnection();
        terminateProgram(Tags.SHUT_DOWN_ERROR);
    }

    /**
     * It initializes the connection.
     *
     * @param args args should be empty.
     * @throws BaseException if there are arguments provided by the user or the buffer size is invalid.
     */
    private void init(String[] args) throws BaseException, SocketException {
        Validator validator = Validator.getInstance();
        validator.validateArguments(args);
        buffer = setupBuffer(Tags.DEFAULT_BUFFER_SIZE);
        initConnection();
    }

    /**
     * It returns the buffer.
     *
     * @return buffer
     */
    public byte[] getBuffer() {
        return buffer;
    }

    /**
     * It returns the port number.
     *
     * @return int
     */
    public int getPort() {
        return SERVER_PORT_NUMBER;
    }

    /**
     * It closes the connection.
     */
    public abstract void closeConnection();
}
