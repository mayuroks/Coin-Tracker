package coin.tracker.zxr;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import coin.tracker.zxr.home.HomeActivity;
import coin.tracker.zxr.models.CoinListResponse;
import coin.tracker.zxr.utils.CoinHelper;
import coin.tracker.zxr.utils.Injection;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.tvAppName)
    TextView tvAppName;

    @BindView(R.id.ivLogo)
    ImageView ivLogo;

    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        initUserAction("", 0, false);
        CoinHelper.getInstance().prePopulateUserCoins();
        getAllCoins();
        startSplashAnimation();
    }

    private void goToCubicLineChart() {
        timer = new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                overridePendingTransition(0, R.anim.fade_out);
                finish();
            }
        };

        timer.start();
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

    private void startSplashAnimation() {
        ivLogo.animate()
                .setDuration(1000)
                .alpha(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tvAppName.animate()
                                .setDuration(500)
                                .alpha(1f)
                                .translationY(-450f)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        goToCubicLineChart();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                })
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }
}
