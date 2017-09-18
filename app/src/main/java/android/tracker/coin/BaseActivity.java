package android.tracker.coin;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvToolbarTitle;
    private RelativeLayout baseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        baseLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        setContentView(baseLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        setSupportActionBar(toolbar);
        ViewStub stub = (ViewStub) baseLayout.findViewById(R.id.container);
        stub.setLayoutResource(layoutResID);
        stub.inflate();
        // Butterknife.bind(this);
    }

    public void initToolbar(String title) {
        tvToolbarTitle.setText(title);
    }
}
