package coin.tracker.zxr.utils;

/**
 * Created by Mayur on 22-09-2017.
 */

public class TextUtils {

    public static final String IN_FORMAT  = "##,##,###.##";
    public static final String IN_FORMAT_ROUNDED_OFF  = "##,##,###";

    public static boolean isValidString(String str) {
        if (str != null && str.length() > 0 &&
                !str.isEmpty() && !str.equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }
}
