package lnu.assignment1;

import lnu.assignment1.server.Server;

/**
 * The main entry point of the server.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class ServerProgram {
    /**
     * @param args, one argument which is the buffer size.
     */
    public static void main(String[] args) {
        Server server = new lnu.assignment1.udp.UDPEchoServer(args);
        /*Server server = new lnu.assignment1.tcp.TCPEchoServer(args);*/
        server.start();
    }
}
