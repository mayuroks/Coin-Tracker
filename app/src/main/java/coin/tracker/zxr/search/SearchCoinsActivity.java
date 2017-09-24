package coin.tracker.zxr.search;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;
import com.orhanobut.logger.Logger;

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
    boolean isUserScrolling = false;
    int visibleThreshold = 5;
    ArrayList<String> testcoins;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_coins);
        getSupportActionBar().hide();
        setupActionButton();
        setupAllCoins();
    }

    private void setupAllCoins() {
        ArrayList<String> coins = CoinHelper.getInstance().getCachedCoinsByPage(0);

        /*
        * TODO give refresh option to user to
        * call the API, save and load coins
        * */
        if (coins.size() == 0) {
            return; //
        }

        allCoinsAdapter = new AllCoinsAdapter(this, coins);
        layoutManager = new LinearLayoutManager(this);
        rvAllCoins.setAdapter(allCoinsAdapter);
        rvAllCoins.setLayoutManager(layoutManager);
        rvAllCoins.setHasFixedSize(true);
        rvAllCoins.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration dividerItemDecoration =
                new RVDividerItemDecoration(ContextCompat.getDrawable(this,
                        R.drawable.bg_rv_separator));
        rvAllCoins.addItemDecoration(dividerItemDecoration);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Logger.i("COINLIST reached end page " + page);
                Toast.makeText(SearchCoinsActivity.this,
                        "Page reached " + Integer.toString(page),
                        Toast.LENGTH_LONG)
                        .show();
                loadNextData(page);
            }
        };

        rvAllCoins.addOnScrollListener(scrollListener);
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

    private void loadNextData(int page) {
        ArrayList<String> newCoins = CoinHelper.getInstance().getCachedCoinsByPage(page);
        int count = allCoinsAdapter.getItemCount();
        allCoinsAdapter.addItems(newCoins);
        int newCount = allCoinsAdapter.getItemCount();
        allCoinsAdapter.notifyItemRangeInserted(count, newCount - count);
    }
}
