package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.android.projects.com.bookit.database.FirebaseOperations;
import team.android.projects.com.bookit.database.IFirebaseOperations;
import team.android.projects.com.bookit.database.UsersList;
import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.dataclasses.User;

import static team.android.projects.com.bookit.logging.Logging.shortToast;
import static team.android.projects.com.bookit.util.UIUtils.find;

public class BookDetails extends AppCompatActivity {
	private Book mBook;
	
	private ImageView mBackBtn;
	private ImageView mFavouriteBtn;
	
	private String mISBN;
	private boolean mIsFavourited;
	
	private IFirebaseOperations mFirebaseOperations;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_details);
		
		init();
		connectListeners();
	}
	
	private void init() {
		mFirebaseOperations = new FirebaseOperations(this);
		
		if (getIntent().getExtras() != null) {
			mBook = (Book) getIntent().getExtras().get("book");
		}
		
		if (mBook == null) {
			shortToast(this, "Unable to load book details");
			return;
		}
		
		mBackBtn = find(this, R.id.backBtn);
		mFavouriteBtn = find(this, R.id.detailsFavouritesBtn);
		
		
		Picasso.get().load(mBook.getThumbnail()).into((ImageView) find(this, R.id.detailsThumbnail));
		((TextView) find(this, R.id.detailsTitle)).setText(mBook.getTitle());
		((TextView) find(this, R.id.detailsAuthors)).setText(mBook.getAuthors());
		((TextView) find(this, R.id.detailsGenres)).setText(mBook.getGenres());
		((TextView) find(this, R.id.detailsRating)).setText(String.valueOf(mBook.getRating()));
		((TextView) find(this, R.id.detailsSummary))
				.setText(mBook.getSummary() == null ? "N/A" : mBook.getSummary());
		
		Map<String, String> isbns = mBook.getISBN();
		mISBN = isbns.containsKey("ISBN_13") ? isbns.get("ISBN_13") : isbns.get("ISBN_10");
		((TextView) find(this, R.id.detailsISBN))
				.setText(String.format("ISBN: %s", mISBN != null ? mISBN : "N/A"));
		((TextView) find(this, R.id.detailsPrice)).setText(getLowestPrice());
		
		List<String> favourites = UsersList.getCurrentUser().favourites;
		if (favourites == null || favourites.size() == 0) {
			mIsFavourited = false;
		} else {
			mIsFavourited = UsersList.getCurrentUser().favourites.contains(mISBN);
		}
		mFavouriteBtn
				.setImageResource(mIsFavourited ?
						R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp);
	}
	
	private void connectListeners() {
		mBackBtn.setOnClickListener(v -> finish());
		mFavouriteBtn.setOnClickListener(v -> favourite());
	}
	
	private void favourite() {
		User currentUser = UsersList.getCurrentUser();
		if (currentUser != null) {
			if (mIsFavourited) {
				mFirebaseOperations.removeFavourite(mISBN);
				mFavouriteBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
				shortToast(this, String.format("Removed %s from favourites", mBook.getTitle()));
			} else {
				mFirebaseOperations.addFavourite(mISBN);
				mFavouriteBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
				shortToast(this, String.format("Added %s to favourites", mBook.getTitle()));
			}
		}
		
	}
	
	private String getLowestPrice() {
		Map<String, Double> prices = mBook.getPrices();
		NumberFormat priceFormat = NumberFormat.getCurrencyInstance();
		
		double min = -1;
		
		for (HashMap.Entry<String, Double> entry : prices.entrySet()) {
			double price = entry.getValue();
			if (min == -1) min = price;
			min = price < min ? price : min;
		}
		
		return priceFormat.format(min);
	}
}
