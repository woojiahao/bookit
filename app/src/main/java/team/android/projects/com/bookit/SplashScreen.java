package team.android.projects.com.bookit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;
import java.util.Locale;

import team.android.projects.com.bookit.utils.database.FirebaseOperations;
import team.android.projects.com.bookit.utils.database.IFirebaseOperations;

import static team.android.projects.com.bookit.utils.logging.ApplicationCodes.Debug;

public class SplashScreen extends AppCompatActivity {
	private IFirebaseOperations mFirebaseOperations;
	private final String[] mLanguages = { "English", "Chinese", "Malay" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		setupLanguage();
		init();
		startApp();
	}
	
	private void setupLanguage() {
		String deviceLanguage = Locale.getDefault().getDisplayLanguage();
		if (!Arrays.asList(mLanguages).contains(deviceLanguage)) {
			deviceLanguage = "English";
		}
		
		SharedPreferences preferences = getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);
		if (!preferences.contains("default_lang")) {
			preferences.edit().putString("default_lang", deviceLanguage).apply();
		}
	}
	
	private void init() {
		mFirebaseOperations = new FirebaseOperations(this);
	}
	
	private void startApp() {
		Handler h = new Handler();
		h.postDelayed(() -> {
			Class target;
			if (mFirebaseOperations.getCurrentUser() != null) {
				Preloading.setCurrentUser(mFirebaseOperations.getCurrentUser().getUid());
				target = Container.class;
			} else {
				target = SignUp.class;
			}
			startActivity(new Intent(this, target));
			finish();
		}, 3000);
	}
}
