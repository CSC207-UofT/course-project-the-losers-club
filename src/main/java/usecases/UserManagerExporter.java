package usecases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class UserManagerExporter {

    /**
     * Export the given <code>UserManager</code>.
     *
     * @param manager    <code>UserManager</code> to serialize
     * @param outputFile <code>OutputStream</code> to serialize to
     * @throws IOException if an I/O error occurs while creating or writing to the given <code>OutputStream</code>
     */
    public static void export(UserManager manager, String outputFile) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(outputFile));
        objectOutputStream.writeObject(manager);
    }
}
