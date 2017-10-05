package coin.tracker.zxr;

import android.os.Bundle;

public class AboutMe extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        initToolbar("About me", R.drawable.ic_close);
        initUserAction("", 0, false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_to_bottom);
    }
}
