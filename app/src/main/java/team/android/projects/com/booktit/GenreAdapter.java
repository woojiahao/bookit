package team.android.projects.com.booktit;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GenreAdapter extends BaseAdapter {
	private List<Genre> genresList;
	
	public GenreAdapter (List<Genre> genres) {
		genresList = genres;
	}
	
	@Override public int getCount () {
		return genresList.size();
	}
	
	@Override public Object getItem (int position) {
		return null;
	}
	
	@Override public long getItemId (int position) {
		return 0;
	}
	
	@Override public View getView (int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.genre_card, parent, false);
		}
		
		Genre genre = genresList.get(position);
		
		final TextView genreTitle = convertView.findViewById(R.id.genreTitle);
		final ImageView genreImage = convertView.findViewById(R.id.genreImage);
		final ImageView genreIcon = convertView.findViewById(R.id.genreIcon);
		
		genreTitle.setText(genre.getGenreTitle());
		genreImage.setImageResource(genre.getGenreImage());
		genreIcon.setImageResource(genre.getGenreIcon());
		genreIcon.setColorFilter(Color.parseColor("#FFFFFF"));
		
		return convertView;
	}
}
