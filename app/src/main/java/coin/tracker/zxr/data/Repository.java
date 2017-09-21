package coin.tracker.zxr.data;

import android.support.annotation.NonNull;

import coin.tracker.zxr.models.CoinListResponse;
import coin.tracker.zxr.models.PriceMultiFull;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by Mayur on 19-09-2017.
 */

public class Repository implements DataSource {

    private static Repository INSTANCE = null;
    private final DataSource remoteDataSource;
    private final DataSource localdDataSource;

    private Repository(@NonNull DataSource remoteDataSource,
                       @NonNull DataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localdDataSource = localDataSource;
    }

    public static Repository getInstance(@NonNull DataSource remoteDataSource,
                                         @NonNull DataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(remoteDataSource, localDataSource);
        }

        return INSTANCE;
    }

    @Override
    public Observable<PriceMultiFull> getTrackedCoins(HashMap params) {
        return remoteDataSource.getTrackedCoins(params);
    }

    @Override
    public Observable<CoinListResponse> getAllCoins() {
        return remoteDataSource.getAllCoins();
    }
}
