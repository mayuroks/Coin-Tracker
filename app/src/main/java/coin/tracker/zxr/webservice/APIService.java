package coin.tracker.zxr.webservice;

import coin.tracker.zxr.models.CoinListResponse;
import coin.tracker.zxr.models.PriceMultiFull;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Mayur on 19-09-2017.
 */

public interface APIService {

    @GET("pricemultifull")
    Observable<PriceMultiFull> getTrackedCoins(@QueryMap Map<String, Object> options);

    @GET("coinlist/")
    Observable<CoinListResponse> getAllCoins();

}
