package coin.tracker.zxr.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import coin.tracker.zxr.BaseActivity;
import coin.tracker.zxr.R;
import coin.tracker.zxr.data.Repository;
import coin.tracker.zxr.models.CoinListResponse;
import coin.tracker.zxr.models.DisplayPrice;
import coin.tracker.zxr.models.PriceMultiFull;
import coin.tracker.zxr.models.RawPrice;
import coin.tracker.zxr.search.SearchCoinsActivity;
import coin.tracker.zxr.utils.CoinHelper;
import coin.tracker.zxr.utils.FontManager;
import coin.tracker.zxr.utils.Injection;
import coin.tracker.zxr.utils.schedulers.SchedulerProvider;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @BindView(R.id.rvMyCoins)
    RecyclerView rvMyCoins;

    MyCoinsAdapter myCoinsAdapter;
    LinearLayoutManager layoutManager;
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
        setupActionButton();

        HashMap params = new HashMap();
        ArrayList<String> coinsList = CoinHelper.getInstance().getAllUserCoins();
        String coins = android.text.TextUtils.join(",", coinsList);
        params.put("fsyms", coins);
        params.put("tsyms", "INR");
        Logger.i("initView");
        presenter.getTrackedCoinData(params);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showTrackedCoins(PriceMultiFull priceMultiFull) {
        PriceMultiFull price = priceMultiFull;
        HashMap display = priceMultiFull.getDISPLAY();
        ArrayList<DisplayPrice> displayPrices = new ArrayList<>();
        ArrayList<RawPrice> rawPrices = new ArrayList<>();

        if (display.size() > 0) {
            Logger.i("display size > 0");
            displayPrices = priceMultiFull.getDisplayPrices();
            rawPrices = priceMultiFull.getRawPrices();

            myCoinsAdapter = new MyCoinsAdapter(this, displayPrices, rawPrices);
            layoutManager = new LinearLayoutManager(this);
            rvMyCoins.setAdapter(myCoinsAdapter);
            rvMyCoins.setLayoutManager(layoutManager);
            rvMyCoins.setNestedScrollingEnabled(false);

            RecyclerView.ItemDecoration dividerItemDecoration =
                    new RVDividerItemDecoration(ContextCompat.getDrawable(this,
                            R.drawable.bg_rv_separator));
            rvMyCoins.addItemDecoration(dividerItemDecoration);
        } else {
            // TODO show meaningful error
        }
    }

    @Override
    public void goToSearchCoinView() {
        startActivity(new Intent(this, SearchCoinsActivity.class));
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

    private void setupActionButton() {
        TextView tvActionButton = (TextView) findViewById(R.id.tvActionButton);
        TextView tvActionDescription = (TextView) findViewById(R.id.tvActionDescription);
        Typeface fontawesome = FontManager.getTypeface(this, FontManager.FONTMATERIAL);
        FontManager.setTypeface(tvActionButton, fontawesome);

        tvActionButton.setText(getResources().getString(R.string.material_icon_plus));
        tvActionDescription.setText("Add a coin");

        tvActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchCoinView();
            }
        });
    }
}
