package lnu.assignment2.policy;

import lnu.assignment2.static_data.Tags;

import java.io.File;

/**
 * A class to check whether the client is able to access the desired file.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public class AccessPolicy {


    /**
     * A private constructor to prevent creating instances of the class.
     */
    private AccessPolicy() {
    }


    /**
     * It checks whether the file is not the private file that is not meant for clients.
     * @param target        the file that the client wants to access.
     * @return boolean
     */
    public static boolean isAccessNotPermitted(File target) {
        return target.getName().equals(Tags.HIDDEN_FILENAME);
    }
}
