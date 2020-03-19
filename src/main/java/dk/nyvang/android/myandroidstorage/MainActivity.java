package dk.nyvang.android.myandroidstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dk.nyvang.android.myandroidstorage.handlers.DataHandler;
import dk.nyvang.android.myandroidstorage.utills.Lo;
import dk.nyvang.android.myandroidstorage.utills.LogLevels;

/**
 * MyAndroidStorage application.
 *
 * This app is an example on how to create a few text fields, save the input data in the internal
 * app storage, and to be able to get the data again.
 *
 * I have added several tests to run on the MainActivity to actually prove to myself,
 * if everything is working as expected.
 *
 * @author NE0V
 * @version 1.2
 */
public class MainActivity extends AppCompatActivity {

    // Enable global log statements
    // TODO : Disable prior to release
    public static final boolean IS_GLOBAL_APPLICATION_LOGGING_ACTIVE = true;

    // Java.lang.stuff
    private List<String> dataList;
    private int errorLevel;
    private StringBuilder faultyFields;
    // This.app
    private DataHandler dataHandler;

    /**
     * App entry - starts the app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = new ArrayList<>();
        dataHandler = new DataHandler();
        faultyFields = new StringBuilder();
    }

    /**
     * Saves the data (text) from the UI´s TextEdit fields after validating the input values.
     * The valid data is added to a list and passed on to the <code>DataHandler</code> which then,
     * in turn, return a copy of the saved data.
     * @param view View - the current view
     */
    public void onSaveButtonClick(View view) {

        EditText nameField = findViewById(R.id.name_edit);
        EditText addressField = findViewById(R.id.address_edit);
        EditText phoneField = findViewById(R.id.phone_edit);
        EditText emailField = findViewById(R.id.email_edit);

        errorLevel = 0;

        faultyFields.delete(0, faultyFields.length()+1);
        faultyFields.append(getString(R.string.warning_fields_empty));

        if(checkInput(nameField)) {
            dataList.add(nameField.getText().toString());
        } else {
            faultyFields.append(getString(R.string.ui_field_name));
            errorLevel++;
        }
        if(checkInput(addressField)) {
            dataList.add(addressField.getText().toString());
        } else {
            faultyFields.append(getString(R.string.ui_field_address));
            errorLevel++;
        }
        if(checkInput(phoneField)) {
            dataList.add(phoneField.getText().toString());
        } else {
            faultyFields.append(getString(R.string.ui_field_phone));
            errorLevel++;
        }
        if(checkInput(emailField)) {
            dataList.add(emailField.getText().toString());
        } else {
            faultyFields.append(getString(R.string.ui_field_email));
            errorLevel++;
        }

        if(errorLevel >= 1) {
            Toast.makeText(
                    this,
                    R.string.user_action_fill_in_fields + "\n" + faultyFields,
                    Toast.LENGTH_LONG
            ).show();

            Lo.g(LogLevels.LEVEL_WARNING, getOrigin(),
                    "ErrorLevel (Should be 0)= " + errorLevel + "\n" + faultyFields );
        } else {

            String result = dataHandler.saveDataToFile(this, dataList);

            if(!result.isEmpty()) {
                Toast.makeText(this,
                        getString(R.string.ui_sucessful_data_save) + result,
                        Toast.LENGTH_LONG
                ).show();

                TextView outputTextView = findViewById(R.id.output_name_text_view);
                outputTextView.setText(result);
            }
        }
    }

    /**
     * Responsible for reading the internal storage of the app, and to present the user
     * with this data by adding it to the output text view
     * @param view View - the current view
     */
    public void onGetDataButtonClick(View view) {
        TextView outputTextView = findViewById(R.id.output_name_text_view);
        outputTextView.setText("");
        String savedData = dataHandler.readDataFromFile(this);

        outputTextView.setText(savedData);
        Toast.makeText(this, savedData, Toast.LENGTH_SHORT).show();
    }

    /**
     * Basic field validation, that checks for empty field.
     * Called from the onSaveButtonClick method
     * @param field - the Input field
     * @return true if ok, false if error
     */
    private boolean checkInput(EditText field) {
        // TODO: Expand the validation to actually validate field data, and not just for empty fields.
        //  This, however, is not the idea with the app. This is to play around with storage of data in files
        return "" != field.getText().toString();
    }

    /**
     * Simple function for improved readability on log statements.
     * Adds package name and class name for easy searching through the log.
     * @return String - "Package-name and Class-name"
     */
    private String getOrigin() {
        return "Package-> " + this.getPackageName() + "\n" + "Class-> " + this.getLocalClassName();
    }
}
