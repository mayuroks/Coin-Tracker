package android.tracker.coin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        goToCubicLineChart();
    }

    private void goToCubicLineChart() {
        startActivity(new Intent(this, CubicLineChartActivity.class));
    }
}
