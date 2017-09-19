package android.tracker.coin.webservice;

import android.tracker.coin.models.PriceMultiFull;
import android.tracker.coin.models.TestPostModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Mayur on 19-09-2017.
 */

public interface APIService {

    // FIXME change the urls
    @GET("pricemultifull")
    Observable<PriceMultiFull> getTrackedCoins(@QueryMap Map<String, Object> options);

}
