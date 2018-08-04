package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.database.UsersList;
import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.dataclasses.BookGroup;
import team.android.projects.com.bookit.dataclasses.User;
import team.android.projects.com.bookit.loading.PreloadedData;
import team.android.projects.com.bookit.logging.ApplicationCodes;
import team.android.projects.com.bookit.searchengine.SearchType;
import team.android.projects.com.bookit.ui.adapters.DiscoverAdapter;
import team.android.projects.com.bookit.ui.decorators.SpacingDecoration;
import team.android.projects.com.bookit.ui.decorators.SpacingDecorationError;

import static team.android.projects.com.bookit.searchengine.Engines.GoogleBooks;
import static team.android.projects.com.bookit.searchengine.Engines.NewYorkTimes;
import static team.android.projects.com.bookit.searchengine.SearchType.BestSellers;
import static team.android.projects.com.bookit.util.UIUtils.find;

// todo: set the discover to constantly query for new data as the user scrolls
// todo: reimplement the new releases section
public class DiscoverFragment extends Fragment {
	private View mView;
	
	private List<Book> mRecommendations;
	
	private List<BookGroup> mGroups;
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_discover, container, false);
		init();
		return mView;
	}
	
	private void init() {
		loadBooks();
		
		DiscoverAdapter adapter = new DiscoverAdapter(mGroups);
		RecyclerView categories = find(mView, R.id.discoverArea);
		categories.setAdapter(adapter);
		
		try {
			categories.addItemDecoration(new SpacingDecoration(0, 96, 1));
		} catch (SpacingDecorationError e) {
			e.printStackTrace();
		}
		
		categories.setLayoutManager(
				new LinearLayoutManager(
						mView.getContext(),
						LinearLayoutManager.VERTICAL,
						false));
	}
	
	private void loadBooks() {
		try {
			generateRecommendations();
		} catch (InterruptedException | ExecutionException e) {
			Log.e(ApplicationCodes.Error.name(), "Unable to load books");
			e.printStackTrace();
		}
		
		mGroups = Arrays.asList(
				new BookGroup("Recommended for you", mRecommendations),
				new BookGroup("Best-sellers", PreloadedData.getBestSellers())
		);
	}
	
	private void generateRecommendations() throws ExecutionException, InterruptedException {
		mRecommendations = new ArrayList<Book>();
		
		long maxDisplayed = 15L;
		User currentUser = UsersList.getCurrentUser();
		
		if (currentUser != null) {
			List<String> genres = currentUser.genres;
			long chunkSize = maxDisplayed / genres.size();
			for (String genre : genres) {
				mRecommendations.addAll(
						App.searchEngines.get(GoogleBooks.mapKey)
								.groupSearch(SearchType.Genre, genre, chunkSize));
			}
		}
	}
}
