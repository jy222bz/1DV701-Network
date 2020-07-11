package lnu.assignment3;

import lnu.assignment3.server.Server;
import lnu.assignment3.server.TFTPServer;

/**
 * A class for the main method of the program.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-03-07
 */
public class Program {

    /**
     * The main entry point to the server.
     *
     * @param args should be empty.
     */
    public static void main(String[] args) {
        Server server = new TFTPServer(args);
        server.start();
    }
}
