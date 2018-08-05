package team.android.projects.com.bookit.ui.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.dataclasses.Store;

import static team.android.projects.com.bookit.logging.Logging.shortToast;
import static team.android.projects.com.bookit.util.UIUtils.find;

public class BookDetailsAdapter extends RecyclerView.Adapter<BookDetailsAdapter.ViewHolder> {
	private List<Store> mPrices;
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		private View mView;
		private TextView mTitle;
		private ImageView mIcon;
		
		ViewHolder(View v) {
			super(v);
			mView = v;
			
			init();
		}
		
		private void init() {
			mTitle = find(mView, R.id.bookStorePrice);
			mIcon = find(mView, R.id.bookStoreIcon);
		}
		
		void setPrice(double price) {
			mTitle.setText(String.format("SGD%.2f", price));
		}
		
		void setIcon(String location) {
			int icon = -1;
			switch (location) {
				case "Amazon":
					icon = R.drawable.amazon_icon;
					break;
					
				case "Book Depository":
					icon = R.drawable.book_depository_icon;
					break;
					
				case "Google Books":
					icon = R.drawable.google_books_icon;
					break;
			}
			mIcon.setImageResource(icon);
		}
	}
	
	
	public BookDetailsAdapter(List<Store> prices) {
		mPrices = prices;
	}
	
	@NonNull @Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext())
				.inflate(R.layout.book_details_bookstore, parent, false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Store location = mPrices.get(position);
		holder.setPrice(location.getPrice());
		holder.setIcon(location.getStoreName());
		holder.mView.setOnClickListener(v -> {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(location.getStoreURL()));
			holder.mView.getContext().startActivity(browserIntent);
		});
	}
	
	@Override
	public int getItemCount() {
		return mPrices.size();
	}
}
