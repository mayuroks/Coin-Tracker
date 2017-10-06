package coin.tracker.zxr;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class AboutMe extends BaseActivity {

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvAppName)
    TextView tvAppName;

    @BindView(R.id.tvBy)
    TextView tvBy;

    @BindView(R.id.ivMayurRokade)
    ImageView ivMayurRokade;

    @BindView(R.id.tvDescription)
    TextView tvDescription;

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
//        final float startSize = 36; // Size in pixels
//        final float endSize = 12;
//        final int animationDuration = 1000; // Animation duration in ms
//
//        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
//        animator.setDuration(animationDuration);
//
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float animatedValue = (float) valueAnimator.getAnimatedValue();
//                tvName.setTextSize(animatedValue);
//            }
//        });
//
//        animator.start();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.left_in_text);
        final Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.bottom_in_text);
        final Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.right_in_text);
        final Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        final Animation animation4 = AnimationUtils.loadAnimation(this, R.anim.bottom_in_text);

        tvAppName.startAnimation(animation);
        tvAppName.setText("Coin Tracker");

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvBy.setAnimation(animation1);
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
                tvName.startAnimation(animation2);
                tvName.setText("Mayur Rokade");
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
                ivMayurRokade.setImageDrawable(ContextCompat
                        .getDrawable(AboutMe.this, R.drawable.dev_mayur_rokade));
                ivMayurRokade.startAnimation(animation3);
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
                tvDescription.setText("I'm an Android Developer, passionate about building apps with user experience in mind.\n" +
                        "\n" +
                        "Always happy to work with people, startup or companies who are dedicated towards building great products.\n" +
                        "\n" +
                        "Quick Highlights:\n" +
                        "● Created 5+ fully functional apps for Android devices.\n" +
                        "● Written efficient, maintainable and reusable code.\n" +
                        "● Proficient in design, data structures, problem-solving and debugging.\n" +
                        "\n" +
                        "Skills:\n" +
                        "Android Development, Project Management, Agile, UI/UX Design Patterns, Development Best Practices and DevOps.");
                tvDescription.startAnimation(animation4);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
