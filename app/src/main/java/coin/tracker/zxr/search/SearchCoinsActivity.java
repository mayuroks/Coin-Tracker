package coin.tracker.zxr.search;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;
import com.orhanobut.logger.Logger;

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
    boolean isUserScrolling = false;
    int visibleThreshold = 5;
    HashMap<String, String> testcoins;

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

        // FIXME remove this
        testcoins = new HashMap<String, String>() {
            {
                put("LTC", "Litecoin");
                put("BTC", "Bitcoin");
                put("ETH", "Ethereum");
                put("WINK", "Wink");
                put("YOC", "YoCoin");
                put("IOC", "IOCoin");
                put("WIN", "Win");
                put("YOC1", "YoCoin1");
                put("IOC2", "IOCoin2");
                put("WINK3", "Wink3");
                put("YOC4", "YoCoin4");
                put("IOC5", "IOCoin5");
                put("WINK6", "Wink6");
                put("YOC7", "YoCoin7");
                put("IOC8", "IOCoin8");
                put("WINK9", "Wink9");
            }
        };
        allCoinsAdapter = new AllCoinsAdapter(this, testcoins);
//        allCoinsAdapter = new AllCoinsAdapter(this, coins);
        layoutManager = new LinearLayoutManager(this);
        rvAllCoins.setAdapter(allCoinsAdapter);
        rvAllCoins.setLayoutManager(layoutManager);
        rvAllCoins.setHasFixedSize(true);
        rvAllCoins.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration dividerItemDecoration =
                new RVDividerItemDecoration(ContextCompat.getDrawable(this,
                        R.drawable.bg_rv_separator));
        rvAllCoins.addItemDecoration(dividerItemDecoration);
        rvAllCoins.addOnScrollListener(createInfiniteScrollListener());
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

    private InfiniteScrollListener createInfiniteScrollListener() {
        return new InfiniteScrollListener(10, layoutManager) {
            @Override public void onScrolledToEnd(final int firstVisibleItemPosition) {
                Logger.i("COINLIST onScrolledToEnd");
                // load your items here
                // logic of loading items will be different depending on your specific use case

                // when new items are loaded, combine old and new items, pass them to your adapter
                // and call refreshView(...) method from InfiniteScrollListener class to refresh RecyclerView
                // FIXME remove this
                HashMap<String, String> newCoins = new HashMap<String, String>() {
                    {
                        put("XRP", "Ripple");
                        put("DOGE", "Dogecoin");
                    }
                };

                testcoins.putAll(newCoins);
                refreshView(rvAllCoins,
                        new AllCoinsAdapter(SearchCoinsActivity.this, testcoins),
                        firstVisibleItemPosition);
            }
        };
    }
}
