package lnu.assignment1.server;

import lnu.assignment1.utility.Utility;
import lnu.assignment1.exceptions.ExceptionMain;
import lnu.assignment1.tags.Tags;
import lnu.assignment1.validators.BufferValidator;
import lnu.assignment1.validators.Validator;

/**
 * The server abstract class, that can be extended by both, the UDP and TCP server classes.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public abstract class Server extends Utility {

    /**
     * A private field for the default buffer size.
     */
    private int bufferSize;

    /**
     * A private field for the buffer.
     */
    private byte[] buffer;

    /**
     * A private field for the arguments.
     */
    private final String[] arguments;


    /**
     * Constructor, to construct an object.
     *
     * @param args the buffer size.
     */
    public Server(String[] args) {
        arguments = args;
    }

    /**
     * It runs the connection.
     */
    protected abstract void run();

    /**
     * The start method, where the server starts its connection.
     */
    public void start() {
        try {
            init(arguments);
            run();
        } catch (ExceptionMain exceptionMain) {
            log(exceptionMain.toString());
            terminateProgram(Tags.SHUT_DOWN_ERROR);
        }
    }

    /**
     * It sets the size of the buffer and the buffer.
     * If the values of the buffer size is invalid, it will terminate the program.
     *
     * @param bufferSize the size of the buffer.
     */
    public void setBuffer(int bufferSize) {
        buffer = BufferValidator.getBuffer(bufferSize);
        if (buffer.length == 0) {
            closeConnection();
            terminateProgram(Tags.SHUT_DOWN_ERROR);
        } else
            this.bufferSize = buffer.length;
    }

    /**
     * It validate the argument and initializes it.
     *
     * @param args the arguments, the buffer size.
     * @throws ExceptionMain, if the value is not valid.
     */
    private void init(String[] args) throws ExceptionMain {
        Validator validator = new Validator();
        validator.validateArgument(args);
        setBuffer(validator.getNumber(args[0]));
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
     * It returns the buffer size.
     *
     * @return int
     */
    public int getBufferSize() {
        return bufferSize;
    }

    /**
     * It closes the connection.
     */
    public abstract void closeConnection();
}
