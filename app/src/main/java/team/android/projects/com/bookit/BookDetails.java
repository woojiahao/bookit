package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import team.android.projects.com.bookit.dataclasses.Book;

import static team.android.projects.com.bookit.logging.Logging.shortToast;
import static team.android.projects.com.bookit.util.UIUtils.find;

public class BookDetails extends AppCompatActivity {
	private Book mBook;
	
	private ImageView mBackBtn;
	private ImageView mFavouriteBtn;
	
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
		((TextView) find(this, R.id.detailsSummary)).setText(String.valueOf(mBook.getSummary()));
		
		Map<String, String> isbns = mBook.getISBN();
		String isbn = isbns.containsKey("ISBN_13") ? isbns.get("ISBN_13") : isbns.get("ISBN_10");
		((TextView) find(this, R.id.detailsISBN)).setText(String.format("ISBN: %s", isbn));
		
		((TextView) find(this, R.id.detailsPrice)).setText(getLowestPrice());
	}
	
	private void connectListeners() {
		mBackBtn.setOnClickListener(v -> finish());
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
