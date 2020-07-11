package lnu.assignment3.models;

import lnu.assignment3.exceptions.BaseException;
import lnu.assignment3.resource.DirectoryManager;
import lnu.assignment3.tags.Tags;
import lnu.assignment3.utility.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

/**
 * A class that handles the requests.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class RequestManager extends Utility {

    /**
     * A private field for the directory manager.
     */
    private final DirectoryManager directoryManager;

    /**
     * A private filed for the data gram.
     */
    private final DatagramSocket datagramSocket;

    /**
     * A private field for the buffer.
     */
    private byte[] buffer;

    /**
     * A private field for the buffer size.
     */
    private final int bufferSize;

    /**
     * A private field for the current available amount of bytes that can be used for sending and receiving data.
     */
    private final int allowedDataSize;

    /**
     * A private field for parser.
     */
    private final Parser parser;

    /**
     * A private field for the client ip address.
     */
    private final String clientIpAddress;

    /**
     * A private field for the boolean, to check whether is still transmitting.
     */
    private boolean isTransmitting;


    /**
     * Constructor, to construct an object.
     *
     * @param directoryManager the directory manager, i.e. manages the write and the read directories.
     * @param datagramSocket   the data gram for the send.
     * @param bufferSize       the size of the buffer.
     * @param clientIpAddress  the client ip address.
     */
    public RequestManager(DirectoryManager directoryManager, DatagramSocket datagramSocket, int bufferSize,
                          String clientIpAddress) {
        this.directoryManager = directoryManager;
        this.datagramSocket = datagramSocket;
        this.bufferSize = bufferSize;
        this.allowedDataSize = bufferSize - Tags.HEADER_SIZE;
        this.clientIpAddress = clientIpAddress;
        parser = Parser.getInstance();
    }

    /**
     * It handles the client requests; i.e. read or write request.
     * If the mode is not OCTET, it sends an error.
     * The requirements of the assignments allows using the NO-SUCH-USER-ERROR message
     * for error that related to IO, that implies this methods follows this principle.
     *
     * @param request         the client request.
     * @param fileNameAndMode the file name and the mode.
     */
    public void handleRequest(Request request, String[] fileNameAndMode) {
        try {
            if (!fileNameAndMode[1].equalsIgnoreCase(Tags.OCTET))
                handleError(ErrorType.INVALID_MODE);
            else if (request == Request.OPERATION_READ_REQUEST)
                handleRead(fileNameAndMode[0]);
            else if (request == Request.OPERATION_WRITE_REQUEST)
                handleWrite(fileNameAndMode[0]);
            else
                handleError(ErrorType.ILLEGAL_OPERATION);
        } catch (BaseException e) {
            log(e.toString());
        } catch (IOException e) {
            handleError(ErrorType.NO_SUCH_USER);
        }
    }

    /**
     * It handles the read request.
     * It sends an error when the requested file either does not exist or cannot be accessed.
     *
     * @param fileName the name of the file that is requested by the client.
     * @throws IOException   if an IO error occurs.
     * @throws BaseException if the the buffer size exceeds the memory capacity.
     */
    private void handleRead(String fileName) throws IOException, BaseException {
        if (!directoryManager.doesFileExist(fileName, directoryManager.getReadDirectory()))
            handleError(ErrorType.FILE_NOT_FOUND);
        if (!directoryManager.isPermittedFile(fileName, directoryManager.getReadDirectory()))
            handleError(ErrorType.ACCESS_VIOLATION);
        else
            sendFileAndAcknowledge(directoryManager.getFile(fileName, directoryManager.getReadDirectory()));
    }

    /**
     * It handles the write request.
     * It send an error if the file already exists.
     *
     * @param fileName the file name that is being uploaded by the client.
     * @throws IOException   if an IO error occurs.
     * @throws BaseException if the the buffer size exceeds the memory capacity.
     */
    private void handleWrite(String fileName) throws IOException, BaseException {
        if (directoryManager.doesFileExist(fileName, directoryManager.getWriteDirectory()))
            handleError(ErrorType.FILE_EXISTS);
        else
            receiveFileAndAcknowledge(directoryManager.getWriteDirectoryPath() + fileName);
    }

    /**
     * It sends the file to the client and sends acknowledgment along the process.
     * The file will be transmitted as segments. When the client does not acknowledge
     * the transmission, the segment will be transmitted again tills they receive it or
     * the maximum amount reached.
     * If the maximum amount is reached then an error will be sent and the connection will be closed.
     * If any error occurs it will send an error message and the connection closes afterwards.
     * When the client ip address differs it send an error message - Unknown ID.
     * The file input stream will be closed automatically by the try block.
     *
     * @param file the file to be sent.
     * @throws IOException   if an IO error occurs.
     * @throws BaseException if the the buffer size exceeds the memory capacity.
     */
    private void sendFileAndAcknowledge(File file) throws IOException, BaseException {
        buffer = setupBuffer(allowedDataSize);
        int blockNumber = 0;
        isTransmitting = true;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            while (isTransmitting) {
                fillBuffer(Tags.VALUE_OF_ZERO, buffer);
                int br = fileInputStream.read(buffer);
                byte[] responseData = parser.getByte(Request.OPERATION_DATA, ++blockNumber, buffer, bufferSize, true);
                sendSegment(responseData, (br + Tags.HEADER_SIZE), blockNumber);
                if (br < allowedDataSize) isTransmitting = false;
            }
        }
    }

    /**
     * It sends a segment of data.
     * If the user fails to receive the data from the first time it re-transmits it again.
     * The maximum amount of re-transmissions is 5.
     *
     * @param responseData the data segment.
     * @param br           the byte read.
     * @param blockNumber  the block number.
     * @throws IOException if an IO error occurs.
     */
    private void sendSegment(byte[] responseData, int br, int blockNumber) throws IOException {
        int retransmissionCounter = 0;
        Request request = Request.INITIAL;
        boolean isOperational = true;
        do {
            try {
                datagramSocket.setSoTimeout(Tags.TIME_OUT);
                DatagramPacket packet = new DatagramPacket(responseData, br);
                datagramSocket.send(packet);
                request = parser.getRequest(receiveAcknowledgment(), blockNumber);
                ErrorType errorType = ControlManager.getErrorStateDuringSending(packet, clientIpAddress);
                if (errorType != ErrorType.NO_ERROR) {
                    handleError(errorType);
                    isOperational = false;
                    isTransmitting = false;
                } else if (request == Request.OPERATION_ACKNOWLEDGMENT_REQUEST) break;
            } catch (SocketTimeoutException ignored) {
                retransmissionCounter++;
                if (retransmissionCounter == Tags.MAXIMUM_RETRANSMISSIONS) {
                    handleError(ErrorType.TRANSMISSION_ERROR);
                    isOperational = false;
                    isTransmitting = false;
                }
            }
        } while (isOperational && request != Request.OPERATION_ERROR);
        if (request == Request.OPERATION_ERROR) isTransmitting = false;
    }

    /**
     * It receives the file and sends acknowledgment along the process.
     * It loops tills it get the file.
     * In case when the file exceeds the disk free space then it sends an error message.
     * When the client ip address or port number differs it send an error message - Unknown ID.
     * The file output stream will be closed automatically by the try block.
     * If the time-out runs out it will try again to receive the data.
     * The maximum amount of data is five times.
     *
     * @param fileName the filename.
     * @throws IOException   if an IO error occurs.
     * @throws BaseException if the the buffer size exceeds the memory capacity.
     */
    private void receiveFileAndAcknowledge(String fileName) throws IOException, BaseException {
        buffer = setupBuffer(bufferSize);
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            sendMessage(Request.OPERATION_ACKNOWLEDGMENT_REQUEST, Tags.VALUE_OF_ZERO);
            isTransmitting = true;
            ErrorType errorType;
            int retransmissionCounter = 0;
            do {
                fillBuffer(Tags.VALUE_OF_ZERO, buffer);
                DatagramPacket packet = new DatagramPacket(buffer, bufferSize);
                try {
                    datagramSocket.setSoTimeout(Tags.TIME_OUT);
                    datagramSocket.receive(packet);
                    errorType = ControlManager.getErrorStateDuringReceiving(packet, directoryManager, clientIpAddress);
                    if (errorType == ErrorType.NO_ERROR) {
                        fileOutputStream.write(Arrays.copyOfRange(packet.getData(), Tags.DATA_INDEX, packet.getLength()));
                        fileOutputStream.flush();
                        sendMessage(Request.OPERATION_ACKNOWLEDGMENT_REQUEST, parser.getValues(packet.getData())[1]);
                    } else if (errorType != null && errorType != ErrorType.ERROR_CLOSE_CONNECTION) {
                        handleError(errorType);
                        isTransmitting = false;
                    } else if (errorType == ErrorType.ERROR_CLOSE_CONNECTION)
                        isTransmitting = false;
                } catch (SocketTimeoutException ignored) {
                    ++retransmissionCounter;
                    if (retransmissionCounter == Tags.MAXIMUM_RETRANSMISSIONS) {
                        sendMessage(Request.OPERATION_ERROR, ErrorType.TRANSMISSION_ERROR.getCode());
                        break;
                    }
                }
                if (packet.getData().length < allowedDataSize) isTransmitting = false;
            } while (isTransmitting);
        }
    }

    /**
     * It sends an error message to the client.
     * The error message is according to RFC, that is:
     * 05    |  ErrorCode |   ErrMsg   |   0
     *
     * @param errorType the error type.
     */
    private void handleError(ErrorType errorType) {
        StringBuilder sb = new StringBuilder();
        sb.append(errorType.getDescription());
        byte[] errorResponse = parser.getByte(Request.OPERATION_ERROR, errorType.getCode(), sb.toString().getBytes(),
                (sb.toString().length() + Tags.HEADER_SIZE), true);
        try {
            datagramSocket.send(new DatagramPacket(errorResponse, errorResponse.length));
        } catch (IOException e) {
            log(e.toString());
        }
    }

    /**
     * It sends a message or an acknowledgment to the client.
     *
     * @param request the request to be sent to the client.
     * @param info    it could be the block number or the error type.
     * @throws IOException if an IO error occurs.
     */
    private void sendMessage(Request request, int info) throws IOException {
        byte[] acknowledgmentResponse = parser.getByte(request, info, buffer,
                Tags.HEADER_SIZE, false);
        datagramSocket.send(new DatagramPacket(acknowledgmentResponse, acknowledgmentResponse.length));
    }

    /**
     * It receives and wraps the acknowledgment.
     *
     * @return int[] the opcode and the block number.
     * @throws IOException if IO an error occurs.
     */
    private int[] receiveAcknowledgment() throws IOException {
        byte[] buf = new byte[Tags.HEADER_SIZE];
        datagramSocket.receive(new DatagramPacket(buf, buf.length));
        return parser.getValues(buf);
    }
}
