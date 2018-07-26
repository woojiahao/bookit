package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		Handler h = new Handler();
		h.postDelayed(() -> {
			startActivity(new Intent(this, SignUp.class));
			finish();
		}, 3000);
	}
}
