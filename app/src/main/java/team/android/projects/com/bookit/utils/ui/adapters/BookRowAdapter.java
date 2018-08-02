package team.android.projects.com.bookit.utils.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;

import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.dataclasses.Book;

import static team.android.projects.com.bookit.utils.ui.UIUtils.displayPopupMenu;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class BookRowAdapter extends RecyclerView.Adapter<BookRowAdapter.ViewHolder> {
	private List<Book> mBooks;
	
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
			connectListeners();
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
		
		private void connectListeners() {
			mPopupMenu.setOnClickListener(v -> displayPopupMenu(mView.getContext(), mPopupMenu));
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
		
		void setRating(float rating) {
			mBookRating.setText(String.valueOf(rating));
		}
		
		void setPrice(float price) {
			mBookPrice.setText(mCurrencyFormatter.format(price));
		}
	}
	
	public BookRowAdapter(List<Book> books) {
		mBooks = books;
	}
	
	@NonNull @Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Book b = mBooks.get(position);
		holder.setAuthor(b.getAuthor());
		holder.setLocation(b.getLocation());
		holder.setTitle(b.getTitle());
		holder.setPrice(b.getPrice());
		holder.setThumbnail(b.getThumbnail());
		holder.setRating(b.getRating());
	}
	
	@Override public int getItemCount() {
		return mBooks.size();
	}
	
}
