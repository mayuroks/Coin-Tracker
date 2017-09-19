package android.tracker.coin;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Mayur on 19-09-2017.
 */

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        Logger.addLogAdapter(new AndroidLogAdapter());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/lato_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
