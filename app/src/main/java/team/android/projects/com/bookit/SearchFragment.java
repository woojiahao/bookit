package team.android.projects.com.bookit;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import team.android.projects.com.bookit.dataclasses.Genre;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;
import static team.android.projects.com.bookit.utils.ui.UIUtils.loadGenreSelection;

public class SearchFragment extends Fragment {
	private EditText mSearchField;
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
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_search, container, false);
		init();
		connectListeners();

		return mView;
	}

	private void init() {
		mSearchField = find(mView, R.id.searchField);
		loadGenreSelection(getFragmentManager(), "Genre", 2, false);
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
