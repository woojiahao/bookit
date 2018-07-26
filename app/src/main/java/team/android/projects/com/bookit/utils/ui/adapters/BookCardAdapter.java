package team.android.projects.com.bookit.utils.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.dataclasses.Book;

public class BookCardAdapter extends RecyclerView.Adapter<BookCardAdapter.ViewHolder> {
	private List<Book> mBooks;
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		private View mView;
		private ImageView mThumbnail;
		private TextView mTitle;
		private TextView mGenre;
		private TextView mPrice;
		private TextView mRating;
		
		private NumberFormat mCurrencyFormatter;
		
		
		ViewHolder(View v) {
			super(v);
			mView = v;
			
			mThumbnail = mView.findViewById(R.id.bookThumbnail);
			mTitle = mView.findViewById(R.id.bookTitle);
			mGenre = mView.findViewById(R.id.bookGenre);
			mPrice = mView.findViewById(R.id.bookPrice);
			mRating = mView.findViewById(R.id.bookRating);
			
			mCurrencyFormatter = NumberFormat.getCurrencyInstance();
		}
		
		void setThumbnail(int thumbnailId) {
			mThumbnail.setImageResource(thumbnailId);
		}
		
		void setTitle(String title) {
			mTitle.setText(title);
		}
		
		void setGenre(String genre) {
			mGenre.setText(genre);
		}
		
		void setPrice(float price) {
			mPrice.setText(mCurrencyFormatter.format(price));
		}
		
		void setRating(float rating) {
			mRating.setText(String.valueOf(rating));
		}
	}
	
	BookCardAdapter(List<Book> books) {
		mBooks = books;
	}
	
	@Override
	@NonNull
	public BookCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.book_card, parent, false);
		return new ViewHolder(v);
	}
	
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Book book = mBooks.get(position);
		
		holder.setTitle(book.getTitle());
		holder.setGenre(book.getGenre());
		holder.setPrice(book.getPrice());
		holder.setThumbnail(book.getThumbnail());
		holder.setRating(book.getRating());
	}
	
	public int getItemCount() {
		return mBooks.size();
	}
}
