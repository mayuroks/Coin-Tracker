package android.tracker.coin;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // in this example, a LineChart is initialized from xml
        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setViewPortOffsets(0, 0, 0, 0);



        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1,1));
        entries.add(new Entry(2,4));
        entries.add(new Entry(3,5));
        entries.add(new Entry(4,9));
        entries.add(new Entry(5,13));

        LineDataSet dataSet = new LineDataSet(entries, "Label yoyo"); // add entries to dataset
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setColor(ContextCompat.getColor(getBaseContext(), R.color.colorGraph));
        dataSet.setValueTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorGraphText)); // styling, ...

        YAxis y = chart.getAxisLeft();
        y.setDrawGridLines(false);


        LineData lineData = new LineData(dataSet);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.getLegend().setEnabled(false);
        chart.animateXY(2000, 2000);
        chart.setData(lineData);


        chart.invalidate(); // refresh

        // TODO: Test Remove this
        goToChartActivity();

    }

    private void goToChartActivity() {
        startActivity(new Intent(this, CubicLineChartActivity.class));
    }
}
