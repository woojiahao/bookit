package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import team.android.projects.com.bookit.utils.database.FirebaseOperations;
import team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.utils.logging.Logging.shortToast;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;
import static team.android.projects.com.bookit.utils.ui.UIUtils.loadGenreSelection;

public class EditGenres extends AppCompatActivity {
	private ClearableEditText mSearch;
	private Button mSelectBtn;
	private ImageView mBackBtn;
	private GenreSelectionFragment mGenreSelectionFragment;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_genres);
		
		init();
		connectListeners();
	}
	
	private void init() {
		mSearch = find(this, R.id.searchGenreField);
		mSelectBtn = find(this, R.id.selectBtn);
		mBackBtn = find(this, R.id.backBtn);
		
		mGenreSelectionFragment = loadGenreSelection(getSupportFragmentManager(), getString(R.string.select_genres), "Genre", 2, true);
	}
	
	private void connectListeners() {
		mBackBtn.setOnClickListener(ev -> finish());
		mSelectBtn.setOnClickListener(v -> shortToast(this, "Editing Genres"));
		mSearch.setOnTypingListener(
				(s, start, count, after) ->
						mGenreSelectionFragment.searchFor(s.toString()));
	}
	
}
