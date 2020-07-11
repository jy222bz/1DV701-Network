package lnu.assignment2.static_data;

/**
 * A class for returning the type of the content.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public class FileType {

    /**
     * A private constructor to prevent creating instances.
     */
    private FileType() {}

    /**
     * It returns the type of content based on the extension of the file.
     *
     * @param fileName the name of the file.
     * @return String
     */
    public static String getFileType(String fileName) {
        String[] extensions = Tags.getExtensions();
        if (fileName.endsWith(extensions[0]) || fileName.endsWith(extensions[1]))
            return Tags.HTML_FILE;
        else if (fileName.endsWith(extensions[2]) || fileName.endsWith(extensions[3]))
            return Tags.JPEG_FILE;
        else if (fileName.endsWith(extensions[4]))
            return Tags.PNG_FILE;
        else
            return Tags.HTML_FILE;
    }
}
