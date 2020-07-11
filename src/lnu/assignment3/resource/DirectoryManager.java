package lnu.assignment3.resource;

import lnu.assignment3.exceptions.BaseException;
import lnu.assignment3.exceptions.Classification;
import lnu.assignment3.tags.Tags;
import lnu.assignment3.validators.Validator;

import java.io.File;

/**
 * A class that handles the client request in a separated thread.
 * It extends the Thread class.
 *
 * @author Jacob Yousif
 * @version 1.0
 * @since 2020-02-07
 */
public class DirectoryManager {

    /**
     * A private field for the write directory.
     */
    private File writeDirectory;

    /**
     * A private field for the read directory.
     */
    private File readDirectory;

    /**
     * A private field for the path of the write directory.
     */
    private final String writeDirectoryPath;

    /**
     * A constructor to construct an object.
     *
     * @param readDirectoryPath  the path for the read directory.
     * @param writeDirectoryPath the path for the write directory.
     * @throws BaseException if a pth invalid or there is a problem in creating a directory
     */
    public DirectoryManager(String readDirectoryPath, String writeDirectoryPath) throws BaseException {
        initDirectories(readDirectoryPath, writeDirectoryPath);
        this.writeDirectoryPath = writeDirectoryPath;
    }

    /**
     * It initializes the read and the write directory.
     *
     * @param readDirectoryPath  path for the read directory.
     * @param writeDirectoryPath path for the write directory.
     * @throws BaseException if the a path is invalid or a directory cannot be initiated.
     */
    private void initDirectories(String readDirectoryPath, String writeDirectoryPath) throws BaseException {
        Validator validator = Validator.getInstance();
        if (validator.isPathInvalid(readDirectoryPath) || validator.isPathInvalid(writeDirectoryPath))
            throw new BaseException(Classification.DIRECTORY_PATH_ERROR);
        File file = new File(readDirectoryPath);
        File file2 = new File(writeDirectoryPath);
        if (file.exists())
            readDirectory = file;
        if (!file.exists() && file.mkdirs())
            readDirectory = file;
        if (file2.exists())
            writeDirectory = file2;
        if (!file2.exists() && file2.mkdirs())
            writeDirectory = file2;
        if (validator.isObjectInvalid(readDirectory) || validator.isObjectInvalid(writeDirectory))
            throw new BaseException(Classification.DIRECTORY_ERROR);
    }

    /**
     * It returns the file if it does exists in the read directory.
     *
     * @param name      the name of the target file.
     * @param directory the directory that contains the file.
     * @return File
     */
    public File getFile(String name, File directory) {
        File file = new File(directory, name);
        if (file.exists())
            return file;
        return null;
    }

    /**
     * It check whether the file exists in the write directory.
     *
     * @param name      the name of the file.
     * @param directory the targeted directory.
     * @return boolean.
     */
    public boolean doesFileExist(String name, File directory) {
        File file = new File(directory, name);
        return file.exists();
    }

    /**
     * It returns the read directory.
     *
     * @return File.
     */
    public File getReadDirectory() {
        return this.readDirectory;
    }

    /**
     * It returns the write directory.
     *
     * @return File.
     */
    public File getWriteDirectory() {
        return this.writeDirectory;
    }

    /**
     * It checks whether the client can access the file.
     * The client can access the file when the file
     * is a file, located in the directory and can be read.
     *
     * @param filename  the requested file name.
     * @param directory the targeted directory.
     * @return boolean
     */
    public boolean isPermittedFile(String filename, File directory) {
        File file = new File(directory, filename);
        return file.isFile() && file.canRead() && !filename.equals(Tags.PRIVATE_FILENAME);
    }

    /**
     * It returns the path of the write directory.
     * If it has the path separator in the end, it removes it.
     *
     * @return String
     */
    public String getWriteDirectoryPath() {
        if (!writeDirectoryPath.endsWith(Tags.BACK_SLASH))
            return writeDirectoryPath + Tags.BACK_SLASH;
        return writeDirectoryPath;
    }
}
