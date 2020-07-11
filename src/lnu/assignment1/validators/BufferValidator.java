package lnu.assignment1.validators;


import lnu.assignment1.utility.Utility;
import lnu.assignment1.tags.Tags;

/**
 * A class with one static method that returns byte array.
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
        Utility helper = new Utility();
        if (bufferSize > 0) {
            try {
                return new byte[bufferSize];
            } catch (OutOfMemoryError e) {
                helper.log(e.toString());
                return new byte[0];
            }
        } else {
            helper.log(Tags.BUFFER_VALUE_INVALID);
            return new byte[0];
        }
    }
}
