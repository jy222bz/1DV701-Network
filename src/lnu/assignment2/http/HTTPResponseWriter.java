package lnu.assignment2.http;

import lnu.assignment2.exceptions.*;
import lnu.assignment2.static_data.Tags;

import java.io.*;

/**
 * A class for the HTTP response.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-17
 */
public class HTTPResponseWriter {

    /**
     * A private field for the code of the response.
     */
    private int code;

    /**
     * It send the response to the client.
     * This method only for successful responses.
     *
     * @param code         the code of the response.
     * @param file         the file to be sent to the client.
     * @param dataType     the type of the data.
     * @param outputStream the out stream.
     * @param buffer       the byte array for the buffer.
     * @throws IOException if IO error occurs.
     */
    public void write(int code, File file, String dataType, DataOutputStream outputStream, byte[] buffer, long dataSize)
            throws IOException {
        outputStream.writeBytes(getHeader(code, dataType, dataSize));
        sendFile(new FileInputStream(file), outputStream, buffer);
    }

    /**
     * It responds according to the error.
     * This method only for failed operations.
     *
     * @param outputStream the out stream.
     * @param buffer       the byte array for the buffer
     * @param e            The exception that was thrown.
     * @throws IOException, if an IO error occurs.
     */
    public void writeError(DataOutputStream outputStream, byte[] buffer, Exception e) throws IOException {
        File file = getFile(e);
        outputStream.writeBytes(getHeader(code, Tags.HTML_FILE, file.length()));
        sendFile(new FileInputStream(file), outputStream, buffer);
    }

    /**
     * It constructs the header and  it returns it as byte array.
     *
     * @param code     the code of the response.
     * @param dataType the tpe of the data to be sent to the client.
     * @param length   the length of the data.
     * @return String
     */
    private String getHeader(int code, String dataType, long length) {
        return HTTPHeaderResponse.getHeader(code, dataType, length);
    }

    /**
     * It send the file to the client.
     *
     * @param fileInputStream the target file.
     * @throws IOException, If IO Error occurs.
     */
    private void sendFile(FileInputStream fileInputStream, DataOutputStream outputStream, byte[] buffer) throws IOException {
        int br;
        while ((br = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, br);
        }
        fileInputStream.close();
        outputStream.flush();
    }

    /**
     * It chooses the file that suitable for the exception that was caught.
     * Ex. If the file not found an exception will br thrown and
     * The file will be chosen for this example is the File Not Found HTML,
     * to send it to the client.
     * It assigns the code of the response also based on the exception.
     *
     * @param e the exception for the error.
     * @return File
     */
    private File getFile(Exception e) {
        if (e instanceof FileDoesNotExist) {
            code = 404;
            return new File(Tags.PATH_NOT_FOUND_FILE);
        } else if (e instanceof BadRequestException || e instanceof BadUploadException) {
            code = 400;
            return new File(Tags.PATH_BAD_REQUEST_FILE);
        } else if (e instanceof IOException || e instanceof InternalErrorException) {
            code = 500;
            return new File(Tags.PATH_INTERNAL_ERROR_FILE);
        } else if (e instanceof NotImplementedException) {
            code = 501;
            return new File(Tags.PATH_NOT_IMPLEMENTED_FILE);
        } else if (e instanceof UnauthorizedException) {
            code = 403;
            return new File(Tags.PATH_UNAUTHORIZED_FILE);
        } else if (e instanceof NoContentException) {
            code = 204;
            return new File(Tags.NO_CONTENT_FILE_PATH);
        }
        code = 500;
        return new File(Tags.PATH_INTERNAL_ERROR_FILE);
    }
}