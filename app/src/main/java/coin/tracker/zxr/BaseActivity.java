package coin.tracker.zxr;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import coin.tracker.zxr.R;

import coin.tracker.zxr.webservice.APIService;
import coin.tracker.zxr.webservice.RestClient;
import android.view.LayoutInflater;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvToolbarTitle;
    private RelativeLayout baseLayout;
    public APIService service = RestClient.getAPIService();

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
        ButterKnife.bind(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void initToolbar(String title) {
        tvToolbarTitle.setText(title);
    }
}
