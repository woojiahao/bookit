package team.android.projects.com.bookit;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import team.android.projects.com.bookit.dataclasses.Genre;
import team.android.projects.com.bookit.utils.ui.adapters.GenreAdapter;
import team.android.projects.com.bookit.utils.ui.helper.decorators.SpacingDecoration;
import team.android.projects.com.bookit.utils.ui.helper.decorators.SpacingDecorationError;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class GenreSelectionFragment extends Fragment {
	private View mView;
	
	private String mTitle = "Blank Title";
	private int mColumnCount = 2;
	private boolean mMultiSelection = true;
	
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
			new Genre("Fantasy"),
			new Genre("Fantasy"),
			new Genre("Action"),
			new Genre("Self-Help"),
			new Genre("Romance")
	);
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_genre_selection, container, false);
		
		if (getArguments() != null) {
			mTitle = getArguments().getString("title");
			mColumnCount = getArguments().getInt("columns");
			mMultiSelection = getArguments().getBoolean("multiSelection");
		}
		
		init();
		
		return mView;
	}
	
	public String[] getSelection() {
		Object[] selection;
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			selection = mGenres.stream()
					.filter(Genre::getIsSelected)
					.map(Genre::getGenreTitle)
					.toArray();
		} else {
			List<String> arr = new ArrayList<>();
			for (Genre g : mGenres) {
				if (g.getIsSelected()) {
					arr.add(g.getGenreTitle());
				}
			}
			selection = arr.toArray();
		}
		return Arrays.copyOf(selection, selection.length, String[].class);
	}
	
	private void init() {
		TextView genreTitle = find(mView, R.id.genreSelectionTitle);
		genreTitle.setText(mTitle);
		
		RecyclerView genresArea = find(mView, R.id.genreSelectionArea);
		GenreAdapter adapter = new GenreAdapter(mGenres, mColumnCount, mMultiSelection);
		genresArea.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));
		try {
			genresArea.addItemDecoration(new SpacingDecoration(32, 32, 2));
		} catch (SpacingDecorationError e) {
			e.printStackTrace();
		}
		genresArea.setAdapter(adapter);
	}
}
