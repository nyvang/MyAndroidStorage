package dk.nyvang.android.myandroidstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import dk.nyvang.android.myandroidstorage.handlers.DataHandler;
import dk.nyvang.android.myandroidstorage.utills.ErrorLevel;

public class MainActivity extends AppCompatActivity {

    // Error levels
    private static final int NO_ERRORS       = 0;
    private static final int ONE_ERROR       = 1;
    private static final int TWO_ERRORS      = 2;
    private static final int THREE_ERRORS    = 3;

    // TextFields (UI)

    // Java.lang.stuff
    private List<String> dataList;
    private AtomicInteger errorLevel = new AtomicInteger();
    private StringBuilder sb = new StringBuilder();
    // This.app
    private DataHandler dataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = new ArrayList<>();
        dataHandler = new DataHandler();
        errorLevel = new AtomicInteger();
        sb = new StringBuilder();
    }

    // TODO: How about making this method a little shorter........
    public void onSaveButtonClick(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_edit);
        EditText addressField = (EditText) findViewById(R.id.address_edit);
        EditText phoneField = (EditText) findViewById(R.id.phone_edit);
        EditText emailField = (EditText) findViewById(R.id.email_edit);

        toggleOutputContainer();

        errorLevel.set(0);
        sb.delete(0, sb.length()+1);
        sb.append("An error occurred in the following fields: ");

        if(checkInput(nameField)) {
            dataList.add(nameField.getText().toString());
        } else {
            sb.append("Name");
            errorLevel.getAndIncrement();
        }
        if(checkInput(addressField)) {
            dataList.add(addressField.getText().toString());
        } else {
            sb.append(", Address");
            errorLevel.getAndIncrement();
        }
        if(checkInput(phoneField)) {
            dataList.add(phoneField.getText().toString());
        } else {
            sb.append(", Phone");
            errorLevel.getAndIncrement();
        }
        if(checkInput(emailField)) {
            dataList.add(emailField.getText().toString());
        } else {
            sb.append(", E-mail");
            errorLevel.getAndIncrement();
        }

        if(errorLevel.get() == ErrorLevel.FOUR_ERRORS) {
            Toast.makeText(this, "Please fill in the fields", Toast.LENGTH_LONG).show();
            throw new InputVoidException("All fields are empty...");
        } else {
            String result = dataHandler.saveDataToFile(this, dataList);

            Toast.makeText(this, "Data successfully saved: " + result, Toast.LENGTH_LONG).show();

            TextView outputTextView = (TextView) findViewById(R.id.output_name_text_view);
            outputTextView.setText(result);
        }

    }

    public void onResetButtonClick(View view) {
        TextView outputTextView = (TextView) findViewById(R.id.output_name_text_view);
        outputTextView.setText("");
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
     *
     * @return String - data to be written to a file
     */
    private String stringBuilder() {

        String fullString = "";
        StringBuilder builder = new StringBuilder();

        return fullString;
    }

    /**
     * Basic field validation, that checks for empty fields
     * @param field - the Input field
     * @return true if ok, false if error
     */
    private boolean checkInput(EditText field) {
        // TODO: Expand the validation to actually validate field data, and not just for empty fields.
        //  This, however, is not the idea with the app. This is to play around with storage of data in files
        return "" != field.getText().toString();
    }
}
