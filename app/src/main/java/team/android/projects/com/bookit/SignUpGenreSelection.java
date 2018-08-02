package team.android.projects.com.bookit;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import team.android.projects.com.bookit.utils.database.FirebaseOperations;
import team.android.projects.com.bookit.utils.database.IFirebaseOperations;
import team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.utils.logging.ApplicationCodes.Error;
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
	
	private IFirebaseOperations mFirebaseOperations;
	
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
		
		mGenreSelectionFragment = loadGenreSelection(getSupportFragmentManager(), getString(R.string.select_genres), "Genre", 2, true, null);
		
		mFirebaseOperations = new FirebaseOperations(this);
	}
	
	private void connectListeners() {
		getCredentials();
		
		mBackBtn.setOnClickListener(ev -> finish());
		mSelectBtn.setOnClickListener(v -> beginRegistration());
		mSearch.setOnTypingListener(
				(s, start, count, after) ->
					mGenreSelectionFragment.searchFor(s.toString()));
	}
	
	private void beginRegistration() {
		String[] selection = mGenreSelectionFragment.getSelection();
		if (selection.length == 0) {
			shortToast(this, getString(R.string.empty_selection_warning));
		} else {
			registerUser();
		}
	}
	
	private void registerUser() {
		mSelectBtn.setEnabled(false);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			mSelectBtn.setBackground(getDrawable(R.drawable.red_button_background_disabled));
		} else {
			mSelectBtn.setBackground(getResources().getDrawable(R.drawable.red_button_background_disabled));
		}
		mFirebaseOperations.registerUser(mEmail, mPassword, mUsername, mGenreSelectionFragment.getSelection());
	}
	
	private void getCredentials() {
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			Log.e(Error.name(), "No data passed from the calling intent");
			shortToast(this, getString(R.string.invalid_credentials_warning));
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
