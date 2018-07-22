package team.android.projects.com.booktit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import team.android.projects.com.booktit.utils.UIUtils;

public class Search extends AppCompatActivity {
	
	private EditText mSearchField;
	private RecyclerView mGenreArea;
	
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
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		init();
		connectListeners();
	}
	
	private void init () {
		mSearchField = findViewById(R.id.searchField);
		mGenreArea = findViewById(R.id.genresArea);
		
		GenreSimpleAdapter adapter = new GenreSimpleAdapter(mGenres);
		mGenreArea.setAdapter(adapter);
		mGenreArea.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mGenreArea.addItemDecoration(new VerticalSpaceItemDecoration(32));
		
//		UIUtils.setBottomBarSelection(this, R.id.navigationSearch);
	}
	
	private void connectListeners () {
		mSearchField.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged (CharSequence s, int start, int count, int after) {
			}
			
			@Override public void afterTextChanged (Editable s) {
			}
			
			@Override public void onTextChanged (CharSequence s, int start, int before, int count) {
				Toast.makeText(Search.this, "Typing: " + s.toString(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
