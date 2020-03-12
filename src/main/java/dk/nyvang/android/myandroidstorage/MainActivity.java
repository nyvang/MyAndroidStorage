package dk.nyvang.android.myandroidstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import dk.nyvang.android.myandroidstorage.handlers.DataHandler;
import dk.nyvang.android.myandroidstorage.utills.ErrorLevel;
import dk.nyvang.android.myandroidstorage.utills.Lo;
import dk.nyvang.android.myandroidstorage.utills.LogLevels;

public class MainActivity extends AppCompatActivity {

    // Enable global log statements
    public static final boolean IS_GLOBAL_APPLICATION_LOGGING_ACTIVE = true;

    // Error levels
    private static final int NO_ERRORS       = 0;
    private static final int ONE_ERROR       = 1;
    private static final int TWO_ERRORS      = 2;
    private static final int THREE_ERRORS    = 3;

    // TextFields (UI)
    EditText nameField;
    EditText addressField;
    EditText phoneField;
    EditText emailField;

    // Java.lang.stuff
    private List<String> dataList;
    private int errorLevel;
    private StringBuilder sb;
    // This.app
    private DataHandler dataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = new ArrayList<>();
        dataHandler = new DataHandler();
        sb = new StringBuilder();
    }

    // TODO: How about making this method a little shorter........
    public void onSaveButtonClick(View view) {

        nameField = (EditText) findViewById(R.id.name_edit);
        addressField = (EditText) findViewById(R.id.address_edit);
        phoneField = (EditText) findViewById(R.id.phone_edit);
        emailField = (EditText) findViewById(R.id.email_edit);

        toggleOutputContainer();
        errorLevel = 0;

        sb.delete(0, sb.length()+1);
        sb.append(getString(R.string.warning_fields_empty));

        if(checkInput(nameField)) {
            dataList.add(nameField.getText().toString());
        } else {
            sb.append(getString(R.string.ui_field_name));
            errorLevel++;
        }
        if(checkInput(addressField)) {
            dataList.add(addressField.getText().toString());
        } else {
            sb.append(getString(R.string.ui_field_address));
            errorLevel++;
        }
        if(checkInput(phoneField)) {
            dataList.add(phoneField.getText().toString());
        } else {
            sb.append(getString(R.string.ui_field_phone));
            errorLevel++;
        }
        if(checkInput(emailField)) {
            dataList.add(emailField.getText().toString());
        } else {
            sb.append(getString(R.string.ui_field_email));
            errorLevel++;
        }

        if(errorLevel == ErrorLevel.FOUR_ERRORS) {
            Toast.makeText(this, R.string.user_action_fill_in_fields, Toast.LENGTH_LONG).show();
            Lo.g(LogLevels.LEVEL_DEBUG, getOrigin(), "ErrorLevel (Should be 4)= " + errorLevel );
            throw new InputVoidException(getString(R.string.error_empty_fields));
        } else {
            Lo.g(LogLevels.LEVEL_DEBUG, getOrigin(), "ErrorLevel (should be below 4)= " + errorLevel );
            String result = dataHandler.saveDataToFile(this, dataList);

            Toast.makeText(this, getString(R.string.ui_sucessful_data_save) + result, Toast.LENGTH_LONG).show();

            TextView outputTextView = (TextView) findViewById(R.id.output_name_text_view);
            outputTextView.setText(result);
        }
    }

    private String getOrigin() {
        return "Package-> " + this.getPackageName() + "\n" + "Class-> " + this.getLocalClassName();
    }

    public void onResetButtonClick(View view) {
        TextView outputTextView = (TextView) findViewById(R.id.output_name_text_view);
        outputTextView.setText("");

        if (null != nameField) nameField.getText().clear();
        if (null != addressField) addressField.getText().clear();
        if (null != phoneField) phoneField.getText().clear();
        if (null != emailField) emailField.getText().clear();

        Toast.makeText(this, R.string.info_all_fields_reset, Toast.LENGTH_SHORT).show();
    }

    public void toggleOutputContainer() {
        CardView outputContainer = (CardView) findViewById(R.id.output_view);

        if(outputContainer.getVisibility() != View.VISIBLE) {
            outputContainer.setVisibility(View.VISIBLE);
        } else {
            outputContainer.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Basic field validation, that checks for empty fields
     * @param field - the Input field
     * @return true if ok, false if error
     */
    private boolean checkInput(EditText field) {
        // TODO: Expand the validation to actually validate field data, and not just for empty fields.
        //  This, however, is not the idea with the app. This is to play around with storage of data in files
        return "" == field.getText().toString();
    }
}
