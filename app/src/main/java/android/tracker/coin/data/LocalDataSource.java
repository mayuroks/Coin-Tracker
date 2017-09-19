package android.tracker.coin.data;

import android.tracker.coin.models.PriceMultiFull;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by Mayur on 19-09-2017.
 */

public class LocalDataSource implements DataSource {
    private static LocalDataSource INSTANCE;

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource();
        }

        return INSTANCE;
    }

    @Override
    public Observable<PriceMultiFull> getTrackedCoins(HashMap params) {
        return null;
    }
}
