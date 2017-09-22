package coin.tracker.zxr.search;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import coin.tracker.zxr.BaseActivity;
import coin.tracker.zxr.R;
import coin.tracker.zxr.home.RVDividerItemDecoration;
import coin.tracker.zxr.utils.CoinHelper;
import coin.tracker.zxr.utils.FontManager;

public class SearchCoinsActivity extends BaseActivity implements SearchCoinListener {

    @BindView(R.id.rvAllCoins)
    RecyclerView rvAllCoins;

    @BindView(R.id.tvSelectedCoinsCount)
    TextView tvSelectedCoinsCount;

    AllCoinsAdapter allCoinsAdapter;
    LinearLayoutManager layoutManager;
    HashMap<String, String> selectedCoins = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_coins);
        getSupportActionBar().hide();
        setupActionButton();
        // FIXME consumes too much memory and start GC
        setupAllCoins();
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
        rvAllCoins.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration dividerItemDecoration =
                new RVDividerItemDecoration(ContextCompat.getDrawable(this,
                        R.drawable.bg_rv_separator));
        rvAllCoins.addItemDecoration(dividerItemDecoration);

        rvAllCoins.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setupActionButton() {
        TextView tvActionButton = (TextView) findViewById(R.id.tvActionButton);
        TextView tvActionDescription = (TextView) findViewById(R.id.tvActionDescription);
        Typeface fontawesome = FontManager.getTypeface(this, FontManager.FONTMATERIAL);
        FontManager.setTypeface(tvActionButton, fontawesome);

        tvActionButton.setText(getResources().getString(R.string.material_icon_done));
        tvActionDescription.setText("Done");

        tvActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String coinTag : selectedCoins.keySet()) {
                    CoinHelper.getInstance()
                            .addUserCoin(coinTag, selectedCoins.get(coinTag));
                }

                finish();
            }
        });
    }

    @Override
    public void onCoinSelected(String coinTag, String coinName) {
        selectedCoins.put(coinTag, coinName);
        tvSelectedCoinsCount.setText("Selected Coins(" +
                Integer.toString(selectedCoins.size()) + ")");
    }

    @Override
    public void onCoinUnselected(String coinTag, String coinName) {
        if (selectedCoins.containsKey(coinTag)) {
            selectedCoins.remove(coinTag);
            tvSelectedCoinsCount.setText("Selected Coins(" +
                    Integer.toString(selectedCoins.size()) + ")");
        }
    }
}
