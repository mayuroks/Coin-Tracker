package coin.tracker.zxr.utils;

/**
 * Created by Mayur on 22-09-2017.
 */

public class TextUtils {
    public static boolean isValidString(String str) {
        if (str != null && str.length() > 0 &&
                !str.isEmpty() && !str.equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }
}
