package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.android.projects.com.bookit.ui.custom.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.util.UIUtils.find;
import static team.android.projects.com.bookit.util.UIUtils.loadGenreSelection;

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
		loadGenreSelection(getFragmentManager(), "Genre", "Genre", 2, false, null);
	}
	
	private void connectListeners() {
		mSearchField.setOnTypingListener((s, start, before, count) -> {
			if (getFragmentManager() != null) {
				Fragment genreSelection = getFragmentManager().findFragmentByTag("Genre");
				if (mSearchField.getText().equals("")) {
					loadGenreSelection(getFragmentManager(), "Genre", "Genre", 2, false, null);
				} else {
					if (genreSelection != null) {
						getFragmentManager().beginTransaction().remove(genreSelection).commit();
					}
				}
			}
		});
	}
}
