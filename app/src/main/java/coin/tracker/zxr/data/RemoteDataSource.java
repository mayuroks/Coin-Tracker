package coin.tracker.zxr.data;

import coin.tracker.zxr.models.CoinListResponse;
import coin.tracker.zxr.models.PriceMultiFull;
import coin.tracker.zxr.webservice.APIService;
import coin.tracker.zxr.webservice.RestClient;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableError;

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

    @Override
    public Observable<CoinListResponse> getAllCoins() {
        return service.getAllCoins();
    }
}
