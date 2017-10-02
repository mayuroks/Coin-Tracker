package coin.tracker.zxr.utils;

import android.content.Context;
import coin.tracker.zxr.data.LocalDataSource;
import coin.tracker.zxr.data.RemoteDataSource;
import coin.tracker.zxr.data.Repository;
import coin.tracker.zxr.utils.schedulers.BaseSchedulerProvider;
import coin.tracker.zxr.utils.schedulers.SchedulerProvider;

/**
 * Created by Mayur on 19-09-2017.
 */

public class Injection {
    public static Repository providesRepository(Context context) {
        return Repository.getInstance(RemoteDataSource.getInstance(),
                LocalDataSource.getInstance());
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
