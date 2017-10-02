package coin.tracker.zxr.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mayur on 20-09-2017.
 */

public class FontManager {
    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "FontAwesome.otf",
    FONTMATERIAL = ROOT + "Material-Design-Iconic-Font.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public static void setTypeface(View v, Typeface typeface) {
        if (v != null && typeface != null) {
            ((TextView) v).setTypeface(typeface);
        }
    }
}
