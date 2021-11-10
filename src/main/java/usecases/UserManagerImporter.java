package usecases;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class UserManagerImporter {

    /**
     * Import the <code>UserManager</code> serialized in the given file at the file path.
     *
     * @param inputFilePath a filepath to a serialized form of a <code>UserManager</code>
     * @return the <code>UserManager</code> represented in the given file
     * @throws IOException            if reading from the file path throws an <code>IOException</code> at any time
     * @throws ClassNotFoundException if the deserialization results in a class that is not found within
     *                                the project structure
     */
    public static UserManager importUserManager(String inputFilePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(inputFilePath))) {
            return (UserManager) objectInputStream.readObject();
        }
    }
}
