package lnu.assignment2.server;

import lnu.assignment2.exceptions.BaseException;
import lnu.assignment2.resource.SharedFolder;
import lnu.assignment2.utility.Utility;
import lnu.assignment2.exceptions.IllegalArgumentsException;
import lnu.assignment2.static_data.Tags;
import lnu.assignment2.validators.Validator;

import java.io.File;

/**
 * The server abstract class, that can be extended by both, the UDP and TCP server classes.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public abstract class Server extends Utility {

    /**
     * A private field for the port.
     */
    private int port;

    /**
     * A private field for the arguments.
     */
    private final String[] arguments;

    /**
     * A private field for the file that is meant to be the main directory.
     */
    private SharedFolder sharedFolder;

    /**
     * Constructor, to construct an object.
     *
     * @param args the port number and the path of the directory.
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
        } catch (BaseException e) {
            log(e.toString());
            terminateProgram(Tags.SHUT_DOWN_ERROR);
        }
    }

    /**
     * It validate the argument and initializes it.
     *
     * @param args the arguments, the buffer size.
     * @throws IllegalArgumentsException, if the value is not valid.
     */
    private void init(String[] args) throws BaseException {
        Validator validator = Validator.getInstance();
        validator.validateArguments(args);
        port = validator.getNumber(args[0]);
        String path = args[1];
        if (path.endsWith(Tags.BACK_SLASH))
            path = path.substring(0, path.length() - 1);
        sharedFolder = new SharedFolder(new File(path));
    }

    /**
     * It returns the port number.
     *
     * @return int
     */
    public int getPort() {
        return port;
    }

    /**
     * It closes the connection.
     */
    public abstract void closeConnection();

    /**
     * It returns the shared folder.
     *
     * @return SharedFolder
     */
    public SharedFolder getSharedFolder() {
        return sharedFolder;
    }
}