package lnu.assignment1.exceptions;

/**
 * Enum class for the classifications and the descriptions the of the customized Exceptions.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public enum Classification {

    IP_ADDRESS_INVALID("The provided IP address is invalid. Check the IP ADDRESS:s FORMAT or VALUES!"),
    ARGUMENTS_ARE_NULL("The arguments are invalid. NULL"),
    TRANSFER_RATE_ILLEGAL_FORMAT("The transfer RATE is NOT an integer!"),
    ILLEGAL_AMOUNT_OF_ARGUMENTS("Illegal amount of arguments. There should be 4 arguments.!"),
    BUFFER_ILLEGAL_FORMAT("The BUFFER SIZE should be integer!"),
    PORT_ILLEGAL_FORMAT("The format of the value of the PORT is illegal!"),
    PACKET_SIZE_INVALID("According to UDP, the size of the message should NOT be greater " +
            "than 65507 bytes nor empty."),
    PORT_NUMBER_INVALID("The PORT NUMBER is invalid!"),
    MESSAGE_EMPTY("The message should not be empty!"),
    NO_SINGLETON_ARGUMENT("There should be only one argument!"),
    BUFFER_VALUE_INVALID("Buffer value cannot be zero!"),
    NEGATIVE_VALUE("There is a negative value among the arguments!");

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