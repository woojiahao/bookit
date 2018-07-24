package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.nio.file.attribute.PosixFileAttributes;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;
import static team.android.projects.com.bookit.utils.ui.UIUtils.loadGenreSelection;

public class SignUpGenreSelection extends AppCompatActivity {
	private EditText mSearch;
	private Button mSelectBtn;
	private ImageView mBackBtn;
	private GenreSelectionFragment mGenreSelectionFragment;

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
		
		mGenreSelectionFragment = loadGenreSelection(getSupportFragmentManager(), "Select Genres", 2, true);
	}

	private void connectListeners() {
		mBackBtn.setOnClickListener(ev -> finish());
		mSelectBtn.setOnClickListener(v -> {
			String[] selection = mGenreSelectionFragment.getSelection();
			if (selection.length == 0) {
				Toast.makeText(this, "Nothing is selected!", Toast.LENGTH_SHORT).show();
			} else {
				startActivity(new Intent(this, SignIn.class));
			}
		});
	}
}
