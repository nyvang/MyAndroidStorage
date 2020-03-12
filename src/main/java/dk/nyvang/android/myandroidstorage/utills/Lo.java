package dk.nyvang.android.myandroidstorage.utills;

import android.util.Log;

import dk.nyvang.android.myandroidstorage.MainActivity;

/**
 * Should handle all logs and, most important, has the flag telling the app to either enable or
 * disable all log statements within the code.
 */
public class Lo {

    public static void g(LogLevels level, String origin, String msg) {

        if (MainActivity.IS_GLOBAL_APPLICATION_LOGGING_ACTIVE) {
            switch (level) {
                case LEVEL_VERBOSE:
                    Log.v(origin, msg);
                case LEVEL_INFO:
                    Log.i(origin, msg);
                case LEVEL_DEBUG:
                    Log.d(origin, msg);
                case LEVEL_WARNING:
                    Log.w(origin, msg);
                case LEVEL_ERROR:
                    Log.e(origin, msg);
                default:
                    Log.println(Log.ASSERT, origin, msg);
            }
        }
    }
}
