package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import team.android.projects.com.bookit.utils.database.FirebaseOperations;
import team.android.projects.com.bookit.utils.database.IFirebaseOperations;

public class SplashScreen extends AppCompatActivity {
	private IFirebaseOperations mFirebaseOperations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		mFirebaseOperations = new FirebaseOperations(this);
		
		Handler h = new Handler();
		h.postDelayed(() -> {
			Class toggle = mFirebaseOperations.getCurrentUser() == null ? SignUp.class : Container.class;
			startActivity(new Intent(this, toggle));
			finish();
		}, 3000);
	}
}
