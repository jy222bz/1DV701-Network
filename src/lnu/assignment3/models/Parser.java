package lnu.assignment3.models;

import lnu.assignment3.tags.Tags;

import java.nio.ByteBuffer;

/**
 * A class for parsing the request.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-03-07
 */
public class Parser {

    /**
     * A private and static object for the Parse class.
     */
    private static Parser mParser;

    /**
     * A private constructor to prevent creating instances.
     */
    private Parser() {
    }

    /**
     * It returns instance of the Parser object,
     *
     * @return Parser
     */
    public static Parser getInstance() {
        if (mParser == null)
            mParser = new Parser();
        return mParser;
    }

    /**
     * It uses the getShort() method to get the opcode and the block number.
     *
     * @param buf Received request.
     * @return int[]
     */
    public int[] getValues(byte[] buf) {
        ByteBuffer wrap = ByteBuffer.wrap(buf);
        int[] values = new int[2];
        values[0] = wrap.getShort();
        values[1] = wrap.getShort();
        return values;
    }

    /**
     * It uses the getShort() method to get the opcode.
     *
     * @param buf Received request.
     * @return Opcode request type: RRQ or WRQ.
     */
    public static int getOpcode(byte[] buf) {
        ByteBuffer wrap = ByteBuffer.wrap(buf);
        return wrap.getShort();
    }

    /**
     * The first two indexes are for the opcode.
     * It loops the rest of the buffer from the third index to get the file name and the mode.
     * There is a zero between the file name the and mode and after the mode there is also a zero.
     * Once the the method get to the second zero, that is where the data ends.
     *
     * @param buf Received request.
     * @return String[] file name and the mode.
     */
    public String[] getFilenameAndMode(byte[] buf) {
        int zeroCounter = 0;
        String[] filenameAndMode = new String[2];
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < buf.length; i++) {
            if ((char) buf[i] == 0) {
                filenameAndMode[zeroCounter] = sb.toString();
                if (filenameAndMode[zeroCounter].endsWith(Tags.BACK_SLASH))
                    filenameAndMode[zeroCounter] = filenameAndMode[zeroCounter]
                            .substring(0, filenameAndMode[zeroCounter].length() - 1);
                sb = new StringBuilder();
                zeroCounter++;
            } else sb.append((char) buf[i]);
            if (zeroCounter == 2) break;
        }
        return filenameAndMode;
    }

    /**
     * It wraps the data according to the request and returns it as bytes.
     *
     * @param request    the request.
     * @param block      the block number.
     * @param data       the data to be sent to the client.
     * @param bufferSize the buffer size.
     * @param withData   either sending just acknowledgment to receive/confirm data or sending data to the client along
     *                   with the opcode.
     * @return byte[]
     */
    public byte[] getByte(Request request, int block, byte[] data, int bufferSize, boolean withData) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
        byteBuffer.putShort((short) request.getCode());
        byteBuffer.putShort((short) block);
        if (withData) byteBuffer.put(data);
        return byteBuffer.array();
    }

    /**
     * It returns the request which contains the operation type,
     * i.e. by comparing values that received from the client
     * with the values of the server, one of which is the block number.
     * It designed to get the request after transmitting one chunk of data at the time
     * to see whether the client received the data,
     * if they did then it returns acknowledgment.
     * If not, it returns TRANSFER_IS_NOT_DONE to notify that the segment should be re-transmitted.
     * If there is any error, it returns error.
     *
     * @param val   values; opcode and block number from the client.
     * @param block the actual block number of the server.
     * @return Request.
     */
    public Request getRequest(int[] val, int block) {
        if (val[0] == Request.OPERATION_ERROR.getCode())
            return Request.OPERATION_ERROR;
        else if (val[0] == Request.OPERATION_ACKNOWLEDGMENT_REQUEST.getCode() && val[1] == block)
            return Request.OPERATION_ACKNOWLEDGMENT_REQUEST;
        else
            return Request.TRANSFER_IS_NOT_DONE;
    }
}
