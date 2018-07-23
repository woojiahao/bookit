package team.android.projects.com.bookit.utils.ui.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.dataclasses.Genre;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
	private List<Genre> mGenres;
	private int mColumnCount;
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		private View mView;
		private TextView mGenreTitle;
		private ImageView mSelected;
		private ImageView mGenreBackground;
		
		ViewHolder(View itemView) {
			super(itemView);
			
			mView = itemView;
			
			mGenreTitle = find(mView, R.id.genreTitle);
			mGenreBackground = find(mView, R.id.genreBackground);
//			mSelected = mView.findViewById(R.id.genreSelected);
		}
		
		private void setTitle(String title) {
			mGenreTitle.setText(title);
		}
		
		private void setGenreBackground(int id) {
			mGenreBackground.setImageResource(id);
		}
		
		private void setSelected(boolean isSelected) {
			if (isSelected) {
				mSelected.setImageResource(R.drawable.ic_clear_black_24dp);
				mSelected.setBackgroundColor(Color.parseColor("#F44336"));
			} else {
				mSelected.setImageResource(R.drawable.ic_add_black_24dp);
				mSelected.setBackgroundColor(Color.parseColor("#00E676"));
			}
		}
	}
	
	public GenreAdapter(List<Genre> genres, int columnCount) {
		mGenres = genres;
		mColumnCount = columnCount;
	}
	
	@NonNull @Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View v = inflater.inflate(R.layout.genre_card, parent, false);
		
		return new ViewHolder(v);
	}
	
	@Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Genre g = mGenres.get(position);
		holder.setTitle(g.getGenreTitle());
		holder.setGenreBackground(g.getGenreBackground());
//		holder.setSelected(g.getIsSelected());

//		holder.mView.setOnClickListener(v -> {
//			g.setIsSelected(!g.getIsSelected());
//			Toast.makeText(v.getContext(), "Pressed", Toast.LENGTH_SHORT).show();
//			holder.setSelected(g.getIsSelected());
//		});
	}
	
	@Override public int getItemCount() {
		return mGenres.size();
	}
}
