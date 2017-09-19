package android.tracker.coin;

import android.os.Bundle;
import android.tracker.coin.data.LocalDataSource;
import android.tracker.coin.data.RemoteDataSource;
import android.tracker.coin.data.Repository;
import android.tracker.coin.models.DisplayPrice;
import android.tracker.coin.models.PriceMultiFull;
import android.tracker.coin.utils.Injection;
import android.tracker.coin.utils.schedulers.SchedulerProvider;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    HomeContract.Presenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new HomePresenterImpl(this,
                Injection.provideSchedulerProvider(),
                Injection.providesRepository(this));
    }

    @Override
    public void initView() {
        initToolbar("Coin Tracker");

        // FIXME save user coins and get them from local DB
//        ArrayList<String> coins = new ArrayList<>();
//        coins.add("BTC");
//        coins.add("ETH");
//        coins.add("LTC");

        HashMap params = new HashMap();
        params.put("fsyms", "BTC,ETH,LTC");
        params.put("tsyms", "INR");
        Logger.i("initView");
        presenter.getTrackedCoinData(params);

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showTrackedCoins() {

    }

    @Override
    public void goToSearchCoinView() {

    }

    private void testAPICall() {
        Logger.i("testAPICall called");
        Repository repository = Injection.providesRepository(this);
        SchedulerProvider schedulerProvider = SchedulerProvider.getInstance();

        HashMap params = new HashMap();
        params.put("fsyms", "BTC,ETH,LTC");
        params.put("tsyms", "INR");

        repository.getTrackedCoins(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new Observer<PriceMultiFull>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PriceMultiFull priceMultiFull) {
                        Logger.i("tsyms onNext");
                        HashMap map = priceMultiFull.getDISPLAY();
                        DisplayPrice price = (DisplayPrice) map.get("BTC");
                        Logger.i(price.getPRICE());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.i("tsyms error");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
