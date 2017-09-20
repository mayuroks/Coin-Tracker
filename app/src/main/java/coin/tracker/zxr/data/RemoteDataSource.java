package coin.tracker.zxr.data;

import coin.tracker.zxr.models.PriceMultiFull;
import coin.tracker.zxr.webservice.APIService;
import coin.tracker.zxr.webservice.RestClient;

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
