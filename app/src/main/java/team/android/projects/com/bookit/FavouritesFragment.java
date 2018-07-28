package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.utils.ui.adapters.BookRowAdapter;
import team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class FavouritesFragment extends Fragment {
	private View mView;
	private ClearableEditText mFavouritesSearch;
	private RecyclerView mFavouritesList;
	
	private final List<Book> mBooks = Arrays.asList(
			new Book("Artemis", "Action", "John Doe", "Amazon", 5.0, 13.99, R.drawable.artemis),
			new Book("Before We Were Yours", "Horror", "John Doe", "Amazon", 4.0, 25.99, R.drawable.before_we_were_yours),
			new Book("Into The Water", "Mystery", "John Doe", "Amazon", 4.3, 13.00F, R.drawable.into_the_water),
			new Book("Little Fires Everywhere", "Comedy", "John Doe", "Amazon", 4.7, 12.10, R.drawable.little_fires_everywhere),
			new Book("Talking As Fast As I Can", "Literature", "John Doe", "Amazon", 3.9, 16.50, R.drawable.talking_as_fast_as_i_can)
	);
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_favourites, container, false);
		
		init();
		connectListeners();
		
		return mView;
	}
	
	private void init() {
		mFavouritesSearch = find(mView, R.id.favouritesSearch);
		
		mFavouritesList = find(mView, R.id.favouritesList);
		BookRowAdapter adapter = new BookRowAdapter(mBooks);
		mFavouritesList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
		DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		mFavouritesList.addItemDecoration(divider);
		mFavouritesList.setAdapter(adapter);
	}
	
	private void connectListeners() {
		mFavouritesSearch.setOnTypingListener((sequence, start, before, count) -> {
			Toast.makeText(getContext(), "Typing: " + sequence, Toast.LENGTH_SHORT).show();
		});
	}
}
