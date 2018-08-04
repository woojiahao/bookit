package team.android.projects.com.bookit;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.logging.ApplicationCodes;
import team.android.projects.com.bookit.searchengine.Engines;
import team.android.projects.com.bookit.searchengine.SearchType;
import team.android.projects.com.bookit.ui.custom.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.util.UIUtils.find;
import static team.android.projects.com.bookit.util.UIUtils.loadGenreSelection;

// todo: add support to search through isbn, author and title (currently only title)
public class SearchFragment extends Fragment {
	private ClearableEditText mSearchField;
	private View mView;
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_search, container, false);
		init();
		connectListeners();
		
		return mView;
	}
	
	private void init() {
		mSearchField = find(mView, R.id.searchField);
		loadGenreSelection(getFragmentManager(), "Genre", "Genre", 2, false, null);
	}
	
	private void connectListeners() {
		mSearchField.setTypingCompleteListener((v, actionId, event) -> {
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				try {
					List<Book> books = App
							.searchEngines
							.get(Engines.GoogleBooks.mapKey)
							.groupSearch(SearchType.Title, mSearchField.getText(), 10L);
					Fragment results;
					Bundle b = new Bundle();
					
					if (books == null || books.size() == 0) {
						results = new StatusFragment();
						b.putBoolean("status", false);
						b.putString("message", "Cannot find " + mSearchField.getText());
					} else {
						results = new SearchResultsFragment();
						b.putParcelableArrayList("results", (ArrayList<? extends Parcelable>) books);
					}
					results.setArguments(b);
					if (getFragmentManager() != null) {
						getFragmentManager()
								.beginTransaction()
								.replace(R.id.genreSelection, results, "Search Results")
								.commit();
					}
				} catch (ExecutionException | InterruptedException e) {
					Log.e(ApplicationCodes.Error.name(), "Unable to load search results");
					e.printStackTrace();
				}
			}
			return false;
		});
		mSearchField.setOnTypingListener((s, start, before, count) -> {
			if (getFragmentManager() != null) {
				Fragment genreSelection = getFragmentManager().findFragmentByTag("Genre");
				if (mSearchField.getText().equals("")) {
					if (getFragmentManager() != null) {
						Fragment searchResults = getFragmentManager().findFragmentByTag("Search Results");
						if (searchResults != null) {
							getFragmentManager().beginTransaction().remove(searchResults).commit();
						}
						loadGenreSelection(getFragmentManager(), "Genre", "Genre", 2, false, null);
					}
				} else {
					if (genreSelection != null) {
						getFragmentManager().beginTransaction().remove(genreSelection).commit();
					}
				}
			}
		});
	}
}
