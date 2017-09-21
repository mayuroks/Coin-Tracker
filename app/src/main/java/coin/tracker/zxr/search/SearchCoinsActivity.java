package coin.tracker.zxr.search;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import coin.tracker.zxr.BaseActivity;
import coin.tracker.zxr.R;
import coin.tracker.zxr.models.CoinListItem;
import coin.tracker.zxr.utils.CoinHelper;
import coin.tracker.zxr.utils.FontManager;

public class SearchCoinsActivity extends BaseActivity {

    @BindView(R.id.rvAllCoins)
    RecyclerView rvAllCoins;

    AllCoinsAdapter allCoinsAdapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_coins);
        setupActionButton();
        // FIXME consumes too much memory and start GC
//        setupAllCoins();
    }

    private void setupAllCoins() {
        HashMap<String, String> coins = CoinHelper.getInstance().getAllCachedCoins();

        if (coins.size() == 0) {
            return; // TODO call the API, save and load coins
        }

        allCoinsAdapter = new AllCoinsAdapter(this, coins);
        layoutManager = new LinearLayoutManager(this);
        rvAllCoins.setAdapter(allCoinsAdapter);
        rvAllCoins.setLayoutManager(layoutManager);
    }

    private void setupActionButton() {
        TextView tvActionButton = (TextView) findViewById(R.id.tvActionButton);
        TextView tvActionDescription = (TextView) findViewById(R.id.tvActionDescription);
        Typeface fontawesome = FontManager.getTypeface(this, FontManager.FONTMATERIAL);
        FontManager.setTypeface(tvActionButton, fontawesome);

        tvActionButton.setText(getResources().getString(R.string.material_icon_plus));
        tvActionDescription.setText("Add a coin");

        tvActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
