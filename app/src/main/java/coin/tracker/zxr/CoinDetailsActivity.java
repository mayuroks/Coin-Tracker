package coin.tracker.zxr;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import coin.tracker.zxr.common.BaseView;
import coin.tracker.zxr.data.Repository;
import coin.tracker.zxr.models.PriceDetailsResponse;
import coin.tracker.zxr.models.PricePoint;
import coin.tracker.zxr.utils.CoinHelper;
import coin.tracker.zxr.utils.Injection;
import coin.tracker.zxr.utils.TextUtils;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class CoinDetailsActivity extends BaseActivity implements BaseView {

    @BindView(R.id.chart)
    LineChart mChart;

    @BindView(R.id.tvPriceTimeline)
    TextView tvPriceTimeline;

    @BindView(R.id.tvCoinName)
    TextView tvCoinName;

    @BindView(R.id.tvPrice)
    TextView tvPrice;

    @BindView(R.id.aviLoader)
    AVLoadingIndicatorView aviLoader;

    String coinTag, coinName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detail);
        initToolbar("Coin Details", R.drawable.ic_back_arrow);
        initUserAction("", 0, false);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            coinTag = bundle.getString("coinTag", "");
            coinName = bundle.getString("coinName", "");
        }

        tvCoinName.setText(coinName);

        mChart = (LineChart) findViewById(R.id.chart);
//        mChart.setPadding(4,4,4,4);

        // FIXME calculate offset for right
        mChart.setViewPortOffsets(6, 30, 90, 30);

        // no description text
        mChart.getDescription().setEnabled(false);
        mChart.setNoDataText("");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);

        XAxis x = mChart.getXAxis();
        x.setTextColor(ContextCompat.getColor(this, R.color.colorText));
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setAxisLineColor(Color.TRANSPARENT);
        x.setDrawGridLines(false);
        x.setAxisLineWidth(0f);

        YAxis y = mChart.getAxisRight();
//        y.setTypeface();
        y.setLabelCount(6, false);
        y.setTextColor(ContextCompat.getColor(this, R.color.colorText));
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.TRANSPARENT);
        y.setAxisLineWidth(0f);

        mChart.getAxisLeft().setEnabled(false);

        // add data
        getCoinDetails(coinTag);

//        setData(45, 100);
        mChart.getLegend().setEnabled(false);
        mChart.animateX(1500);

        // dont forget to refresh the drawing
//        mChart.invalidate();
    }

    private void setData(ArrayList<PricePoint> pricePoints) {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        SimpleDateFormat format = new SimpleDateFormat("MMMM, yyyy");
        String monthStr = format.format(calendar.getTime());
        Logger.i("PRICEPOINT " + monthStr);
        tvPriceTimeline.setText("Price - " + monthStr);
        PricePoint latestPoint = pricePoints.get(pricePoints.size() - 1);
        DecimalFormat decimalFormat = new DecimalFormat(TextUtils.IN_FORMAT);
        String latestPrice = decimalFormat.format(latestPoint.getClose());
        tvPrice.setText(getString(R.string.rupee_symbol) + " " + latestPrice);

        for (PricePoint point : pricePoints) {
            calendar.setTimeInMillis(point.getTime() * 1000);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            if (month == calendar.get(Calendar.MONTH)) {
                yVals.add(new Entry(day, point.getClose()));
            }
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(2f);
            set1.setCircleRadius(4f);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(ContextCompat.getColor(this, R.color.colorGraph));
            set1.setFillAlpha(0);
            set1.setDrawFilled(true);
            set1.setFillColor(ContextCompat.getColor(this, R.color.colorGraph));
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data = new LineData(set1);
//            data.setValueTypeface(mTfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);

            // dont forget to refresh the drawing
            mChart.invalidate();
        }
    }

    private void getCoinDetails(String coinTag) {
        Repository repository = Injection.providesRepository(this);
        HashMap params = new HashMap();
        Calendar calendar = Calendar.getInstance();

        params.put("fsym", coinTag);
        params.put("tsym", "INR");
        params.put("limit", "30");
        params.put("toTs", calendar.getTimeInMillis() / 1000);

        showProgress();
        repository.getCoinDetails(params)
                .observeOn(Injection.provideSchedulerProvider().ui())
                .subscribeOn(Injection.provideSchedulerProvider().io())
                .subscribe(new Observer<PriceDetailsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PriceDetailsResponse priceDetailsResponse) {
                        ArrayList<PricePoint> pricePoints = priceDetailsResponse.getData();
                        Logger.i("PRICEPOINT size " + pricePoints.size());
                        hideProgress();
                        setData(pricePoints);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideProgress();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void initView() {

    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void showProgress() {
        aviLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        aviLoader.setVisibility(View.GONE);
    }
}
