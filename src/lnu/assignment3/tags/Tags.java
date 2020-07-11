package lnu.assignment3.tags;

/**
 * A class for static data.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class Tags {

    /**
     * A private constructor to prevent making instances.
     */
    private Tags() {
    }

    public static final int SERVER_PORT_NUMBER = 4970;
    public static final int DEFAULT_BUFFER_SIZE = 516;
    public static final String READ_DIRECTORY_PATH = "src/lnu/assignment3/read_directory/";
    public static final String WRITE_DIRECTORY_PATH = "src/lnu/assignment3/write_directory/";
    public static final String PRIVATE_FILENAME = "private.txt";
    public static final String OCTET = "octet";
    public static final int VALUE_OF_ZERO = 0;
    public static final int MAXIMUM_RETRANSMISSIONS = 5;
    public static final String BACK_SLASH = "/";
    public static final int TIME_OUT = 3000;
    public static final int DATA_INDEX = 4;
    public static final int HEADER_SIZE = 4;
    public static final String SHUT_DOWN_ERROR = "Due to the error, the system will be terminated now.";
}