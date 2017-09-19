package android.tracker.coin.utils;

import android.content.Context;
import android.tracker.coin.data.LocalDataSource;
import android.tracker.coin.data.RemoteDataSource;
import android.tracker.coin.data.Repository;
import android.tracker.coin.utils.schedulers.BaseSchedulerProvider;
import android.tracker.coin.utils.schedulers.SchedulerProvider;

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
