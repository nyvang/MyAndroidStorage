package dk.nyvang.android.myandroidstorage.handlers;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Responsible for saving data from the input fields of the MainActivity, to a file within the internal files.
 * And of course for reading the file again, once asked to do so.
 */
public class DataHandler {

    private static final String FILE_NAME = "dataFile.txt";

    /**
     * Take the data from the different fields, and use a StringBuilder to create a single object
     * that can be written to a file by a stream
     * @param data
     */
    public String saveDataToFile(Context context, List<String> data) {
        StringBuilder sb = new StringBuilder();
                for (int i = 0; i<data.size(); i++) {
            sb.append(data.get(i));
        }

        String fileContents = sb.toString();

        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(fileContents.getBytes());
            fos.close();

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return fileContents;
    }

}
