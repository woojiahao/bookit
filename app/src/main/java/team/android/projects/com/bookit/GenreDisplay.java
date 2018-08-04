package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.logging.ApplicationCodes;
import team.android.projects.com.bookit.searchengine.Engines;
import team.android.projects.com.bookit.searchengine.SearchType;
import team.android.projects.com.bookit.ui.adapters.BookRowAdapter;

import static team.android.projects.com.bookit.util.UIUtils.find;

// todo: add search bar
public class GenreDisplay extends AppCompatActivity {
	private ImageView mBackBtn;
	private String mTitle;
	private List<Book> mBooks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_genre_display);
		
		init();
		connectListeners();
	}
	
	private void init() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mTitle = extras.getString("title");
		}
		
		mBackBtn = find(this, R.id.backBtn);
		
		((TextView) findViewById(R.id.displayTitle)).setText(mTitle);
		
		loadBooks();
		
		RecyclerView books = find(this, R.id.displayBooks);
		BookRowAdapter adapter = new BookRowAdapter(this, mBooks);
		books.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
		books.addItemDecoration(divider);
		books.setAdapter(adapter);
	}
	
	private void connectListeners() {
		mBackBtn.setOnClickListener(v -> finish());
	}
	
	private void loadBooks() {
		
		try {
			mBooks = App
					.searchEngines
					.get(Engines.GoogleBooks.mapKey)
					.groupSearch(SearchType.Genre, mTitle, 20L);
		} catch (ExecutionException | InterruptedException e) {
			Log.e(ApplicationCodes.Error.name(), "Unable to load books in genre: " + mTitle);
			e.printStackTrace();
		}
	}
}
