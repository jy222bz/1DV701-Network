package lnu.assignment3.validators;

import lnu.assignment3.exceptions.BaseException;
import lnu.assignment3.exceptions.Classification;

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
     * It checks the arguments.
     * There should be no arguments provided.
     *
     * @param args the arguments.
     * @throws BaseException if there are arguments provided by the user.
     */
    public void validateArguments(String[] args) throws BaseException {
        if (!isObjectInvalid(args) && args.length > 0)
            throw new BaseException(Classification.NO_ARGUMENTS);
    }
}