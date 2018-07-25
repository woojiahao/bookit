package team.android.projects.com.bookit.utils.ui.adapters;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.dataclasses.StorePrice;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    private List<StorePrice> mPrice;

    public DetailsAdapter(List<StorePrice> myPrice) {
        mPrice = myPrice;
    }

    @Override
    public DetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details_bookstore_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageView mStoreIcon = (ImageView) holder.mConstraintLayout.findViewById(R.id.bookStoreIcon);
        TextView mStorePrice = (TextView) holder.mConstraintLayout.findViewById(R.id.bookStorePrice);

        NumberFormat format = NumberFormat.getCurrencyInstance();

        mStoreIcon.setImageResource(mPrice.get(position).getStore().getStoreLogoResID());
        mStorePrice.setText(format.format(mPrice.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return mPrice.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout mConstraintLayout;

        public ViewHolder(ConstraintLayout v) {
            super(v);
            mConstraintLayout = v;
        }
    }
}
