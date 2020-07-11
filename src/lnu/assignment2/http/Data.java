package lnu.assignment2.http;


import java.io.File;

/**
 * A class for the date of the response to the client.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public class Data {

    /**
     * A private field for the data.
     */
    private final File file;

    /**
     * Whether the file has been modified.
     * Ex. the file: index.htm will be be modified to index.html
     * @return boolean
     */
    public boolean isModified() {
        return isModified;
    }

    /**
     * A private field for the boolean whether its extension were updated and the client is still able to retrieve it.
     */
    private final boolean isModified;

    /**
     * A private field for the content of the file.
     */
    private final String contentType;

    /**
     * A constructor to construct an object.
     *
     * @param file        the file.
     * @param isModified  boolean.
     * @param contentType content type.
     */
    public Data(File file, boolean isModified, String contentType) {
        this.file = file;
        this.isModified = isModified;
        this.contentType = contentType;
    }

    /**
     * It returns the file.
     *
     * @return File
     */
    public File getFile() {
        return file;
    }

    /**
     * It returns the content type.
     *
     * @return String
     */
    public String getContentType() {
        return contentType;
    }

}
