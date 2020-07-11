package lnu.assignment2.validators;

import lnu.assignment2.exceptions.Classification;
import lnu.assignment2.exceptions.IllegalArgumentsException;
import lnu.assignment2.static_data.Tags;

import java.io.File;

/**
 * It is designed to check whether the are arguments are valid from different perspectives.
 * It is a singleton class.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class Validator {

    /**
     * A private field for the Validator to make it singleton.
     */
    private static Validator mValidator;

    /**
     * A private constructor to prevent making instances.
     */
    private Validator() {
    }


    /**
     * It returns the instance of the Validator.
     *
     * @return Validator
     */
    public static Validator getInstance() {
        if (mValidator == null)
            mValidator = new Validator();
        return mValidator;
    }

    /**
     * It checks whether the provided path is valid.
     *
     * @param path the path of the directory.
     * @return boolean
     */
    public boolean isPathInvalid(String path) {
        File file = new File(path);
        return !file.isDirectory();
    }

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
     * It checks whether the argument that was provided by the user is valid.
     *
     * @param args the arguments.
     * @throws IllegalArgumentsException it throws various types of exceptions, depends on the type of the foul argument.
     */
    public void validateArguments(String[] args) throws IllegalArgumentsException {
        if (isObjectInvalid(args))
            throw new IllegalArgumentsException(Classification.ARGUMENTS_ARE_NULL);
        if (args.length != 2)
            throw new IllegalArgumentsException(Classification.ILLEGAL_AMOUNT_OF_ARGUMENTS);
        if (isNotNumber(args[0]))
            throw new IllegalArgumentsException(Classification.PORT_ILLEGAL_FORMAT);
        if (getNumber(args[0]) > Tags.LAST_ALLOWED_PORT || getNumber(args[0]) < Tags.FIRST_ALLOWED_PORT)
            throw new IllegalArgumentsException(Classification.PORT_NUMBER_INVALID);
        if (isPathInvalid(args[1]))
            throw new IllegalArgumentsException(Classification.PATH_INVALID);
    }
}