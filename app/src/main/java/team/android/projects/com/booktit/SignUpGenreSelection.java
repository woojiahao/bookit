package team.android.projects.com.booktit;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class SignUpGenreSelection extends AppCompatActivity {
	
	private ImageView mBackBtn;
	private Button mMakeSelectionBtn;
	private EditText mGenreSearchField;
	private GridView mGenreSelectionArea;
	
	private final List<Genre> mGenres = Arrays.asList(
			new Genre("Horror", R.drawable.icon_horror, R.drawable.image_horror),
			new Genre("Mystery", R.drawable.ic_search_black_24dp, R.drawable.image_horror),
			new Genre("Literature", R.drawable.icon_horror, R.drawable.image_horror),
			new Genre("Comics", R.drawable.icon_horror, R.drawable.image_horror),
			new Genre("Romance", R.drawable.ic_baseline_arrow_back_24px, R.drawable.image_horror),
			new Genre("History", R.drawable.icon_horror, R.drawable.image_horror),
			new Genre("Comedy", R.drawable.icon_horror, R.drawable.image_horror),
			new Genre("Self-Help", R.drawable.icon_horror, R.drawable.image_horror)
	);
	
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
		final BaseAdapter adapter = new GenreAdapter(mGenres);
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
			public void beforeTextChanged (CharSequence s, int start, int count, int after) {
			}
			
			@Override public void onTextChanged (CharSequence s, int start, int before, int count) {
			}
		});
		
		mGenreSelectionArea.setOnItemClickListener((parent, view, pos, id) -> {
			Genre tapped = mGenres.get(pos);
			
			final TextView genreTitle = view.findViewById(R.id.genreTitle);
			final ImageView genreImage = view.findViewById(R.id.genreImage);
			final ImageView genreIcon = view.findViewById(R.id.genreIcon);
			
			if (!tapped.getIsSelected()) {
				tapped.setScheme(Genre.ColorScheme.WHITE);
				tapped.setIsSelected(true);
				Toast.makeText(this, "Selecting", Toast.LENGTH_SHORT).show();
				
				genreImage.setImageResource(R.drawable.image_romance);
				genreTitle.setTextColor(Color.parseColor("#212121"));
				genreIcon.setColorFilter(Color.parseColor("#212121"));
			} else {
				tapped.setScheme(Genre.ColorScheme.BLACK);
				tapped.setIsSelected(false);
				
				Toast.makeText(this, "Deselecting", Toast.LENGTH_SHORT).show();
				
				genreImage.clearColorFilter();
				genreTitle.setTextColor(Color.parseColor("#FFFFFF"));
				genreIcon.setColorFilter(Color.parseColor("#FFFFFF"));
			}
		});
	}
}
