package lnu.assignment1.validators;

import lnu.assignment1.tags.Tags;

/**
 * A utility class that has one public static method that is designed to validate IPv4 addresses.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class IPAddressValidator {

    /**
     * A private constructor, to prevent creating instances of the class.
     */
    private IPAddressValidator() {
    }

    /**
     * It validates whether the string is a valid ip address.
     * It depends on other two private helper methods to conduct its function.
     *
     * @param ipAddress the ip address to be validated.
     * @return boolean
     */
    public static boolean isValidIP4Address(String ipAddress) {
        if (isValidString(ipAddress))
            return isValidIP(ipAddress);
        return false;
    }

    /**
     * It validates whether the String is valid.
     * The string should not be null, empty or ends with an period.
     * An ip address contains periods in it but it does not start or end with a period.
     *
     * @param str the string that its format to be validated.
     * @return boolean
     */
    private static boolean isValidString(String str) {
        return str != null && !str.isEmpty() && !str.endsWith(Tags.PERIOD_NOTATION) && !str.startsWith(Tags.PERIOD_NOTATION);
    }


    /**
     * This method checks whether the string has the properties of an ipAddress address.
     * An IP address is 32-bit value and contains four segments of values.
     * Each segment in the ip address corresponds to 8-bit value, which means the range of the value is within 0-255.
     * The four segments are separated by a period, i.e. there should be three periods in-between the string.
     * Moreover, a segment cannot start with zero if it has more than one value.
     * Segments Examples: 01 is invalid and 0 is valid.
     *
     * @param ipAddress the ipAddress address to be validated.
     * @return boolean
     */
    private static boolean isValidIP(String ipAddress) {
        String[] segments = ipAddress.split(Tags.PERIOD_PATTERN);
        if (segments.length != 4)
            return false;
        else {
            for (String segment : segments) {
                try {
                    int segmentValue = Integer.parseInt(segment);
                    if (segment.length() > 1 && segment.startsWith(Tags.ZERO)
                            || segmentValue > 255 || segmentValue < 0)
                        return false;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
    }
}
