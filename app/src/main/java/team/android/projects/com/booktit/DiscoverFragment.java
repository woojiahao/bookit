package team.android.projects.com.booktit;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.android.projects.com.booktit.dataclasses.Book;
import team.android.projects.com.booktit.dataclasses.BookCategory;
import team.android.projects.com.booktit.dataclasses.Genre;
import team.android.projects.com.booktit.utils.ui.adapters.DiscoverFinalAdapter;

public class DiscoverFragment extends Fragment {
	
	private View mView;
	
	private ArrayList<BookCategory> mBookCategories;
	private Genre mGenre = new Genre("Action");
	private BottomNavigationView mBottomBar;
	
	private List<Book> mBooks = Arrays.asList(
			new Book("Artemis", 5.0, 13.99, mGenre, R.drawable.artemis),
			new Book("Before We Were Yours", 4.0, 25.99, mGenre, R.drawable.before_we_were_yours),
			new Book("Into The Water", 4.3, 13.00, mGenre, R.drawable.into_the_water),
			new Book("Little Fires Everywhere", 4.7, 12.10, mGenre, R.drawable.little_fires_everywhere),
			new Book("Talking As Fast As I Can", 3.9, 16.50, mGenre, R.drawable.talking_as_fast_as_i_can)
	);
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_discover, container, false);
		
		init();
		connectListeners();
		
		return mView;
	}
	
	private void init() {
		mBookCategories = new ArrayList<>();
		mBookCategories.add(new BookCategory("Recommended for you", mBooks));
		mBookCategories.add(new BookCategory("Best-sellers", mBooks));
		mBookCategories.add(new BookCategory("New Releases", mBooks));
		
		DiscoverFinalAdapter adapter = new DiscoverFinalAdapter(getActivity(), mBookCategories);
		ListView listView = mView.findViewById(R.id.discoverArea);
		listView.setDivider(null);
		listView.setDividerHeight(0);
		listView.setAdapter(adapter);
	}
	
	private void connectListeners() {
	
	}
}
