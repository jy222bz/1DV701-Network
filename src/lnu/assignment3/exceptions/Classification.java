package lnu.assignment3.exceptions;

/**
 * Enum class for the classifications and the descriptions the of the customized Exceptions.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public enum Classification {

    NO_ARGUMENTS("There should be no arguments provided."),
    DIRECTORY_ERROR("There is an error with either the write or the read directory."),
    DIRECTORY_PATH_ERROR("There is an error with either the path of write or the read directory."),
    BUFFER_SIZE("Invalid buffer size. There is a problem with assigning this value.");


    /**
     * Private field for the description.
     */
    private final String description;

    /**
     * Constructor to construct the object.
     *
     * @param description the description of the exception.
     */
    Classification(String description) {
        this.description = description;
    }

    /**
     * It returns the description.
     *
     * @return String
     */
    public String getDescription() {
        return this.description;
    }
}