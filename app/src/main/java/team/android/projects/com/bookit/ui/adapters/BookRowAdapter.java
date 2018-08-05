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
		private TextView mBookRating;
		
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
			mBookRating = find(mView, R.id.bookRating);
			
		}
		
		void setThumbnail(String thumbnail) {
			Picasso.get().load(thumbnail).into(mBookThumbnail);
		}
		
		void setTitle(String title) {
			mBookTitle.setText(title);
		}
		
		void setAuthor(String author) {
			mBookAuthor.setText(author);
		}
		
		void setRating(double rating) {
			mBookRating.setText(String.valueOf(rating));
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
		
		holder.setAuthor(book.getAuthors());
		holder.setTitle(book.getTitle());
		holder.setThumbnail(book.getThumbnail());
		holder.setRating(book.getRating());
		
		holder.mView.setOnClickListener(v -> launchBookDetails(mContext, book));
		
		Map<String, String> isbns = book.getISBN();
		String isbn = isbns.containsKey("ISBN_13") ? isbns.get("ISBN_13") : isbns.get("ISBN_10");
		holder.mPopupMenu.setOnClickListener(v -> {
			displayPopupMenu(
					holder.mView.getContext(),
					holder.mPopupMenu,
					isbn,
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
