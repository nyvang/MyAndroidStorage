package dk.nyvang.android.myandroidstorage;

import android.content.Context;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    private Context app;

    private String fileDirName;
    private String filePath;

    private static final String EXPECTED_INPUT_FILE_NAME = "dataFile.txt";
    private static final String EXPECTED_FILE_DIR = "files";
    private static final String EXPECTED_APP_ROOT_PACKAGE = "dk.nyvang.android.myandroidstorage";
    private static final String EXPECTED_ABSOLUTE_FILEPATH = "/data/user/0/" + EXPECTED_APP_ROOT_PACKAGE + "/" + EXPECTED_FILE_DIR;
    private static final String EXPECTED_INPUT_NAME = "Mr. Test ";
    private static final String EXPECTED_INPUT_ADDRESS = "TestAddress 2, 1234 CityOfTests ";
    private static final String EXPECTED_INPUT_PHONE = "65432198 ";
    private static final String EXPECTED_INPUT_EMAIL = "mr_test@test.com";
    private static final String EXPECTED_SAVED_COMBINED_STRING =
                                                    EXPECTED_INPUT_NAME +
                                                    EXPECTED_INPUT_ADDRESS +
                                                    EXPECTED_INPUT_PHONE +
                                                    EXPECTED_INPUT_EMAIL;
    private static final int EXPECTED_TARGET_SDK_VERSION = 29;

    @Before
    public void setUp(){
        app = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //mainActivity = mainActivityRule.getActivity();

        setUpFilePaths();

        inputTestData();
    }

    @After
    public void tearDown() throws Exception {
        app.deleteFile(EXPECTED_INPUT_FILE_NAME);
    }

    @Test
    public void onCreate() {
        onView(withId(R.id.background)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_table_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void settings() {
        assertEquals("dk.nyvang.android.myandroidstorage", app.getPackageName());
        assertEquals(EXPECTED_TARGET_SDK_VERSION, app.getApplicationInfo().targetSdkVersion);
    }

    @Test
    public void fileNameAndPaths() {
        assertEquals(EXPECTED_FILE_DIR, fileDirName);
        assertEquals(EXPECTED_ABSOLUTE_FILEPATH, filePath);
    }

    @Test
    public void buttonContainerAndButtonViews() {
        onView(withId(R.id.button_view)).check(matches(isDisplayed()));
        onView(withId(R.id.button_reset)).check(matches(isDisplayed()));
        onView(withId(R.id.button_save)).check(matches(isDisplayed()));
    }

    @Test
    public void inputFieldViews() {
        onView(withId(R.id.name_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.address_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.phone_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.email_edit)).check(matches(isDisplayed()));
    }

    @Test
    public void readSavedInternalData() throws IOException {

        performGetDataButtonClick();

        StringBuilder dataReadFromInternalStorage;
        try (FileInputStream fileInputStream = app.openFileInput(EXPECTED_INPUT_FILE_NAME)) {

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            dataReadFromInternalStorage = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                dataReadFromInternalStorage.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
        }

        assertEquals(EXPECTED_SAVED_COMBINED_STRING, dataReadFromInternalStorage.toString());
    }

    /**
     * Grab the file path and the top level directory in order to validate these
     * with the expected result.
     */
    private void setUpFilePaths() {
        File fileListDir = app.getFilesDir();
        fileDirName = fileListDir.getName();
        filePath = fileListDir.getAbsolutePath();
    }

    /**
     * Populate fields for saving the input data in order to save the data to the internal storage.
     * The saved data will then be read and compared to the expected result.
     */
    private void inputTestData() {
        onView(withId(R.id.name_edit))
                .perform(ViewActions.replaceText("Mr. Test "));

        onView(withId(R.id.address_edit))
                .perform(replaceText("TestAddress 2, 1234 CityOfTests "));

        onView(withId(R.id.phone_edit))
                .perform(replaceText("65432198 "));

        onView(withId(R.id.email_edit))
                .perform(replaceText("mr_test@test.com"), closeSoftKeyboard());

        onView(withId(R.id.button_save)).perform(click());
    }

    /**
     * Get the saved data to be used in the <code>readSavedInternalData()</code> test
     */
    public void performGetDataButtonClick() {
        onView(withId(R.id.button_reset)).perform(click());
    }
}