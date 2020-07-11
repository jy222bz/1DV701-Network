package lnu.assignment3.validators;

/**
 * A class with one static method.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-03-07
 */
public class BufferValidator {
    /**
     * A private constructor to construct an object.
     */
    private BufferValidator() {
    }


    /**
     * It validates the buffer size whether it is valid.
     * If it is valid, it returns the targeted byte array.
     * Otherwise, it returns an empty array.
     *
     * @param bufferSize the buffer size
     * @return byte[]
     */
    public static byte[] getBuffer(int bufferSize) {
        if (bufferSize > 0) {
            try {
                return new byte[bufferSize];
            } catch (OutOfMemoryError e) {
                return new byte[0];
            }
        } else
            return new byte[0];
    }
}
