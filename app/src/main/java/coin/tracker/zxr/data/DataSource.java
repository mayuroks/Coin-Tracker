package coin.tracker.zxr.data;

import coin.tracker.zxr.models.CoinListResponse;
import coin.tracker.zxr.models.PriceDetailsResponse;
import coin.tracker.zxr.models.PriceMultiFull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableError;

/**
 * Created by Mayur on 19-09-2017.
 */

public interface DataSource {

    Observable<PriceMultiFull> getTrackedCoins(HashMap params);

    Observable<CoinListResponse> getAllCoins();

    Observable<PriceDetailsResponse> getCoinDetails(HashMap params);

}
