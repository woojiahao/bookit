package team.android.projects.com.booktit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SignUpGenreSelection extends AppCompatActivity {
	
	private ImageView mBackButton;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_genre_selection);
		
		init();
		connectListeners();
	}
	
	private void init () {
		mBackButton = findViewById(R.id.signUpGenreSelectionBackBtn);
	}
	
	private void connectListeners () {
		mBackButton.setOnClickListener(ev -> {
			finish();
		});
	}
}
