package dk.nyvang.android.myandroidstorage;

import androidx.annotation.Nullable;

import java.util.InputMismatchException;

public class InputVoidException extends InputMismatchException {

    public InputVoidException() {
        super();
    }

    public InputVoidException(String s) {
        super(s);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Nullable
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
