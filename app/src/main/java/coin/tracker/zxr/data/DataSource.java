package coin.tracker.zxr.data;

import coin.tracker.zxr.models.PriceMultiFull;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by Mayur on 19-09-2017.
 */

public interface DataSource {

    Observable<PriceMultiFull> getTrackedCoins(HashMap params);

}
