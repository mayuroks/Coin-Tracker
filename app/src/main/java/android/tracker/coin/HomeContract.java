package android.tracker.coin;

import android.tracker.coin.common.BasePresenter;
import android.tracker.coin.common.BaseView;

import java.util.ArrayList;

/**
 * Created by Mayur on 19-09-2017.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void showTrackedCoins();

        void goToSearchCoinView();

    }

    interface Presenter extends BasePresenter {

        void getTrackedCoinData(ArrayList<String> trackedCoins);

    }

}
