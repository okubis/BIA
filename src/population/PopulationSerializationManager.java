package population;

import java.io.*;

/**
 * Created by okubis on 5/31/16.
 */
public class PopulationSerializationManager {
    public static void exportToFile(String pathToFile, Population population) {
        try {
            File f = new File(pathToFile);
            f.getParentFile().mkdirs();
            OutputStream file = new FileOutputStream(pathToFile);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            try {

                output.writeObject(population);
                return;
            } finally {
                output.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Population importFromFile(String pathToFile) {
        Population population = null;

        try {
            InputStream file = new FileInputStream(pathToFile);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            try {
                population = (Population) input.readObject();

            } finally {
                input.close();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return population;
    }
}
