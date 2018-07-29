package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.utils.ApplicationCodes.Error;
import static team.android.projects.com.bookit.utils.logging.Logging.shortToast;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;
import static team.android.projects.com.bookit.utils.ui.UIUtils.loadGenreSelection;

public class SignUpGenreSelection extends AppCompatActivity {
	private ClearableEditText mSearch;
	private Button mSelectBtn;
	private ImageView mBackBtn;
	private GenreSelectionFragment mGenreSelectionFragment;
	
	private String mEmail;
	private String mUsername;
	private String mPassword;
	
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
		
		mGenreSelectionFragment = loadGenreSelection(getSupportFragmentManager(), "Select Genres", "Genre", 2, true);
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
		
		getCredentials();
	}
	
	private void getCredentials() {
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			Log.e(Error.name(), "No data passed from the calling intent");
			shortToast(this, "Invalid credentials entered, try again!");
		} else {
			mEmail = getData("email");
			mUsername = getData("username");
			mPassword = getData("password");
		}
	}
	
	@SuppressWarnings("ConstantConditions")
	private String getData(String key) {
		return getIntent().getExtras().getString(key);
	}
}
