package lnu.assignment1.server;

import lnu.assignment1.utility.Utility;
import lnu.assignment1.exceptions.ExceptionMain;
import lnu.assignment1.tags.Tags;
import lnu.assignment1.validators.BufferValidator;
import lnu.assignment1.validators.Validator;

import java.io.IOException;

/**
 * It is an abstract class that is the base class for the other client classes: UDP and TCP echo client classes.
 * Since both UDP and TCP have common arguments and some common functions, the standard functions will be in this class.
 * It has abstract methods, to allow the child classes implement them according to their objectives.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public abstract class Client extends Utility {

    /**
     * A private field for the arguments.
     */
    private final String[] arguments;

    /**
     * A private field for the ip address.
     */
    private String ip;

    /**
     * A private field for the port.
     */
    private int port;

    /**
     * A private field for the validator.
     */
    private final Validator validator;

    /**
     * A private field for the rate.
     */
    private int rate;

    /**
     * A private field for the running time.
     */
    private long runningTime;

    /**
     * A private field for the buffer size.
     */
    private int bufferSize;

    /**
     * A private field for the buffer.
     */
    private byte[] buffer;

    /**
     * A constructor, to construct the object.
     *
     * @param args the arguments.
     */
    public Client(String[] args) {
        arguments = args;
        validator = new Validator();
    }

    /**
     * It is a skeleton method, with default methods and one abstract method.
     * The abstract method should be implemented by the child class according to its objectives.
     * It validates the arguments and starts the run method.
     */
    public void start() {
        try {
            validator.validateArguments(arguments);
            initDefaultValues();
            run();
        } catch (Exception e) {
            log(e.toString());
            closeConnection();
            terminateProgram(Tags.SHUT_DOWN_ERROR);
        }
        closeConnection();
        terminateProgram(Tags.SHUT_DOWN);
    }

    /**
     * It checks whether the second elapsed or the rate is fulfilled.
     * If it is fulfilled, it will make the thread waits till the second is over.
     * When it reaches approximately one second, and the rate is not fulfilled it aborts and re-starts
     * the operation for the next second.
     * It applies only when the rate is not zero.
     *
     * @throws InterruptedException, if an interruption occurs.
     * @throws IOException,          if an IO error occurs.
     */
    public void checkStatus(int sentMessages, long connectionStartTime) throws InterruptedException, IOException {
        if (getRate() != 0) {
            if (getElapsedTime(connectionStartTime) >= Tags.ONE_SECOND - 30) {
                reportTransferResult(sentMessages);
                resetValues();
            } else if (sentMessages == getRate()) {
                doWait(connectionStartTime);
                reportTransferResult(sentMessages);
                resetValues();
            }
        }
    }

    /**
     * It resets the values, to run the connection for the next second.
     */
    protected abstract void resetValues() throws IOException;


    /**
     * It runs the connection, to send and receive echo messages.
     * It will be implemented by the child class depends on its objective.
     *
     * @throws IOException,          if an IO exceptions occurs.
     * @throws ExceptionMain,        if the size of the received packet is invalid.
     * @throws InterruptedException, if an interruption occurs.
     */
    protected abstract void run() throws IOException, ExceptionMain, InterruptedException;


    /**
     * It initializes the default values that are essential for both UDP/TCP.
     *
     * @throws ExceptionMain, if the size of the message is not valid.
     */
    private void initDefaultValues() throws ExceptionMain {
        ip = arguments[0];
        port = validator.getNumber(arguments[1]);
        setMessage(Tags.MESSAGE);
        this.rate = validator.getNumber(arguments[2]);
        setBuffer(validator.getNumber(arguments[3]));
    }

    /**
     * This method is designed to delay the thread for the remaining time of one second.
     *
     * @param connectionStartTime, when the connection started.
     * @throws InterruptedException, it throws exception if an interruption occurs.
     */
    private void doWait(long connectionStartTime) throws InterruptedException {
        if (getElapsedTime(connectionStartTime) < Tags.ONE_SECOND)
            Thread.sleep(Tags.ONE_SECOND - getElapsedTime(connectionStartTime));
    }

    /**
     * It returns the IP address.
     *
     * @return String.
     */
    public String getIPAddress() {
        return ip;
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
     * It returns the rate.
     *
     * @return int
     */
    public int getRate() {
        return rate;
    }

    /**
     * It returns the buffer.
     *
     * @return byte[]
     */
    public byte[] getBuffer() {
        return buffer;
    }

    /**
     * It initializes the buffer.
     */
    public void initBuffer() {
        buffer = new byte[bufferSize];
    }

    public int getBufferSize() {
        return bufferSize;
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
     * It compares the strings and it logs accordingly.
     *
     * @param str     the received message.
     * @param str1    the sent message.
     * @param bytes   the size of the received bytes.
     * @param version the transport protocol.
     */
    public void report(String str, String str1, int bytes, String version) {
        if (str.compareTo(str1) == 0)
            log(version + " SENT: " + str1 + " RECEIVED: " + str + " The bytes sent and received are: "
                    + str1.getBytes().length + " bytes and " + bytes + " bytes.");
        else{
            log("The sent and received messages are not equal!");
            closeConnection();
            terminateProgram(Tags.MESSAGE_NOT_EQUAL_SHUT_DOWN);
        }
    }

    /**
     * It closes the socket.
     */
    protected abstract void closeConnection();


    /**
     * It logs the amount of the messages that actually have been sent
     * under one second and the remaining amount of messages.
     *
     * @param sentMessages the amount of the messages that has been sent under one second.
     */
    protected abstract void reportTransferResult(int sentMessages);

    /**
     * It returns the message to be sent to the server.
     *
     * @return String
     */
    public abstract String getMessage();

    /**
     * It sets the run time.
     *
     * @param runningTimeInSeconds the time in seconds.
     */
    public void setRunTime(int runningTimeInSeconds) {
        if (runningTimeInSeconds > 0) {
            runningTime = Tags.ONE_SECOND * runningTimeInSeconds;
        }
    }

    /**
     * Checks whether the running time is zero.
     * If it is not then the should run for the specific time.
     *
     * @return boolean
     */
    public boolean isInfinite() {
        return runningTime < 1 && getRate() != 0;
    }

    /**
     * It sets the message.
     *
     * @param message the message to be sent to the server.
     */
    public abstract void setMessage(String message) throws ExceptionMain;

    /**
     * It returns the running time.
     *
     * @return long
     */
    public long getRunningTime() {
        return runningTime;
    }
}