package coin.tracker.zxr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import coin.tracker.zxr.home.HomeActivity;
import coin.tracker.zxr.utils.CoinHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CoinHelper.getInstance().init();
        goToCubicLineChart();
    }

    private void goToCubicLineChart() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
