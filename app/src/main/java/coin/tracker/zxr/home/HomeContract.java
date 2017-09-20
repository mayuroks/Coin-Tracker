package coin.tracker.zxr.home;

import coin.tracker.zxr.common.BasePresenter;
import coin.tracker.zxr.common.BaseView;
import coin.tracker.zxr.models.PriceMultiFull;

import java.util.HashMap;

/**
 * Created by Mayur on 19-09-2017.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void showTrackedCoins(PriceMultiFull priceMultiFull);

        void goToSearchCoinView();

    }

    interface Presenter extends BasePresenter {

        void getTrackedCoinData(HashMap params);

    }

}
