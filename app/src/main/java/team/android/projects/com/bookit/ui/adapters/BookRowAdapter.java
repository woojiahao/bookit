package team.android.projects.com.bookit.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.database.FirebaseOperations;
import team.android.projects.com.bookit.database.IFirebaseOperations;
import team.android.projects.com.bookit.dataclasses.Book;

import static team.android.projects.com.bookit.util.UIUtils.displayPopupMenu;
import static team.android.projects.com.bookit.util.UIUtils.find;

public class BookRowAdapter extends RecyclerView.Adapter<BookRowAdapter.ViewHolder> {
	private List<Book> mBooks;
	private Context mContext;
	private IFirebaseOperations mFirebaseOperations;
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		private View mView;
		
		private ImageView mBookThumbnail;
		private ImageView mPopupMenu;
		
		private TextView mBookTitle;
		private TextView mBookAuthor;
		private TextView mBookLocation;
		private TextView mBookRating;
		private TextView mBookPrice;
		private NumberFormat mCurrencyFormatter;
		
		ViewHolder(View v) {
			super(v);
			mView = v;
			
			init();
		}
		
		private void init() {
			mBookThumbnail = find(mView, R.id.bookThumbnail);
			mPopupMenu = find(mView, R.id.bookPopupMenu);
			
			mBookTitle = find(mView, R.id.bookTitle);
			mBookAuthor = find(mView, R.id.bookAuthor);
			mBookLocation = find(mView, R.id.bookLocation);
			mBookRating = find(mView, R.id.bookRating);
			mBookPrice = find(mView, R.id.bookPrice);
			
			mCurrencyFormatter = NumberFormat.getCurrencyInstance();
		}
		
		void setThumbnail(int thumbnail) {
			mBookThumbnail.setImageResource(thumbnail);
		}
		
		void setTitle(String title) {
			mBookTitle.setText(title);
		}
		
		void setAuthor(String author) {
			mBookAuthor.setText(author);
		}
		
		void setLocation(String location) {
			mBookLocation.setText(String.format("Found: %s", location));
		}
		
		void setRating(double rating) {
			mBookRating.setText(String.valueOf(rating));
		}
		
		void setPrice(double price) {
			mBookPrice.setText(mCurrencyFormatter.format(price));
		}
	}
	
	public BookRowAdapter(Context c, List<Book> books) {
		mContext = c;
		mBooks = books;
		mFirebaseOperations = new FirebaseOperations(mContext);
	}
	
	@NonNull @Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false));
	}
	
	@Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Book book = mBooks.get(position);
		
		Map<String, Double> prices = book.getPrices();
		
		String location = null;
		double price = 0.0;
		
		for (Map.Entry<String, Double> entry : prices.entrySet()) {
			location = entry.getKey();
			price = entry.getValue();
		}
		
		holder.setAuthor(book.getAuthors());
		holder.setLocation(location);
		holder.setTitle(book.getTitle());
		holder.setPrice(price);
//		holder.setThumbnail(book.getThumbnail());
		holder.setRating(book.getRating());
		
		holder.mPopupMenu.setOnClickListener(v -> {
			displayPopupMenu(
					holder.mView.getContext(),
					holder.mPopupMenu,
					book.getISBN(),
					mFirebaseOperations,
					() -> {
						mBooks.remove(book);
						notifyDataSetChanged();
					});
		});
	}
	
	@Override public int getItemCount() {
		return mBooks.size();
	}
}
