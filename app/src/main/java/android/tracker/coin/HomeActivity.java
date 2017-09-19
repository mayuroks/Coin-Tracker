package android.tracker.coin;

import android.os.Bundle;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initToolbar("Coin Tracker");
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }

    @Override
    public void showTrackedCoins() {

    }

    @Override
    public void goToSearchCoinView() {

    }
}
