package lnu.assignment1.tcp;


import lnu.assignment1.server.Client;
import lnu.assignment1.exceptions.Classification;
import lnu.assignment1.exceptions.ExceptionMain;
import lnu.assignment1.tags.Tags;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * A TCP Echo Client class and it extends the base client class.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class TCPEchoClient extends Client {

    /**
     * A private field for the echo message.
     */
    private String message = Tags.MESSAGE;

    /**
     * A private field for the message counter;
     */
    private int messageCounter;

    /**
     * A private field for the connection time;
     */
    private long connectionTime;

    /**
     * A private field represents the socket.
     */
    private Socket socket;

    /**
     * A private field represents the input stream.
     */
    private InputStream inputStream;

    /**
     * A private field for the output stream.
     */
    private OutputStream outputStream;

    /**
     * A constructor, to construct an object.
     *
     * @param args the arguments.
     */
    public TCPEchoClient(String[] args) {
        super(args);
    }

    /**
     * It will be called once the second is over.
     * It will close the current connection.
     * It resets the values to re-run the same function for the next second.
     *
     * @throws IOException, if an IO error occurs.
     */
    @Override
    protected void resetValues() throws IOException {
        inputStream.close();
        outputStream.close();
        closeConnection();
        init();
        connectionTime = getCurrentTime();
        messageCounter = 0;
    }

    /**
     * It initializes the values.
     * The socket, the input- and the output- stream.
     * It catches an exception, in case the server is not connecting.
     * In that case it terminates the program.
     *
     * @throws IOException, if an IO error occurs.
     */
    private void init() throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(getIPAddress(), getPort()));
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * It runs the connection.
     * The loop is for receiving the sub-strings, until it collects all data.
     * If the rate zero, it will run one single time.
     * When the rate is greater than zero, it will run for a second
     * if the rate is reached before the seconds elapses, it will will wait till the second over
     * afterwards, it reports the result, closes the connection with the current thread
     * and starts a new connection with the server for the new second.
     *
     * @throws IOException,          if IO occurs.
     * @throws InterruptedException, if an interrupt occurs.
     * @throws ExceptionMain,        if the size of the message is invalid.
     */
    @Override
    protected void run() throws IOException, InterruptedException, ExceptionMain {
        init();
        long startTime = getCurrentTime();
        connectionTime = getCurrentTime();
        do {
            outputStream.write(getMessage().getBytes(), 0, getMessage().length());
            StringBuilder sb = new StringBuilder();
            ++messageCounter;
            do {
                initBuffer();
                int br = inputStream.read(getBuffer(), 0, getBuffer().length);
                if (br > 0) sb.append(new String(getBuffer(), 0, br));
                else break;
            } while (inputStream.available() > 0);
            report(sb.toString(), getMessage(), sb.toString().getBytes().length, Tags.TCP_VERSION);
            setMessage(sb.toString());
            checkStatus(messageCounter, connectionTime);
        } while (isInfinite() || getElapsedTime(startTime) < getRunningTime() && getRate() > 0);
    }

    /**
     * It closes the connection.
     */
    @Override
    public void closeConnection() {
        if (socket != null){
            try {
                if (!socket.isClosed())
                    socket.close();
            } catch (IOException e) {
                terminateProgram(Tags.SHUT_DOWN_ERROR);
            }
        }
    }

    /**
     * It logs the amount of the messages that actually have been sent
     * under one second and the remaining amount of messages.
     */
    @Override
    protected void reportTransferResult(int sentMessages) {
        log(Tags.TCP_VERSION + " The amount of the sent messages is: " + sentMessages + ". The remaining is: "
                + (sentMessages - getRate()));
    }

    /**
     * It returns the echo message.
     *
     * @return String
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * It sets the message.
     *
     * @param message the message to be sent to the server.
     * @throws ExceptionMain, if the message is empty.
     */
    @Override
    public void setMessage(String message) throws ExceptionMain {
        if (message.isEmpty())
            throw new ExceptionMain(Classification.MESSAGE_EMPTY);
        this.message = message;
    }
}
