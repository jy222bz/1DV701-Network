package lnu.assignment1.tcp;

import lnu.assignment1.server.Server;
import lnu.assignment1.tags.Tags;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The TCP Echo Server class.
 * It extends the server class.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class TCPEchoServer extends Server {
    /**
     * A private field represents the server socket.
     */
    private ServerSocket serverSocket;

    /**
     * A private field for the identification for the thread.
     */
    private int id;

    /**
     * Constructor, to construct an object.
     *
     * @param args the buffer size.
     */
    public TCPEchoServer(String[] args) {
        super(args);
    }

    /**
     * The starts method.
     * The thread will be running unless the thread gets terminated or some thing goes foul.
     */
    @Override
    protected void run() {
        boolean isRunning = true;
        initValues();
        while (isRunning) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, ++id, getBufferSize());
                clientHandler.start();
            } catch (IOException e) {
                log(e.toString());
                closeConnection();
                isRunning = false;
            }
        }
        terminateProgram(Tags.SHUT_DOWN_ERROR);
    }


    /**
     * It initializes the socket.
     */
    private void initValues() {
        try {
            serverSocket = new ServerSocket(Tags.SERVER_PORT);
        } catch (IOException e) {
            log(e.toString());
            terminateProgram(Tags.SHUT_DOWN_ERROR);
        }
    }

    /**
     * It closes the connection.
     */
    public void closeConnection() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                log(e.toString());
                terminateProgram(Tags.SHUT_DOWN_ERROR);
            }
        }
    }
}
