package coin.tracker.zxr.data;

import coin.tracker.zxr.models.CoinListResponse;
import coin.tracker.zxr.models.PriceMultiFull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableError;

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

    @Override
    public Observable<CoinListResponse> getAllCoins() {
        return null;
    }
}
