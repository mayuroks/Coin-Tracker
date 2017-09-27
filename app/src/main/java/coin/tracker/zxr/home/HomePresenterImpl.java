package coin.tracker.zxr.home;

import coin.tracker.zxr.data.Repository;
import coin.tracker.zxr.models.PriceMultiFull;
import coin.tracker.zxr.utils.schedulers.BaseSchedulerProvider;

import com.orhanobut.logger.Logger;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Mayur on 19-09-2017.
 */

public class HomePresenterImpl implements HomeContract.Presenter {

    @NonNull
    HomeContract.View view;

    @NonNull
    Repository repository;

    @NonNull
    BaseSchedulerProvider schedulerProvider;

    public HomePresenterImpl(@NonNull HomeContract.View view,
                             @NonNull BaseSchedulerProvider schedulerProvider,
                             @NonNull Repository repository) {
        this.view = view;
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
        view.setPresenter(this);
        view.initView();
    }

    @Override
    public void subscriber() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getTrackedCoinData(HashMap params) {
        Logger.i("getTrackedCoinData method called");
        view.showErrorMsg(false);
        view.showProgress();

        repository.getTrackedCoins(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new Observer<PriceMultiFull>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PriceMultiFull priceMultiFull) {
                        Logger.i("onNext");
                        view.hideProgress();
                        view.showTrackedCoins(priceMultiFull);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.i("error");
                        view.hideProgress();
                        view.showErrorMsg(true);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
