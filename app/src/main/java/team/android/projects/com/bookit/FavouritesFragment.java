package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.database.UsersList;
import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.logging.ApplicationCodes;
import team.android.projects.com.bookit.searchengine.Engines;
import team.android.projects.com.bookit.searchengine.SearchType;
import team.android.projects.com.bookit.ui.adapters.BookRowAdapter;
import team.android.projects.com.bookit.ui.custom.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.util.UIUtils.find;

// todo: generify the searchFor
// todo: add dynamic loading of the favourites instead of hardcoding it
// todo: if the user has more than 40 favourites, break it down before sending all of the queries to google books
public class FavouritesFragment extends Fragment {
	private View mView;
	private ClearableEditText mFavouritesSearch;
	private RecyclerView mFavouritesList;
	private BookRowAdapter mAdapter;
	
	private List<Book> mDisplayedBooks;
	private List<Book> mBooks;
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_favourites, container, false);
		
		init();
		connectListeners();
		
		return mView;
	}
	
	private void init() {
		loadBooks();
		mFavouritesSearch = find(mView, R.id.favouritesSearch);
		
		mFavouritesList = find(mView, R.id.favouritesList);
		mAdapter = new BookRowAdapter(getContext(), mDisplayedBooks);
		mFavouritesList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
		DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		mFavouritesList.addItemDecoration(divider);
		mFavouritesList.setAdapter(mAdapter);
	}
	
	private void connectListeners() {
		mFavouritesSearch.setOnTypingListener((sequence, start, before, count) ->
				searchFor(sequence.toString()));
	}
	
	private void searchFor(String s) {
		List<Book> temp = new ArrayList<>();
		if (mDisplayedBooks == null || mBooks == null) return;
		
		if (s.trim().equals("")) {
			if (!mDisplayedBooks.containsAll(mBooks)) temp = mBooks;
		} else {
			for (Book b : mBooks) {
				if (b.getTitle().toLowerCase().startsWith(s.toLowerCase())) temp.add(b);
			}
		}
		mDisplayedBooks.clear();
		mDisplayedBooks.addAll(temp);
		mAdapter.notifyDataSetChanged();
	}
	
	private void loadBooks() {
		List<String> userFavourites = UsersList.getCurrentUser().favourites;
		if (userFavourites == null || userFavourites.size() == 0) {
			mDisplayedBooks = new ArrayList<Book>();
			return;
		}
		
		try {
			mBooks = App
					.searchEngines
					.get(Engines.GoogleBooks.mapKey)
					.batchSearch(
							SearchType.ISBN,
							userFavourites.toArray(new String[userFavourites.size()]));
			mDisplayedBooks = new ArrayList<Book>(mBooks);
		} catch (ExecutionException | InterruptedException e) {
			Log.e(ApplicationCodes.Error.name(), "Unable to load favourites");
			e.printStackTrace();
		}
	}
}
