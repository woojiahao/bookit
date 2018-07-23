package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

import team.android.projects.com.bookit.dataclasses.Genre;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;
import static team.android.projects.com.bookit.utils.ui.UIUtils.loadGenreSelection;

public class SignUpGenreSelection extends AppCompatActivity {
	private EditText mSearch;
	private Button mSelectBtn;
	private ImageView mBackBtn;

	private final List<Genre> mGenres = Arrays.asList(
			new Genre("Horror", R.drawable.ic_search_black_24dp),
			new Genre("Mystery", R.drawable.ic_search_black_24dp),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp),
			new Genre("Fantasy", R.drawable.ic_search_black_24dp),
			new Genre("Action", R.drawable.ic_search_black_24dp),
			new Genre("Self-Help", R.drawable.ic_search_black_24dp),
			new Genre("Romance", R.drawable.ic_search_black_24dp)
	);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_genre_selection);

		init();
		connectListeners();
	}

	private void init() {
		mSearch = find(this, R.id.searchGenreField);
		mSelectBtn = find(this, R.id.selectBtn);
		mBackBtn = find(this, R.id.backBtn);
		
		loadGenreSelection(getSupportFragmentManager(), "Select Genres", 2);
	}

	private void connectListeners() {
		mBackBtn.setOnClickListener(ev -> finish());
		mSelectBtn.setOnClickListener(v -> {
			startActivity(new Intent(this, SignIn.class));
		});
	}
}
