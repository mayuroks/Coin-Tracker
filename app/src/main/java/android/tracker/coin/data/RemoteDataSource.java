package android.tracker.coin.data;

import android.tracker.coin.models.PriceMultiFull;
import android.tracker.coin.webservice.APIService;
import android.tracker.coin.webservice.RestClient;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by Mayur on 19-09-2017.
 */

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE;
    APIService service = RestClient.getAPIService();

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }

        return INSTANCE;
    }

    @Override
    public Observable<PriceMultiFull> getTrackedCoins(HashMap params) {
        return service.getTrackedCoins(params);
    }
}
