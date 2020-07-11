package lnu.assignment1.validators;


import lnu.assignment1.exceptions.Classification;
import lnu.assignment1.exceptions.ExceptionMain;
import lnu.assignment1.tags.Tags;

/**
 * It is designed to check whether the object is valid from different perspectives.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class Validator {

    /**
     * It checks whether the object is valid or not.
     *
     * @param object the object to be checked.
     * @param <T>    any object.
     * @return boolean.
     */
    public <T> boolean isObjectInvalid(T object) {
        return object == null;
    }

    /**
     * It checks whether the ip address is valid.
     *
     * @param ipAddress the ip address that has to be checked.
     * @return boolean
     */
    public boolean isIPAddressValid(String ipAddress) {
        return IPAddressValidator.isValidIP4Address(ipAddress);
    }

    /**
     * It checks whether the arguments are within the legal boundaries.
     *
     * @param args the array of the parameters.
     * @return boolean
     */
    public boolean areArgumentsLengthValid(String[] args) {
        return args.length == 4;
    }

    /**
     * It checks whether the string is containing a number.
     *
     * @param number the object to be checked.
     * @return boolean
     */
    public boolean isNotNumber(String number) {
        if (isObjectInvalid(number))
            return true;
        else
            try {
                Integer.parseInt(number);
                return false;
            } catch (NumberFormatException e) {
                return true;
            }
    }

    /**
     * It converts String to integer.
     * It designed to be used after checking that the String is a number.
     *
     * @param number the value.
     * @return int
     */
    public int getNumber(String number) {
        return Integer.parseInt(number);
    }


    /**
     * It checks whether the arguments that were provided by the user are valid.
     * The legal port corresponds to 16-bit value, as the port zero reserved that leaves the range to 1-65535.
     * When the rate value is invalid, then the default value applies.
     * When the rate vale is invalid, then the default value applies but
     * when the value exceeds the memory, the system will be terminated.
     *
     * @param args the arguments.
     * @throws ExceptionMain it throws various types of exceptions, depends on the type of the foul argument.
     */
    public void validateArguments(String[] args) throws ExceptionMain {
        if (isObjectInvalid(args))
            throw new ExceptionMain(Classification.ARGUMENTS_ARE_NULL);
        if (!areArgumentsLengthValid(args))
            throw new ExceptionMain(Classification.ILLEGAL_AMOUNT_OF_ARGUMENTS);
        if (!isIPAddressValid(args[0]))
            throw new ExceptionMain(Classification.IP_ADDRESS_INVALID);
        if (isNotNumber(args[1]))
            throw new ExceptionMain(Classification.PORT_ILLEGAL_FORMAT);
        if (isNotNumber(args[2]))
            throw new ExceptionMain(Classification.TRANSFER_RATE_ILLEGAL_FORMAT);
        if (getNumber(args[2]) < 0)
            throw new ExceptionMain(Classification.NEGATIVE_VALUE);
        if (isNotNumber(args[3]))
            throw new ExceptionMain(Classification.BUFFER_ILLEGAL_FORMAT);
        if (getNumber(args[3]) < 0)
            throw new ExceptionMain(Classification.NEGATIVE_VALUE);
        if (getNumber(args[3]) == 0)
            throw new ExceptionMain(Classification.BUFFER_VALUE_INVALID);
        if (getNumber(args[1]) > Tags.LAST_ALLOWED_PORT || getNumber(args[1]) < Tags.FIRST_ALLOWED_PORT)
            throw new ExceptionMain(Classification.PORT_NUMBER_INVALID);
    }

    /**
     * It checks whether the argument that was provided by the user is valid.
     *
     * @param args the arguments.
     * @throws ExceptionMain it throws various types of exceptions, depends on the type of the foul argument.
     */
    public void validateArgument(String[] args) throws ExceptionMain {
        if (isObjectInvalid(args))
            throw new ExceptionMain(Classification.ARGUMENTS_ARE_NULL);
        if (args.length != 1)
            throw new ExceptionMain(Classification.NO_SINGLETON_ARGUMENT);
        if (isNotNumber(args[0]))
            throw new ExceptionMain(Classification.BUFFER_ILLEGAL_FORMAT);
        if (getNumber(args[0]) < 0)
            throw new ExceptionMain(Classification.NEGATIVE_VALUE);
        if (getNumber(args[0]) == 0)
            throw new ExceptionMain(Classification.BUFFER_VALUE_INVALID);
    }
}
