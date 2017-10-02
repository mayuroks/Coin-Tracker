package coin.tracker.zxr;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import coin.tracker.zxr.R;
import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import coin.tracker.zxr.utils.CoinHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Mayur on 19-09-2017.
 */

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        Hawk.init(getBaseContext()).build();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/lato_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
