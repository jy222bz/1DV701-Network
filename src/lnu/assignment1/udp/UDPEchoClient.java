package lnu.assignment1.udp;

import lnu.assignment1.server.Client;
import lnu.assignment1.exceptions.Classification;
import lnu.assignment1.exceptions.ExceptionMain;
import lnu.assignment1.tags.Tags;

import java.io.IOException;
import java.net.*;


/**
 * The UDP Echo Client class and it extends the base Client class.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class UDPEchoClient extends Client {

    /**
     * A private field for the echo message.
     */
    private String message = Tags.MESSAGE;

    /**
     * private field for the connection time.
     */
    private long connectionTime;

    /**
     * A private field for the message counter.
     */
    private int messageCounter;

    /**
     * The private field represents the remote socket address.
     */
    private SocketAddress remoteBindPoint;

    /**
     * The private field represents the socket.
     */
    private DatagramSocket socket;

    /**
     * A constructor, to construct the object.
     *
     * @param args the four arguments.
     */
    public UDPEchoClient(String[] args) {
        super(args);
    }

    /**
     * It will be called once the second is over.
     * It resets the values to re-run the same function for the next second.
     */
    @Override
    protected void resetValues() {
        connectionTime = getCurrentTime();
        messageCounter = 0;
    }


    /**
     * It establishes the connection between the client and the receiver.
     * If the rate if fulfilled before the one second elapses it will wait the reaming time of the second,
     * and then it reports and resets the values to starts the next second as new rate run.
     * If the second is over and the rate is not reached, it will report, reset the values and start the next second.
     * If the rate is zero, it will run one time.
     *
     * @throws IOException,          if an IO occurs.
     * @throws InterruptedException, if the sleep gets interrupted.
     * @throws ExceptionMain,        if the size of the message is invalid.
     */
    @Override
    public void run() throws IOException, InterruptedException, ExceptionMain {
        init();
        long startTime = getCurrentTime();
        connectionTime = getCurrentTime();
        do {
            messageCounter++;
            DatagramPacket sendPacket = new DatagramPacket(getMessage().getBytes(), getMessage().length(),
                    remoteBindPoint);
            DatagramPacket receivePacket = new DatagramPacket(getBuffer(), getBuffer().length);
            socket.send(sendPacket);
            socket.receive(receivePacket);
            String received = new String(receivePacket.getData(),
                    receivePacket.getOffset(),
                    receivePacket.getLength());
            report(received, getMessage(), receivePacket.getLength(), Tags.UDP_VERSION);
            setMessage(received);
            checkStatus(messageCounter, connectionTime);
        } while (isInfinite() || getElapsedTime(startTime) < getRunningTime() && getRate() > 0);
    }


    /**
     * It closes the socket and terminates the program.
     */
    @Override
    protected void closeConnection() {
        if (socket != null)
            socket.close();
    }

    /**
     * It logs the amount of the messages that actually have been sent
     * under one second and the remaining amount of messages.
     */
    @Override
    protected void reportTransferResult(int sentMessages) {
        log(Tags.UDP_VERSION + " The amount of the sent messages is: " + sentMessages + ". The remaining is: "
                + (getRate() - sentMessages));
    }

    /**
     * It returns the echo message.
     *
     * @return String
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * It sets the message.
     *
     * @param message the message to be sent to the server.
     * @throws ExceptionMain, if the message size is invalid.
     */
    @Override
    public void setMessage(String message) throws ExceptionMain {
        if (isPackageInvalid(message))
            throw new ExceptionMain(Classification.PACKET_SIZE_INVALID);
        this.message = message;
    }

    /**
     * It initializes the socket and the local bind point.
     * The socket will be set to time out, in case it does not connect.
     *
     * @throws SocketException, if a Socket error occurs.
     */
    private void init() throws SocketException {
        socket = new DatagramSocket(null);
        SocketAddress localBindPoint = new InetSocketAddress(Tags.CLIENT_PORT);
        socket.bind(localBindPoint);
        remoteBindPoint = new InetSocketAddress(getIPAddress(), getPort());
        socket.setSoTimeout(5000);
        checkPacketSize();
    }

    /**
     * It checks whether the message length is greater than the buffer size.
     * If is it is than it re-set the buffer size.
     */
    private void checkPacketSize() {
        if (getMessage().getBytes().length > getBufferSize())
            setBuffer(getMessage().length());
    }

    /**
     * It checks if the packet size is invalid.
     * According to UDP the packet the maximum size is 16-bit value.
     * 16-bit value is 65535 byte and after subtracting 8 byte for the header and 20 byte for the ip header,
     * the remaining will be 65507.
     * The packet size should make sense, i.e. it should not be empty, in order to echo it.
     *
     * @param packet the packet that is to be checked.
     * @return boolean
     */
    private boolean isPackageInvalid(String packet) {
        return packet.getBytes().length > 65507 || packet.isEmpty();
    }
}