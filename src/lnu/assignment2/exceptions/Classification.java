package lnu.assignment2.exceptions;

/**
 * Enum class for the classifications and the descriptions the of the customized Exceptions.
 *
 * @author Jacob Yousif
 * @version 2.0
 * @since 2020-02-17
 */
public enum Classification {

    ARGUMENTS_ARE_NULL("The arguments are invalid. NULL"),
    BAD_REQUEST("Bad Request! Either the HTTP version is an unsupported HTTP version or the amount of the arguments incorrect"),
    ILLEGAL_AMOUNT_OF_ARGUMENTS("Illegal amount of arguments. There should be 2 arguments.!"),
    PORT_ILLEGAL_FORMAT("The format of the value of the PORT is illegal!"),
    PORT_NUMBER_INVALID("The PORT NUMBER is invalid!"),
    PATH_INVALID("The path for the directory is invalid! It has to be a path for the directory."),
    METHOD_INVALID("There is no such method!"),
    METHOD_NOT_IMPLEMENTED("The requested method is not implemented!"),
    UNAUTHORIZED_ACCESS("You do not have authorization to access the file!"),
    INTERNAL_ERROR("An Internal Error!"),
    FILE_DOES_NOT_EXIST("The file does not exist in the system!"),
    EMPTY_LINE("The line is empty!");


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