package team.android.projects.com.booktit;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import team.android.projects.com.booktit.dataclasses.Genre;

public class GenreSelectionFragment extends Fragment {
	private View mView;
	private List<Genre> mGenres;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_genre_selection, container, false);
		
		init();
		
		return mView;
	}
	
	private void init() {
	}
}
