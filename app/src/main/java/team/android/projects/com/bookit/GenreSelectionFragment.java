package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import team.android.projects.com.bookit.dataclasses.Genre;
import team.android.projects.com.bookit.utils.ui.adapters.GenreAdapter;
import team.android.projects.com.bookit.utils.ui.helper.decorators.SpacingDecoration;
import team.android.projects.com.bookit.utils.ui.helper.decorators.SpacingDecorationError;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class GenreSelectionFragment extends Fragment {
	private View mView;
	private TextView mGenreSelectionTitle;
	private RecyclerView mGenres;
	
	private String mTitle = "Blank Title";
	private int mColumnCount = 2;
	private boolean mMultiSelection = true;
	
	private final List<Genre> mGenresLst = Arrays.asList(
			new Genre("Horror", R.drawable.ic_search_black_24dp, R.drawable.image_horror),
			new Genre("Mystery", R.drawable.ic_search_black_24dp, R.drawable.image_horror),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp, R.drawable.image_horror),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp, R.drawable.image_horror),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp, R.drawable.image_horror),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp, R.drawable.image_horror),
			new Genre("Action", R.drawable.ic_search_black_24dp, R.drawable.image_horror),
			new Genre("Self-Help", R.drawable.ic_search_black_24dp, R.drawable.image_horror),
			new Genre("Romance", R.drawable.ic_search_black_24dp, R.drawable.image_horror)
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
	
	private void init() {
		mGenreSelectionTitle = find(mView, R.id.genreSelectionTitle);
		mGenres = find(mView, R.id.genreSelectionArea);
		
		mGenreSelectionTitle.setText(mTitle);
		
		GenreAdapter adapter = new GenreAdapter(mGenresLst, mColumnCount, mMultiSelection);
		mGenres.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));
		try {
			mGenres.addItemDecoration(new SpacingDecoration(32, 32, 2));
		} catch (SpacingDecorationError e) {
			e.printStackTrace();
		}
		mGenres.setAdapter(adapter);
	}
}
