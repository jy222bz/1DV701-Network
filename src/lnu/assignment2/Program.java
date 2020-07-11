package lnu.assignment2;

import lnu.assignment2.server.Server;
import lnu.assignment2.server.WebServer;

/**
 * The main entry point of the server.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public class Program {
    /**
     * @param args, two arguments the port number and the directory.
     */
    public static void main(String[] args)  {
        Server webServer = new WebServer(args);
        webServer.start();
    }
}
