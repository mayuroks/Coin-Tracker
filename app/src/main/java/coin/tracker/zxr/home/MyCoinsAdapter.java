package coin.tracker.zxr.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import coin.tracker.zxr.BaseActivity;
import coin.tracker.zxr.R;
import coin.tracker.zxr.models.DisplayPrice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import coin.tracker.zxr.models.RawPrice;
import coin.tracker.zxr.utils.CoinHelper;
import coin.tracker.zxr.utils.TextUtils;

/**
 * Created by Mayur on 19-09-2017.
 */

public class MyCoinsAdapter extends RecyclerView.Adapter<MyCoinsAdapter.ViewHolder> {

    ArrayList<DisplayPrice> displayPrices;
    ArrayList<RawPrice> rawPrices;
    Context context;

    public MyCoinsAdapter(Context context,
                          ArrayList<DisplayPrice> displayPrices,
                          ArrayList<RawPrice> rawPrices) {
        this.displayPrices = displayPrices;
        this.rawPrices = rawPrices;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_coin, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DisplayPrice displayPrice = displayPrices.get(position);
        RawPrice rawPrice = rawPrices.get(position);

        if (displayPrice.getPRICE() != null) {
            holder.tvPrice.setText(displayPrice.getPRICE());
        } else {
            holder.tvPrice.setText("n/a");
        }

        if (rawPrice.getFROMSYMBOL() != null) {
            holder.tvCoinTag.setText(rawPrice.getFROMSYMBOL());
            String symbol = rawPrice.getFROMSYMBOL();
            String coinName = CoinHelper.getInstance().getCoinName(symbol);

            if (TextUtils.isValidString(coinName)) {
                holder.tvCoinName.setText(coinName);
            }
        } else {
            holder.tvCoinTag.setText("C");
        }

        if (displayPrice.getCHANGE24HOUR() != null) {
            String change24Hours = displayPrice.getCHANGE24HOUR();
            holder.tvPriceDelta.setText(change24Hours + " " +
                    context.getResources().getString(R.string.up_caret));

            if (change24Hours.contains("-")) {
                holder.tvPriceDelta
                        .setTextColor(ContextCompat.getColor(context,
                                R.color.colorChangeDecrement));
                holder.tvPriceDelta.setText(change24Hours + " " +
                        context.getResources().getString(R.string.down_caret));
            }
        } else {
            holder.tvCoinTag.setText("n/a");
        }
    }

    @Override
    public int getItemCount() {
        return displayPrices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvPrice)
        TextView tvPrice;

        @BindView(R.id.tvCoinTag)
        TextView tvCoinTag;

        @BindView(R.id.tvCoinName)
        TextView tvCoinName;

        @BindView(R.id.tvPriceDelta)
        TextView tvPriceDelta;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
