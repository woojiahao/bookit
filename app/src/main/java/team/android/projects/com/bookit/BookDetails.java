package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import team.android.projects.com.bookit.dataclasses.Book;

import static team.android.projects.com.bookit.util.UIUtils.find;

public class BookDetails extends AppCompatActivity {
	private Book mBook;
	
	private ImageView mBackBtn;
	private ImageView mFavouriteBtn;
	private ImageView mBookThumbnail;
	
	private TextView mBookTitle;
	private TextView mBookAuthors;
	private TextView mBookISBN;
	private TextView mBookGenres;
	private TextView mBookRating;
	private TextView mBookPrice;
	private TextView mBookSummary;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_details);
		
		init();
		connectListeners();
	}
	
	private void init() {
		mBackBtn = find(this, R.id.backBtn);
		mFavouriteBtn = find(this, R.id.detailsFavouritesBtn);
		mBookThumbnail = find(this, R.id.detailsThumbnail);
		mBookTitle = find(this, R.id.detailsTitle);
		mBookAuthors = find(this, R.id.detailsAuthors);
		mBookISBN = find(this, R.id.detailsISBN);
		mBookGenres = find(this, R.id.detailsGenres);
		mBookRating = find(this, R.id.detailsRating);
		mBookPrice = find(this, R.id.detailsPrice);
		mBookSummary = find(this, R.id.detailsSummary);
	}
	
	private void connectListeners() {
		mBackBtn.setOnClickListener(v -> finish());
	}
}
