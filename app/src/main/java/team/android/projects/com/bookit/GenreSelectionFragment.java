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
import java.util.stream.Collectors;

import team.android.projects.com.bookit.dataclasses.Genre;
import team.android.projects.com.bookit.utils.ui.adapters.GenreAdapter;
import team.android.projects.com.bookit.utils.ui.decorators.SpacingDecoration;
import team.android.projects.com.bookit.utils.ui.decorators.SpacingDecorationError;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class GenreSelectionFragment extends Fragment {
	private View mView;
	private RecyclerView mGenreArea;
	private GenreAdapter mGenreAdapter;
	
	private String mTitle = "Blank Title";
	private int mColumnCount = 2;
	private boolean mMultiSelection = true;
	
	private List<Genre> mDisplayedGenres;
	private List<Genre> mGenres;
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_genre_selection, container, false);
		
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
	
	public void searchFor(String s) {
		List<Genre> temp = new ArrayList<>();
		if (s.trim().equals("")) {
			if (!mDisplayedGenres.containsAll(mGenres)) {
				temp = mGenres;
			}
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				temp = mGenres.stream()
						.filter(genre -> genre.getGenreTitle().toLowerCase().startsWith(s.toLowerCase()))
						.collect(Collectors.toList());
			} else {
				for (Genre g : mGenres) {
					if (g.getGenreTitle().toLowerCase().startsWith(s.toLowerCase())) temp.add(g);
				}
			}
		}
		mDisplayedGenres.clear();
		mDisplayedGenres.addAll(temp);
		mGenreAdapter.notifyDataSetChanged();
	}
	
	private void init() {
		if (getArguments() != null) {
			mTitle = getArguments().getString("title");
			mColumnCount = getArguments().getInt("columns");
			mMultiSelection = getArguments().getBoolean("multiSelection");
		}
		
		mGenres = new ArrayList<Genre>() {{
			add(new Genre("Horror"));
			add(new Genre("Mystery"));
			add(new Genre("Fantasy"));
			add(new Genre("Fantasy"));
			add(new Genre("Fantasy"));
			add(new Genre("Fantasy"));
			add(new Genre("Fantasy"));
			add(new Genre("Fantasy"));
			add(new Genre("Fantasy"));
			add(new Genre("Fantasy"));
			add(new Genre("Fantasy"));
			add(new Genre("Fantasy"));
			add(new Genre("Action"));
			add(new Genre("Self-Help"));
			add(new Genre("Romance"));
		}};
		mDisplayedGenres = new ArrayList<>(mGenres);
		
		TextView genreTitle = find(mView, R.id.genreSelectionTitle);
		genreTitle.setText(mTitle);
		
		mGenreArea = find(mView, R.id.genreSelectionArea);
		mGenreAdapter = new GenreAdapter(mDisplayedGenres, mColumnCount, mMultiSelection);
		mGenreArea.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));
		try {
			mGenreArea.addItemDecoration(new SpacingDecoration(32, 32, 2));
		} catch (SpacingDecorationError e) {
			e.printStackTrace();
		}
		mGenreArea.setAdapter(mGenreAdapter);
	}
}
