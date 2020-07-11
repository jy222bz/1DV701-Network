package lnu.assignment2.server;

import lnu.assignment2.static_data.Tags;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The TCP Echo Server class.
 * It extends the server class.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class WebServer extends Server {

    /**
     * A private field represents the server socket.
     */
    private ServerSocket serverSocket;

    /**
     * Constructor, to construct an object.
     *
     * @param args the port number and the path of the directory.
     */
    public WebServer(String[] args) {
        super(args);
    }

    /**
     * The starts method.
     * The thread will be running unless the thread gets terminated or some thing goes foul.
     */
    protected void run() {
        boolean isRunning = true;
        initValues();
        while (isRunning) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket, getSharedFolder());
                client.start();
            } catch (Exception e) {
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
            serverSocket = new ServerSocket(getPort());
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
