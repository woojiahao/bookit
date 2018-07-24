package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;
import static team.android.projects.com.bookit.utils.ui.UIUtils.loadGenreSelection;

public class SearchFragment extends Fragment {
	private ClearableEditText mSearchField;
	private View mView;
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_search, container, false);
		init();
		connectListeners();
		
		return mView;
	}
	
	private void init() {
		mSearchField = find(mView, R.id.searchField);
		loadGenreSelection(getFragmentManager(), "Genre", "Genre", 2, false);
	}
	
	private void connectListeners() {
		mSearchField.setOnTypingListener((s, start, before, count) -> {
			if (getFragmentManager() != null) {
				Fragment genreSelection = getFragmentManager().findFragmentByTag("Genre");
				if (genreSelection != null) {
					if (start == 0) {
						if (!mSearchField.getText().equals("")) {
							getFragmentManager().beginTransaction().remove(genreSelection).commit();
						}
					}
				} else {
					if (s.toString().trim().equals("")) {
						loadGenreSelection(getFragmentManager(), "Genre", "Genre", 2, false);
					}
				}
			}
		});
	}
}
