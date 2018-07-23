package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.dataclasses.BookGroup;
import team.android.projects.com.bookit.utils.ui.adapters.DiscoverAdapter;
import team.android.projects.com.bookit.utils.ui.helper.VerticalSpaceItemDecoration;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class DiscoverFragment extends Fragment {
	private View mView;
	private RecyclerView mCategories;
	
	private final List<Book> mBooks = Arrays.asList(
			new Book("Artemis", "Action", 5.0, 13.99, R.drawable.artemis),
			new Book("Before We Were Yours", "Horror", 4.0, 25.99, R.drawable.before_we_were_yours),
			new Book("Into The Water", "Mystery", 4.3, 13.00F, R.drawable.into_the_water),
			new Book("Little Fires Everywhere", "Comedy", 4.7, 12.10, R.drawable.little_fires_everywhere),
			new Book("Talking As Fast As I Can", "Literature", 3.9, 16.50, R.drawable.talking_as_fast_as_i_can)
	);
	
	private final List<BookGroup> mGroups = Arrays.asList(
			new BookGroup("Recommended for you", mBooks),
			new BookGroup("Best-sellers", mBooks),
			new BookGroup("New releases", mBooks)
	);
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_discover, container, false);
		
		init();
		connectListeners();
		
		return mView;
	}
	
	private void init() {
		DiscoverAdapter adapter = new DiscoverAdapter(mGroups);
		mCategories = find(mView, R.id.discoverArea);
		mCategories.setAdapter(adapter);
		mCategories.addItemDecoration(new VerticalSpaceItemDecoration(64, false));
		mCategories.setLayoutManager(new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false));
	}
	
	private void connectListeners() {
	}
}
