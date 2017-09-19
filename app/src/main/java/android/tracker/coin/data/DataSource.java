package android.tracker.coin.data;

import android.tracker.coin.models.PriceMultiFull;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by Mayur on 19-09-2017.
 */

public interface DataSource {

    Observable<PriceMultiFull> getTrackedCoins(HashMap params);

}
