package usecases;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class UserManagerImporter {

    /**
     * Import the <code>UserManager</code> serialized in the <code>InputStream</code>.
     *
     * @param inputFile a filepath to a serialized form of a <code>UserManager</code>
     * @return the <code>UserManager</code> represented in the given <code>InputStream</code>
     * @throws IOException            if the <code>InputStream</code> throws an <code>IOException</code> at any time
     * @throws ClassNotFoundException if the deserialization results in a class that is not found within
     *                                the project structure
     */
    public static UserManager importUserManager(String inputFile) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(inputFile));
        return (UserManager) objectInputStream.readObject();
    }
}
