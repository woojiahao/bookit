package team.android.projects.com.booktit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

import team.android.projects.com.booktit.dataclasses.Genre;
import team.android.projects.com.booktit.utils.ui.adapters.GenreAdapter;
import team.android.projects.com.booktit.utils.ui.helper.VerticalSpaceItemDecoration;

public class SignUpGenreSelection extends AppCompatActivity {
	
	private EditText mSearch;
	private RecyclerView mGenreArea;
	private Button mSelectBtn;
	private ImageView mBackBtn;
	
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
		setContentView(R.layout.activity_sign_up_genre_selection);
		
		init();
		connectListeners();
	}
	
	private void init () {
		mSearch = findViewById(R.id.searchGenreField);
		mGenreArea = findViewById(R.id.genresArea);
		mSelectBtn = findViewById(R.id.selectBtn);
		mBackBtn = findViewById(R.id.backBtn);
		
		GenreAdapter adapter = new GenreAdapter(mGenres);
		mGenreArea.setAdapter(adapter);
		mGenreArea.setLayoutManager(new LinearLayoutManager(
				this,
				LinearLayoutManager.VERTICAL,
				false));
		mGenreArea.addItemDecoration(new VerticalSpaceItemDecoration(32, true));
	}
	
	private void connectListeners () {
		mBackBtn.setOnClickListener(ev -> finish());
		mSelectBtn.setOnClickListener(v -> {
			startActivity(new Intent(this, SignIn.class));
		});
	}
}
