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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.dataclasses.BookGroup;
import team.android.projects.com.bookit.logging.ApplicationCodes;
import team.android.projects.com.bookit.ui.adapters.DiscoverAdapter;
import team.android.projects.com.bookit.ui.decorators.SpacingDecoration;
import team.android.projects.com.bookit.ui.decorators.SpacingDecorationError;

import static team.android.projects.com.bookit.util.UIUtils.find;

public class DiscoverFragment extends Fragment {
	private View mView;
	private RecyclerView mCategories;
	
	private List<Book> mBooks;
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_discover, container, false);
		
		try {
			mBooks = App.searchEngine.genreSearch("horror");
		} catch (ExecutionException | InterruptedException e) {
			Log.e(ApplicationCodes.Error.name(), "Unable to retrieve books");
			e.printStackTrace();
		}
		
		init();
		
		return mView;
	}
	
	private void init() {
		List<BookGroup> mGroups = Arrays.asList(
				new BookGroup("Recommended for you", mBooks),
				new BookGroup("Best-sellers", mBooks),
				new BookGroup("New releases", mBooks)
		);
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
}
