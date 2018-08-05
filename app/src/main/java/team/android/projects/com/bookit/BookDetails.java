package team.android.projects.com.bookit;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.database.FirebaseOperations;
import team.android.projects.com.bookit.database.IFirebaseOperations;
import team.android.projects.com.bookit.database.UsersList;
import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.dataclasses.Store;
import team.android.projects.com.bookit.dataclasses.User;
import team.android.projects.com.bookit.logging.ApplicationCodes;
import team.android.projects.com.bookit.searchengine.GoodReadsSearchEngine;
import team.android.projects.com.bookit.ui.adapters.BookDetailsAdapter;
import team.android.projects.com.bookit.ui.decorators.SpacingDecoration;
import team.android.projects.com.bookit.ui.decorators.SpacingDecorationError;

import static team.android.projects.com.bookit.logging.Logging.shortToast;
import static team.android.projects.com.bookit.searchengine.Engines.GoodReads;
import static team.android.projects.com.bookit.util.UIUtils.find;

public class BookDetails extends AppCompatActivity {
	private Book mBook;
	
	private ImageView mBackBtn;
	private ImageView mFavouriteBtn;
	private TextView mPrices;
	private TextView mDisplayPrices;
	
	private String mISBN;
	private boolean mIsFavourited;
	
	private List<Store> mPricesList;
	private Map<String, String> mISBNs;
	
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
		
		if (getIntent().getExtras() == null) return;
		Bundle data = getIntent().getExtras().getBundle("data");
		
		if (data == null) return;
		
		mBook = data.getParcelable("book");
		List<Store> prices = data.getParcelableArrayList("prices");
		mPricesList = prices == null ? new ArrayList<Store>() : prices;
		mISBNs = (Map<String, String>) data.getSerializable("isbn");
		
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
		
		mISBN = mISBNs.containsKey("ISBN_13") ? mISBNs.get("ISBN_13") : mISBNs.get("ISBN_10");
		((TextView) find(this, R.id.detailsISBN))
				.setText(String.format("ISBN: %s", mISBN != null ? mISBN : "N/A"));
		
		mDisplayPrices = find(this, R.id.detailsPrice);
		
		HandlerThread handlerThread = new HandlerThread("Retrieving Price");
		handlerThread.start();
		Handler requestHandler = new Handler(handlerThread.getLooper());
		final Handler responseHandler = new Handler(Looper.getMainLooper()) {
			@Override public void handleMessage(Message msg) {
				TextView loadingText = find(BookDetails.this, R.id.loadingText);
				
				if (mPricesList.size() == 0 || mPricesList == null) {
					loadingText.setText(R.string.no_prices_found);
					mDisplayPrices.setText(R.string.no_price);
					return;
				}
				mDisplayPrices.setText(getLowestPrice(mPricesList));
				
				loadingText.setVisibility(View.GONE);
				RecyclerView locations = find(BookDetails.this, R.id.detailsLocations);
				BookDetailsAdapter adapter = new BookDetailsAdapter(mPricesList);
				locations.setLayoutManager(
						new LinearLayoutManager(
								BookDetails.this,
								LinearLayoutManager.HORIZONTAL,
								false));
				locations.setAdapter(adapter);
				try {
					locations.addItemDecoration(new SpacingDecoration(84, 0, mPricesList.size()));
				} catch (SpacingDecorationError e) {
					e.printStackTrace();
				}
			}
		};
		Runnable runnable = () -> {
			List<Store> retreivedPrices = getPrices();
			if (retreivedPrices != null) mPricesList.addAll(retreivedPrices);
			Message message = new Message();
			responseHandler.sendMessage(message);
		};
		requestHandler.post(runnable);
		
		List<String> favourites = UsersList.getCurrentUser().favourites;
		mIsFavourited = favourites != null && favourites.size() != 0 && UsersList.getCurrentUser().favourites.contains(mISBN);
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
	
	private List<Store> getPrices() {
		try {
			return ((GoodReadsSearchEngine) App.searchEngines.get(GoodReads.mapKey))
					.getPrices(mBook.getTitle());
		} catch (ExecutionException | InterruptedException e) {
			Log.e(ApplicationCodes.Error.name(), "Unable to load prices");
			e.printStackTrace();
		}
		return null;
	}
	
	private String getLowestPrice(List<Store> prices) {
		StringBuilder priceString = new StringBuilder("SGD");
		
		double min = prices.get(0).getPrice();
		for (int i = 1; i < prices.size(); i++) {
			Store cur = prices.get(i);
			if (cur.getPrice() < min) min = cur.getPrice();
		}
		
		return priceString.append(String.format("%.2f", min)).toString();
	}
}
