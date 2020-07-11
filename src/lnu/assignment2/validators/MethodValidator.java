package lnu.assignment2.validators;

import lnu.assignment2.exceptions.BadRequestException;
import lnu.assignment2.exceptions.BaseException;
import lnu.assignment2.exceptions.Classification;
import lnu.assignment2.exceptions.NotImplementedException;
import lnu.assignment2.static_data.Method;

/**
 * A class with one static method.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public class MethodValidator {

    /**
     * A private constructor to prevent creating instances.
     */
    private MethodValidator() {
    }

    /**
     * It checks whether the method exists and whether the method is implemented.
     * It returns a method object if the method exists.
     *
     * @param methodName the name of the method
     * @return Method
     * @throws BaseException  If the method is invalid or there is no such HTTP method.
     * @throws BaseException, If the method that is requested is not implemented.
     */
    public static Method getMethod(String methodName) throws BaseException {
        Method method = null;
        BaseException baseException;
        Method[] methods = Method.values();
        for (Method m : methods) {
            if (m.getMethod().equalsIgnoreCase(methodName)) {
                method = m;
                break;
            }
        }
        if (method == null) {
            baseException = new BadRequestException(Classification.METHOD_INVALID);
            throw baseException;
        } else if (!method.isImplemented()) {
            baseException = new NotImplementedException(Classification.METHOD_NOT_IMPLEMENTED);
            throw baseException;
        }
        return method;
    }
}
