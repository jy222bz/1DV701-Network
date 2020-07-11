package lnu.assignment2.http;

import lnu.assignment2.exceptions.BaseException;
import lnu.assignment2.resource.SharedFolder;
import lnu.assignment2.static_data.Tags;

import java.io.File;


/**
 * A class for the HTTP response.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public class HTTPResponse {

    /**
     * A private field for the shared folder.
     */
    private final SharedFolder sharedFolder;

    /**
     * A private field for the request.
     */
    private final Request request;

    /**
     * A private field for the data.
     */
    private Data data;

    /**
     * @param sharedFolder the main directory.
     * @param request      request.
     * @throws BaseException, If an error that is related to the director occurs.
     */
    public HTTPResponse(SharedFolder sharedFolder, Request request) throws BaseException {
        this.sharedFolder = sharedFolder;
        this.request = request;
        handleGet();
    }

    /**
     * It gets the requested file.
     *
     * @throws BaseException, If an error that is related to the director occurs.
     */
    private void handleGet() throws BaseException {
        data = sharedFolder.getData(request.getUrl());
    }

    /**
     * It returns whether the file is modified, if it is then code of the response is 302.
     * When the method is Put or Post, it returns code 201.
     *
     * @return int
     */
    public int getCode() {
        if (data.isModified())
            return Tags.FOUND_CODE;
        else return Tags.OK_CODE;
    }

    /**
     * Whether the connection should kept alive.
     *
     * @return boolean.
     */
    public boolean shouldClose() {
        return request.shouldClose();
    }

    /**
     * It returns the file.
     *
     * @return File.
     */
    public File getFile() {
        return data.getFile();
    }

    /**
     * It returns the data type of the file.
     *
     * @return String.
     */
    public String getDataType() {
        return data.getContentType();
    }

    /**
     * It returns the size of the file.
     *
     * @return long
     */
    public long getDataSize() {
        return data.getFile().length();
    }
}
