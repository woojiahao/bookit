package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import team.android.projects.com.bookit.dataclasses.Book;

import static team.android.projects.com.bookit.util.UIUtils.find;

public class BookDetails extends AppCompatActivity {
	private Book mBook;
	
	private ImageView mBackBtn;
	private ImageView mFavouriteBtn;
	
	private TextView mBookISBN;
	private TextView mBookPrice;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_details);
		
		init();
		connectListeners();
	}
	
	private void init() {
		if (getIntent().getExtras() != null) {
			mBook = (Book) getIntent().getExtras().get("book");
		}
		
		mBackBtn = find(this, R.id.backBtn);
		mFavouriteBtn = find(this, R.id.detailsFavouritesBtn);
		
		Picasso.get().load(mBook.getThumbnail()).into((ImageView) find(this, R.id.detailsThumbnail));
		((TextView) find(this, R.id.detailsTitle)).setText(mBook.getTitle());
		((TextView) find(this, R.id.detailsAuthors)).setText(mBook.getAuthors());
		((TextView) find(this, R.id.detailsGenres)).setText(mBook.getGenres());
		((TextView) find(this, R.id.detailsRating)).setText(String.valueOf(mBook.getRating()));
		((TextView) find(this, R.id.detailsSummary)).setText(String.valueOf(mBook.getSummary()));
		
		mBookISBN = find(this, R.id.detailsISBN);
		mBookPrice = find(this, R.id.detailsPrice);
	}
	
	private void connectListeners() {
		mBackBtn.setOnClickListener(v -> finish());
	}
}
