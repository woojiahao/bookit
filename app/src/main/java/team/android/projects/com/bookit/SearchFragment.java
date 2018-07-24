package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
			Toast.makeText(getContext(), "Typing: " + s, Toast.LENGTH_SHORT).show();
			if (getFragmentManager() != null) {
				if (getFragmentManager().findFragmentByTag("Genre") != null) {
					Log.d("Search", "Removing genre selection");
					getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("Genre")).commit();
				} else {
					if (s.toString().trim().equals("")) {
						Log.d("Search", "Loading back genre selection");
						loadGenreSelection(getFragmentManager(), "Genre", "Genre", 2, false);
					}
				}
			}
		});
	}
}
