package team.android.projects.com.bookit.utils.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.dataclasses.Genre;

public class GenreSimpleAdapter extends RecyclerView.Adapter<GenreSimpleAdapter.ViewHolder> {
	private List<Genre> mGenres;
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		private View mView;
		private TextView mGenreTitle;
		
		ViewHolder (View v) {
			super(v);
			mView = v;
			
			mGenreTitle = mView.findViewById(R.id.genreTitle);
		}
		
		public void setTitle (String title) {
			mGenreTitle.setText(title);
		}
	}
	
	public GenreSimpleAdapter (List<Genre> genres) {
		mGenres = genres;
	}
	
	@NonNull @Override
	public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(
				LayoutInflater
						.from(parent.getContext())
						.inflate(R.layout.genre_card_simple, parent, false));
	}
	
	@Override
	public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
		Genre g = mGenres.get(position);
		
		holder.setTitle(g.getGenreTitle());
		
		holder.mView.setOnClickListener(v -> {
			Toast.makeText(v.getContext(), "Pressed: " + mGenres.get(holder.getAdapterPosition()).getGenreTitle(), Toast.LENGTH_SHORT).show();
		});
	}
	
	@Override public int getItemCount () {
		return mGenres.size();
	}
}
