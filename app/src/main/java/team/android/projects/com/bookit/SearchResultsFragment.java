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

import java.util.List;

import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.ui.adapters.BookRowAdapter;

import static team.android.projects.com.bookit.util.UIUtils.find;

public class SearchResultsFragment extends Fragment {
	private View mView;
	private List<Book> mBooks;
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_search_results, container, false);
		
		init();
		
		return mView;
	}
	
	private void init() {
		if (getArguments() != null) {
			mBooks = getArguments().getParcelableArrayList("results");
		}
		
		RecyclerView results = find(mView, R.id.searchResults);
		BookRowAdapter adapter = new BookRowAdapter(getContext(), mBooks);
		results.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
		DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		results.addItemDecoration(divider);
		results.setAdapter(adapter);
	}
}
