package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.ObjectStreamField;

import team.android.projects.com.bookit.utils.database.FirebaseOperations;
import team.android.projects.com.bookit.utils.database.IFirebaseOperations;
import team.android.projects.com.bookit.utils.logging.ApplicationCodes;

public class SplashScreen extends AppCompatActivity {
	private IFirebaseOperations mFirebaseOperations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		mFirebaseOperations = new FirebaseOperations(this);
		
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
