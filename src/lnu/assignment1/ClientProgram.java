package lnu.assignment1;

import lnu.assignment1.server.Client;

/**
 * The main entry point of the client.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class ClientProgram {

    /**
     * @param args, in this order; ip address, port number, rate and buffer size.
     *              If the desired runtime is infinite then the runtime can set to any value less than one, or
     *              it does not to be set at all and it will run infinite.
     *              To run the program for some specific time, set the run time for the desired amount of seconds.
     */
    public static void main(String[] args) {
        Client host = new lnu.assignment1.udp.UDPEchoClient(args);
        /*Client host = new org.assignment1.tcp.TCPEchoClient(args);*/
        host.setRunTime(-1);
        host.start();
    }
}
