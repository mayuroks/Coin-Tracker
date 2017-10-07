package coin.tracker.zxr.about;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import coin.tracker.zxr.R;
import coin.tracker.zxr.models.AboutItem;

/**
 * Created by Mayur on 07-10-2017.
 */

public class AboutMeAdapter extends RecyclerView.Adapter<AboutMeAdapter.ViewHolder> {

    private ArrayList<AboutItem> items;

    public AboutMeAdapter(ArrayList<AboutItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_about_me, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AboutItem aboutItem = items.get(position);
        holder.tvTitle.setText(aboutItem.getTitle());
        holder.tvSubTitle.setText(aboutItem.getSubTitle());

        if (position == items.size() - 1) {
            holder.view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvSubTitle)
        TextView tvSubTitle;

        @BindView(R.id.view)
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
