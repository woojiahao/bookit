package team.android.projects.com.booktit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import team.android.projects.com.booktit.dataclasses.Book;
import team.android.projects.com.booktit.dataclasses.BookGroup;
import team.android.projects.com.booktit.utils.ui.adapters.DiscoverAdapter;
import team.android.projects.com.booktit.utils.ui.helper.VerticalSpaceItemDecoration;

public class DiscoverFragment extends Fragment {
	private View mView;
	
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
		RecyclerView categories = mView.findViewById(R.id.discoverArea);
		categories.setAdapter(adapter);
		categories.addItemDecoration(new VerticalSpaceItemDecoration(64, false));
		categories.setLayoutManager(new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false));
	}
	
	private void connectListeners() {
	
	}
}
