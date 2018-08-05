package team.android.projects.com.bookit.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.database.FirebaseOperations;
import team.android.projects.com.bookit.database.IFirebaseOperations;
import team.android.projects.com.bookit.dataclasses.Book;

import static team.android.projects.com.bookit.util.UIUtils.displayPopupMenu;
import static team.android.projects.com.bookit.util.UIUtils.find;
import static team.android.projects.com.bookit.util.UIUtils.launchBookDetails;

// todo: reimplement the price displayed when a more optimal api is found
public class BookCardAdapter extends RecyclerView.Adapter<BookCardAdapter.ViewHolder> {
	private List<Book> mBooks;
	private Context mContext;
	private IFirebaseOperations mFirebaseOperations;
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		private View mView;
		private ImageView mThumbnail;
		private ImageView mPopupMenu;
		
		private TextView mTitle;
		private TextView mGenre;
		private TextView mRating;
		
		ViewHolder(View v) {
			super(v);
			mView = v;
			init();
		}
		
		private void init() {
			mThumbnail = find(mView, R.id.bookThumbnail);
			mPopupMenu = find(mView, R.id.bookPopupMenu);
			
			mTitle = find(mView, R.id.bookTitle);
			mGenre = find(mView, R.id.bookGenre);
			mRating = find(mView, R.id.bookRating);
		}
		
		void setThumbnail(String thumbnailId) {
			Picasso.get().load(thumbnailId).into(mThumbnail);
		}
		
		void setTitle(String title) {
			mTitle.setText(title);
		}
		
		void setGenre(String genre) {
			mGenre.setText(genre);
		}
		
		void setRating(double rating) {
			mRating.setText(String.valueOf(rating));
		}
	}
	
	BookCardAdapter(Context c, List<Book> books) {
		mContext = c;
		mBooks = books;
		mFirebaseOperations = new FirebaseOperations(mContext);
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
		holder.setGenre(book.getGenres());
		holder.setThumbnail(book.getThumbnail());
		holder.setRating(book.getRating());
		
		holder.mView.setOnClickListener(v -> launchBookDetails(mContext, book));
		
		Map<String, String> isbns = book.getISBN();
		String isbn = isbns.containsKey("ISBN_13") ? isbns.get("ISBN_13") : isbns.get("ISBN_10");
		holder.mPopupMenu.setOnClickListener(v ->
				displayPopupMenu(
						holder.mView.getContext(), holder.mPopupMenu,
						isbn, mFirebaseOperations)
		);
	}
	
	public int getItemCount() {
		return mBooks.size();
	}
}
