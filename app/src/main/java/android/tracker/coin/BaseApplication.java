package android.tracker.coin;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Mayur on 19-09-2017.
 */

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("APPLICATION", "onCreate");
        MultiDex.install(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/lato_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
