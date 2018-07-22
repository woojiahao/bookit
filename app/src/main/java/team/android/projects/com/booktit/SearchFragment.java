package team.android.projects.com.booktit;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import team.android.projects.com.booktit.dataclasses.Genre;
import team.android.projects.com.booktit.utils.ui.adapters.GenreSimpleAdapter;
import team.android.projects.com.booktit.utils.ui.helper.VerticalSpaceItemDecoration;

public class SearchFragment extends Fragment {
	private EditText mSearchField;
	private RecyclerView mGenreArea;
	private View mView;
	
	private final List<Genre> mGenres = Arrays.asList(
			new Genre("Horror"),
			new Genre("Mystery"),
			new Genre("Fantasy"),
			new Genre("Fantasy"),
			new Genre("Fantasy"),
			new Genre("Fantasy"),
			new Genre("Fantasy"),
			new Genre("Fantasy"),
			new Genre("Fantasy"),
			new Genre("Fantasy"),
			new Genre("Action"),
			new Genre("Self-Help"),
			new Genre("Romance")
	);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_search, container, false);
		init();
		connectListeners();
		
		return mView;
	}
	
	private void init() {
		mSearchField = mView.findViewById(R.id.searchField);
		mGenreArea = mView.findViewById(R.id.genresArea);
		
		GenreSimpleAdapter adapter = new GenreSimpleAdapter(mGenres);
		mGenreArea.setAdapter(adapter);
		mGenreArea.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
		mGenreArea.addItemDecoration(new VerticalSpaceItemDecoration(32, true));
	}
	
	private void connectListeners() {
		mSearchField.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override public void afterTextChanged(Editable s) {
			}
			
			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {
				Toast.makeText(getActivity(), "Typing: " + s.toString(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
