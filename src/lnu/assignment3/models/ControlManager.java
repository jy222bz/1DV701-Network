package lnu.assignment3.models;

import lnu.assignment3.resource.DirectoryManager;
import java.net.DatagramPacket;

public class ControlManager {

    /**
     * A private constructor to prevent creating instances.
     */
    private ControlManager() {
    }

    /**
     * It checks whether there is an error occurred, during the receiving phase.
     * It checks it is the same client through the ip address and the port number.
     * It checks whether the op is actually data.
     * It checks whether there is space in the disk.
     * When every thing is okay it send no error state of request.
     *
     * @param remote           the remote datagram packet.
     * @param directoryManager the main directory.
     * @param clientIpAddress  the IP address of the client.
     * @return ErrorType
     */
    public static ErrorType getErrorStateDuringReceiving(DatagramPacket remote, DirectoryManager directoryManager, String clientIpAddress) {
        if (!remote.getAddress().getHostAddress().equals(clientIpAddress))
            return ErrorType.UNKNOWN_ID;
        else if (directoryManager.getWriteDirectory().getUsableSpace() < remote.getLength())
            return ErrorType.DISK_FULL;
        else if (Parser.getOpcode(remote.getData()) == Request.OPERATION_DATA.getCode())
            return ErrorType.NO_ERROR;
        else if (Parser.getOpcode(remote.getData()) == Request.OPERATION_ERROR.getCode())
            return ErrorType.ERROR_CLOSE_CONNECTION;
        return null;
    }

    /**
     * It checks whether the same client or not.
     *
     * @param remote          the remote datagram.
     * @param clientIpAddress the IP address of the client.
     * @return ErrorType.
     */
    public static ErrorType getErrorStateDuringSending(DatagramPacket remote, String clientIpAddress) {
        if (!remote.getAddress().getHostAddress().equals(clientIpAddress))
            return ErrorType.UNKNOWN_ID;
        return ErrorType.NO_ERROR;
    }
}
