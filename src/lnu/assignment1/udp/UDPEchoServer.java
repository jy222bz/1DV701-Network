package lnu.assignment1.udp;



import lnu.assignment1.server.Server;
import lnu.assignment1.tags.Tags;

import java.net.*;


/**
 * The UDP Echo Server class.
 * It extends the server class.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class UDPEchoServer extends Server {

    /**
     * A private field, for the datagram socket.
     */
    private DatagramSocket socket;

    /**
     * Constructor, to construct an object.
     *
     * @param args the buffer size.
     */
    public UDPEchoServer(String[] args) {
        super(args);
    }

    /**
     * The starts method.
     * The thread will be running unless the thread gets terminated or some thing goes foul.
     */
    @Override
    protected void run() {
        initValues();
        boolean isRunning = true;
        while (isRunning) {
            DatagramPacket receivePacket = new DatagramPacket(getBuffer(), getBuffer().length);
            try {
                socket.receive(receivePacket);
                DatagramPacket sendPacket =
                        new DatagramPacket(receivePacket.getData(),
                                receivePacket.getLength(),
                                receivePacket.getAddress(),
                                receivePacket.getPort());
                socket.send(sendPacket);
            } catch (Exception e) {
                log(e.toString());
                closeConnection();
                isRunning = false;
            }
            log("UDP echo request from  " + receivePacket.getAddress().getHostAddress() +
                    " using port \n" + receivePacket.getPort());
        }
        terminateProgram(Tags.SHUT_DOWN_ERROR);
    }

    /**
     * It closes the connection.
     */
    @Override
    public void closeConnection() {
        if (socket != null)
            socket.close();
    }

    /**
     * It initializes the socket and local bind point.
     */
    private void initValues() {
        try {
            socket = new DatagramSocket(null);
            SocketAddress localBindPoint = new InetSocketAddress(Tags.SERVER_PORT);
            socket.bind(localBindPoint);
        } catch (SocketException e) {
            log(e.toString());
            terminateProgram(Tags.SHUT_DOWN_ERROR);
        }
    }
}
