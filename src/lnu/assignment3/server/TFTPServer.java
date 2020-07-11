package lnu.assignment3.server;

import lnu.assignment3.exceptions.BaseException;
import lnu.assignment3.resource.DirectoryManager;
import lnu.assignment3.models.Parser;
import lnu.assignment3.models.Request;
import lnu.assignment3.models.RequestDeterminer;
import lnu.assignment3.tags.Tags;

import java.io.IOException;
import java.net.*;

/**
 * A class for the Trivial File Transfer Protocol server and it extends the Server class.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-03-07
 */
public class TFTPServer extends Server {

    /**
     * A private field for the directory manager.
     */
    private DirectoryManager directoryManager;

    /**
     * A private field for the socket.
     */
    private DatagramSocket socket;

    /**
     * A private field for the request determiner.
     */
    private RequestDeterminer requestDeterminer;

    /**
     * A private field for the parser.
     */
    private Parser parser;

    /**
     * Constructor, to construct an object.
     *
     * @param args args should be empty.
     */
    public TFTPServer(String[] args) {
        super(args);
    }

    /**
     * The run method. It runs the connection and receives the packets.
     * Every packet it receives it handles it in a separate thread.
     */
    @Override
    protected void run() {
        boolean isServing = true;
        while (isServing) {
            try {
                InetSocketAddress remote = receive(socket, getBuffer());
                Request request = parseRequest(getBuffer());
                ServerThread serverThread = new ServerThread(remote, request,
                        parser.getFilenameAndMode(getBuffer()), directoryManager);
                serverThread.start();
            } catch (IOException e) {
                log(e.toString());
                isServing = false;
            }
        }
    }

    /**
     * Reads the first block of data, i.e., the request for an action such as the read or the write.
     *
     * @param socket socket to read from.
     * @param buf    where to store the read data.
     * @return socketAddress the socket address of the client.
     */
    private InetSocketAddress receive(DatagramSocket socket, byte[] buf) throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        return new InetSocketAddress(packet.getAddress(), packet.getPort());
    }

    /**
     * It initializes the values and the connection.
     *
     * @throws SocketException if socket error occurs.
     */
    @Override
    protected void initConnection() throws SocketException, BaseException {
        directoryManager = new DirectoryManager(Tags.READ_DIRECTORY_PATH, Tags.WRITE_DIRECTORY_PATH);
        requestDeterminer = RequestDeterminer.getInstance();
        parser = Parser.getInstance();
        socket = new DatagramSocket(null);
        SocketAddress localBindPoint = new InetSocketAddress(getPort());
        socket.bind(localBindPoint);
    }

    /**
     * It parses the request.
     *
     * @param buf the buffer.
     * @return Request the client request.
     */
    private Request parseRequest(byte[] buf) {
        return requestDeterminer.getRequest(Parser.getOpcode(buf));
    }

    /**
     * It closes the socket.
     */
    @Override
    public void closeConnection() {
        socket.close();
    }
}
