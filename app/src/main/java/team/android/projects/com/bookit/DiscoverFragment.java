package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.dataclasses.BookGroup;
import team.android.projects.com.bookit.utils.ui.adapters.DiscoverAdapter;
import team.android.projects.com.bookit.utils.ui.decorators.SpacingDecoration;
import team.android.projects.com.bookit.utils.ui.decorators.SpacingDecorationError;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class DiscoverFragment extends Fragment {
	private View mView;
	private RecyclerView mCategories;
	
	private List<Book> mBooks = new ArrayList<Book>() {{
		add(new Book.Builder()
				.setTitle("Artemis")
				.setGenres(new String[] { "Action" })
				.setAuthors(new String[] { "John Doe" })
				.setPrices(new HashMap<String, Double>() {{
					put("Amazon", 13.99);
				}})
				.setRating(5)
				.setThumbnail(R.drawable.artemis)
				.build());
		add(new Book.Builder()
				.setTitle("Artemis")
				.setGenres(new String[] { "Action" })
				.setAuthors(new String[] { "John Doe" })
				.setPrices(new HashMap<String, Double>() {{
					put("Amazon", 13.99);
				}})
				.setRating(5)
				.setThumbnail(R.drawable.artemis)
				.build());
		add(new Book.Builder()
				.setTitle("Artemis")
				.setGenres(new String[] { "Action" })
				.setAuthors(new String[] { "John Doe" })
				.setPrices(new HashMap<String, Double>() {{
					put("Amazon", 13.99);
				}})
				.setRating(5)
				.setThumbnail(R.drawable.artemis)
				.build());
		add(new Book.Builder()
				.setTitle("Artemis")
				.setGenres(new String[] { "Action" })
				.setAuthors(new String[] { "John Doe" })
				.setPrices(new HashMap<String, Double>() {{
					put("Amazon", 13.99);
				}})
				.setRating(5)
				.setThumbnail(R.drawable.artemis)
				.build());
	}};

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
		
		try {
			mCategories.addItemDecoration(new SpacingDecoration(0, 96, 1));
		} catch (SpacingDecorationError e) {
			e.printStackTrace();
		}
		
		mCategories.setLayoutManager(new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false));
	}
	
	private void connectListeners() {
	}
}
