package coin.tracker.zxr;

import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

import coin.tracker.zxr.home.HomeActivity;
import coin.tracker.zxr.models.CoinListResponse;
import coin.tracker.zxr.utils.CoinHelper;
import coin.tracker.zxr.utils.Injection;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CoinHelper.getInstance().prePopulateUserCoins();
        getAllCoins();
        goToCubicLineChart();
    }

    private void goToCubicLineChart() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void getAllCoins() {
        if (CoinHelper.getInstance().getAllCachedCoins().size() > 0) return;

        service.getAllCoins()
                .subscribeOn(Injection.provideSchedulerProvider().io())
                .observeOn(Injection.provideSchedulerProvider().computation())
                .subscribe(new Observer<CoinListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CoinListResponse coinListResponse) {
                        Logger.i("getAllCoins", "getAllCoins done");
                        CoinHelper.getInstance()
                                .updateAllCachedCoins(coinListResponse.getData(), true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.i("getAllCoins", "getAllCoins error");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
