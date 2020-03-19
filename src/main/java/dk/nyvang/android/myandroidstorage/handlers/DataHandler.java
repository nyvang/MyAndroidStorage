package dk.nyvang.android.myandroidstorage.handlers;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public String saveDataToFile(Context context, @NotNull List<String> data) {
        StringBuilder sb = new StringBuilder();
                for (int i = 0; i<data.size(); i++) {
            sb.append(data.get(i));
        }

        String fileContents = sb.toString();

        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(fileContents.getBytes());
            fos.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return fileContents;
    }

    /**
     * Find and return the data saved to the internal storage of the Android app.
     *
     * @param context - Context, the current context of the app
     * @return String - the data previously saved by <code>saveDataToFile</code>
     */
    public String readDataFromFile(Context context) {
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;

        try (FileInputStream fileInputStream = context.openFileInput(FILE_NAME)) {

            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb);

            bufferedReader.close();
            inputStreamReader.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return sb.toString();
    }

}
