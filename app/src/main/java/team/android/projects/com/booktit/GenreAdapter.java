package team.android.projects.com.booktit;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.android.projects.com.booktit.dataclasses.Genre;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
	private List<Genre> mGenres;
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		private View mView;
		private TextView mGenreTitle;
		private ImageView mSelected;
		
		ViewHolder (View itemView) {
			super(itemView);
			
			mView = itemView;
			
			mGenreTitle = mView.findViewById(R.id.genreTitle);
			mSelected = mView.findViewById(R.id.genreSelected);
		}
		
		private void setTitle (String title) {
			mGenreTitle.setText(title);
		}
		
		private void setSelected (boolean isSelected) {
			if (isSelected) {
				mSelected.setImageResource(R.drawable.ic_clear_black_24dp);
				mSelected.setBackgroundColor(Color.parseColor("#F44336"));
			} else {
				mSelected.setImageResource(R.drawable.ic_add_black_24dp);
				mSelected.setBackgroundColor(Color.parseColor("#00E676"));
			}
		}
	}
	
	GenreAdapter (List<Genre> genres) {
		mGenres = genres;
	}
	
	@NonNull @Override
	public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		
		return new ViewHolder(inflater.inflate(R.layout.genre_card, parent, false));
	}
	
	@Override public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
		Genre g = mGenres.get(position);
		holder.setTitle(g.getGenreTitle());
		holder.setSelected(g.getIsSelected());
		
		holder.mView.setOnClickListener(v -> {
			g.setIsSelected(!g.getIsSelected());
			Toast.makeText(v.getContext(), "Pressed", Toast.LENGTH_SHORT).show();
			holder.setSelected(g.getIsSelected());
		});
	}
	
	@Override public int getItemCount () {
		return mGenres.size();
	}
}
