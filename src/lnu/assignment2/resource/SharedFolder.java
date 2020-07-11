package lnu.assignment2.resource;

import lnu.assignment2.exceptions.*;
import lnu.assignment2.http.Data;
import lnu.assignment2.policy.AccessPolicy;
import lnu.assignment2.static_data.FileType;
import lnu.assignment2.static_data.Tags;

import java.io.*;
import java.util.Objects;

/**
 * This class for the main directory.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public class SharedFolder {

    /**
     * A private field for the main directory.
     */
    private final File mainDirectory;

    private boolean isModified;


    /**
     * @param mainDirectory the main directory.
     */
    public SharedFolder(File mainDirectory) {
        this.mainDirectory = mainDirectory;
    }

    /**
     * This method works for the HTTP get method.
     * It looks for the target file and it returns a response data.
     *
     * @param path the path of the targeted file.
     * @return ResponseData
     * @throws BaseException, If the file is not found.
     */
    public synchronized Data getData(String path) throws BaseException {
        BaseException baseException;
        isModified = false;
        String filePath = checkExt(path);
        File file = new File(filePath);
        File file1 = new File(mainDirectory, filePath);
        if (!file.isDirectory())
            file = file1;
        if (file.isDirectory()) {
            for (int i = 0; i < Objects.requireNonNull(file.listFiles()).length; i++) {
                if (Objects.requireNonNull(file.listFiles())[i].getName().equals(Tags.HTML_INDEX)) {
                    file = Objects.requireNonNull(file.listFiles())[i];
                    break;
                }
            }
        }

        if (AccessPolicy.isAccessNotPermitted(file)) {
            baseException = new UnauthorizedException(Classification.UNAUTHORIZED_ACCESS);
            throw baseException;
        } else if (file.exists()) {
            return new Data(file, isModified, getContentType(file.getName()));
        } else {
            baseException = new FileDoesNotExist(Classification.FILE_DOES_NOT_EXIST);
            throw baseException;
        }
    }


    /**
     * It returns the content type.
     *
     * @param ext the extension of the file.
     * @return String
     */
    private String getContentType(String ext) {
        return FileType.getFileType(ext);
    }

    /**
     * It checks whether the path needs to be modified and it acts accordingly.
     * Ex. if the file: index.htm then it should be modified to be index.html
     *
     * @param filePath the path of the file.
     * @return String
     */
    private String checkExt(String filePath) {
        if (filePath.endsWith(Tags.HTM)) {
            filePath += Tags.L;
            isModified = true;
        } else if (filePath.equals(Tags.BACK_SLASH))
            filePath += Tags.HTML_INDEX;
        return filePath;
    }
}
