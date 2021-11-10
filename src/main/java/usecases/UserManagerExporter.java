package usecases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class UserManagerExporter {

    /**
     * Export the given <code>UserManager</code>.
     *
     * @param manager       <code>UserManager</code> to serialize
     * @param outputFilePat output file to serialize to
     * @throws IOException if an I/O error occurs while creating or writing to the given file path
     */
    public static void exportUserManager(UserManager manager, String outputFilePat) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(outputFilePat))) {
            objectOutputStream.writeObject(manager);
        }
    }
}
