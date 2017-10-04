package coin.tracker.zxr.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import coin.tracker.zxr.BaseActivity;
import coin.tracker.zxr.CoinDetailsActivity;
import coin.tracker.zxr.R;
import coin.tracker.zxr.models.DisplayPrice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
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
    DecimalFormat format = new DecimalFormat(TextUtils.IN_FORMAT);

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DisplayPrice displayPrice = displayPrices.get(position);
        final RawPrice rawPrice = rawPrices.get(position);

        if (displayPrice.getPRICE() != null) {
            String formattedPrice = format.format(Float.parseFloat(rawPrice.getPRICE()));
            holder.tvPrice.setText(formattedPrice);
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

        holder.rlCoinItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coinName = CoinHelper.getInstance()
                        .getCoinName(rawPrice.getFROMSYMBOL());
                Intent intent = new Intent(context, CoinDetailsActivity.class);
                intent.putExtra("coinTag", rawPrice.getFROMSYMBOL());
                intent.putExtra("coinName", coinName);

                context.startActivity(intent);
            }
        });

        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coinTag = rawPrice.getFROMSYMBOL();
                CoinHelper.getInstance().deleteUserCoin(coinTag);
                remove(holder.getAdapterPosition());
            }
        });
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

        @BindView(R.id.rlCoinItem)
        RelativeLayout rlCoinItem;

        @BindView(R.id.tvRemove)
        TextView tvRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void remove(int position) {
        this.displayPrices.remove(position);
        this.rawPrices.remove(position);
        notifyItemRemoved(position);
    }
}
