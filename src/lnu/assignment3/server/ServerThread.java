package lnu.assignment3.server;

import lnu.assignment3.resource.DirectoryManager;
import lnu.assignment3.models.Request;
import lnu.assignment3.models.RequestManager;
import lnu.assignment3.tags.Tags;
import lnu.assignment3.utility.Utility;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * A class that handles the client request in a separated thread.
 * It extends the Thread class.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class ServerThread extends Thread {

    /**
     * A private field for the socket.
     */
    private DatagramSocket sendSocket;

    /**
     * A private field for the socket address.
     */
    private final InetSocketAddress address;

    /**
     * A private field for the request.
     */
    private final Request request;

    /**
     * A private field for the string array.
     */
    private final String[] filenameAndMode;

    /**
     * A private field for the logger.
     */
    private Utility logger;

    /**
     * A private field for the request manager.
     */
    private RequestManager requestManager;

    /**
     * A private field for the directory manager.
     */
    private final DirectoryManager directoryManager;

    /**
     * A constructor to construct an object.
     *
     * @param address          the client address.
     * @param request          the request.
     * @param filenameAndMode  the data; file name and mode.
     * @param directoryManager the directory manager.
     */
    public ServerThread(InetSocketAddress address, Request request, String[] filenameAndMode,
                        DirectoryManager directoryManager) {
        this.address = address;
        this.request = request;
        this.filenameAndMode = filenameAndMode;
        this.directoryManager = directoryManager;
    }

    /**
     * The run method.
     * It calls the request manager and the request manager manages the request.
     */
    @Override
    public void run() {
        try {
            init();
            requestManager.handleRequest(request, filenameAndMode);
        } catch (Exception e) {
            logger.log(e.toString());
        }
        closeConnection();
    }

    /**
     * It initializes the values.
     */
    private void init() throws SocketException {
        logger = new Utility();
        sendSocket = new DatagramSocket(Tags.VALUE_OF_ZERO);
        sendSocket.connect(address);
        requestManager = new RequestManager(directoryManager, sendSocket, Tags.DEFAULT_BUFFER_SIZE,
                address.getAddress().getHostAddress());
    }

    /**
     * It closes the socket after executing the request.
     */
    private void closeConnection() {
        sendSocket.close();
    }
}
