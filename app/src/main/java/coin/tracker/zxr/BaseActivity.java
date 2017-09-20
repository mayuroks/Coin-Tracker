package coin.tracker.zxr;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import coin.tracker.zxr.webservice.APIService;
import coin.tracker.zxr.webservice.RestClient;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {

    private TextView tvActionButton;
    private TextView tvActionDescription;
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
        tvActionButton = (TextView) findViewById(R.id.tvActionButton);
        tvActionDescription = (TextView) findViewById(R.id.tvActionDescription);
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

    public static boolean isValidString(String str) {
        if (str != null && str.length() > 0 &&
                !str.isEmpty() && !str.equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }
}
