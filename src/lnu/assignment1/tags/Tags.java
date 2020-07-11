package lnu.assignment1.tags;


/**
 * A class that is holding the important tags.
 * The tags will never change over time.
 * It is good to have them as final and separate.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class Tags {

    /**
     * A private constructor, to prevent creating instances of the class.
     */
    private Tags() {
    }

    /**
     * The public and static fields are self-explanatory.
     */
    public static final int SERVER_PORT = 4950;
    public static final int CLIENT_PORT = 0;
    public static final int FIRST_ALLOWED_PORT = 1;
    public static final int LAST_ALLOWED_PORT = 65535;
    public static final String PERIOD_PATTERN = "\\.";
    public static final String PERIOD_NOTATION = ".";
    public static final String ZERO = "0";
    public static final String SHUT_DOWN = "The system will be terminated now.";
    public static final String SHUT_DOWN_ERROR = "Due to the error, the system will be terminated now.";
    public static final String MESSAGE_NOT_EQUAL_SHUT_DOWN = "The messages are not equal. Therefore, the system will be terminated now.";
    public static final long ONE_SECOND = 1000;
    public static final String MESSAGE = "An Echo Message!";
    public static final String THREAD_INITIAL_MESSAGE = "Thread No.: ";
    public static final String ECHOING = " is echoing the following message: ";
    public static final String BUFFER_VALUE_INVALID = "The provided buffer size was INVALID";
    public static final String UDP_VERSION = "UDP.";
    public static final String TCP_VERSION = "TCP.";
}
