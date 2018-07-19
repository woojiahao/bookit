package team.android.projects.com.booktit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;

public class SignUpGenreSelection extends AppCompatActivity {
	
	private ImageView mBackBtn;
	private Button mMakeSelectionBtn;
	private EditText mGenreSearchField;
	private GridView mGenreSelectionArea;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_genre_selection);
		
		init();
		connectListeners();
	}
	
	private void init () {
		mBackBtn = findViewById(R.id.signUpGenreSelectionBackBtn);
		mMakeSelectionBtn = findViewById(R.id.makeSelectionBtn);
		mGenreSearchField = findViewById(R.id.genreSearchField);
		
		mGenreSelectionArea = findViewById(R.id.genreSelectionArea);
		final BaseAdapter adapter = new GenreAdapter(Arrays.asList(
				new Genre("Horror", R.drawable.icon_horror, R.drawable.image_horror),
				new Genre("Horror", R.drawable.icon_horror, R.drawable.image_horror),
				new Genre("Horror", R.drawable.icon_horror, R.drawable.image_horror),
				new Genre("Horror", R.drawable.icon_horror, R.drawable.image_horror),
				new Genre("Horror", R.drawable.icon_horror, R.drawable.image_horror)
		));
		mGenreSelectionArea.setAdapter(adapter);
	}
	
	private void connectListeners () {
		mBackBtn.setOnClickListener(ev -> {
			finish();
		});
		
		mMakeSelectionBtn.setOnClickListener(ev -> {
			Toast.makeText(this, "Making selection", Toast.LENGTH_SHORT).show();
		});
		
		mGenreSearchField.addTextChangedListener(new TextWatcher() {
			@Override public void afterTextChanged (Editable s) {
				Toast.makeText(
						SignUpGenreSelection.this,
						"Typing: " + s.toString(),
						Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void beforeTextChanged (CharSequence s, int start, int count, int after) { }
			@Override public void onTextChanged (CharSequence s, int start, int before, int count) { }
		});
	}
}
