package lnu.assignment2.static_data;

/**
 * A class that is holding the important tags.
 * The tags will never change over time.
 * It is good to have them as final and separate.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public class Tags {

    /**
     * A private constructor, to prevent creating instances of the object.
     */
    private Tags() {
    }

    /**
     * The public and static fields are self-explanatory.
     */
    public static final String BACK_SLASH = "/";
    public static final String SHUT_DOWN_ERROR = "Due to the error, the system will be terminated now.";
    public static final int FIRST_ALLOWED_PORT = 1;
    public static final int LAST_ALLOWED_PORT = 65535;
    public static final String CRLF = "\r\n";
    public static final String WHITE_SPACE = " ";
    public static final String CLOSE = "close";
    public static final String HTTP_VERSION = "HTTP/1.1";
    public static final int DEFAULT_BUFFER_SIZE = 8*1024;
    public static final String HTML_INDEX = "index.html";
    public static final String HTM = "htm";
    public static final String HTML_FILE = "text/html";
    public static final String JPEG_FILE = "image/jpg";
    public static final String PNG_FILE = "image/png";
    public static final String NO_CONTENT_FILE_PATH = "src/lnu/assignment2/html_files/noContent.html";
    public static final String PATH_BAD_REQUEST_FILE = "src/lnu/assignment2/html_files/badRequest.html";
    public static final String PATH_NOT_IMPLEMENTED_FILE = "src/lnu/assignment2/html_files/notImplemented.html";
    public static final String PATH_NOT_FOUND_FILE = "src/lnu/assignment2/html_files/notFound.html";
    public static final String PATH_INTERNAL_ERROR_FILE = "src/lnu/assignment2/html_files/internalError.html";
    public static final String PATH_UNAUTHORIZED_FILE = "src/lnu/assignment2/html_files/unauthorized.html";
    public static final String HIDDEN_FILENAME = "private.html";
    public static final int OK_CODE = 200;
    public static final int FOUND_CODE = 302;
    private static final String[] EXTENSIONS = {".html", ".htm", ".jpg", ".jpeg", ".png"};
    public static final String L ="l";

    public static String[] getExtensions() {
        return EXTENSIONS.clone();
    }
}
