package coin.tracker.zxr;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;

public class AboutMeActivity extends BaseActivity {
    
    @BindView(R.id.tvAppName)
    TextView tvAppName;

    @BindView(R.id.tvBy)
    TextView tvBy;

    @BindView(R.id.ivMayurRokade)
    ImageView ivMayurRokade;

    @BindView(R.id.rlAboutHeader)
    RelativeLayout rlAboutHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        initToolbar("About Me", R.drawable.ic_close);
        initUserAction("", 0, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_to_bottom);
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.left_in_text);
        final Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.right_in_text);
        final Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.bottom_in_text);
        final Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.top_out_text);

        tvAppName.startAnimation(animation);
        tvAppName.setText("Coin Tracker");

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvBy.startAnimation(animation1);
                tvBy.setText("by");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rlAboutHeader.startAnimation(animation2);
                rlAboutHeader.setAlpha(1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvAppName.startAnimation(animation3);
                tvBy.startAnimation(animation3);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvAppName.setAlpha(0);
                tvAppName.setVisibility(View.GONE);
                tvBy.setAlpha(0);
                tvBy.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
