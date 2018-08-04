package team.android.projects.com.bookit.ui.adapters;

import android.content.Context;
import android.content.Intent;
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

import team.android.projects.com.bookit.GenreDisplay;
import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.dataclasses.Genre;

import static team.android.projects.com.bookit.util.UIUtils.find;

// todo: make this backward compatible
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
	private List<Genre> mGenres;
	private int mColumnCount;
	private boolean mMultiSelection;
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		private View mView;
		private TextView mGenreTitle;
		private ImageView mGenreCheck;
		
		ViewHolder(View itemView) {
			super(itemView);
			
			mView = itemView;
			
			mGenreTitle = find(mView, R.id.genreTitle);
			mGenreCheck = find(mView, R.id.genreCheck);
		}
		
		private void setTitle(String title) {
			mGenreTitle.setText(title);
		}
		
		private void setSelected(boolean isSelected) {
			if (isSelected) {
				mGenreTitle.setBackground(mView.getContext().getResources().getDrawable(R.drawable.white_tint));
				mGenreTitle.setTextColor(Color.parseColor("#212121"));
				mGenreCheck.setVisibility(View.VISIBLE);
			} else {
				mGenreTitle.setBackground(mView.getContext().getResources().getDrawable(R.drawable.black_tint));
				mGenreTitle.setTextColor(Color.parseColor("#FFFFFF"));
				mGenreCheck.setVisibility(View.GONE);
			}
		}
		
	}
	
	public GenreAdapter(List<Genre> genres, int columnCount, boolean multiSelection) {
		mGenres = genres;
		mColumnCount = columnCount;
		mMultiSelection = multiSelection;
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
		if (mMultiSelection) {
			holder.setSelected(g.getIsSelected());
		}
		
		holder.mView.setOnClickListener(v -> {
			if (mMultiSelection) {
				g.setIsSelected(!g.getIsSelected());
				holder.setSelected(g.getIsSelected());
			} else {
				Context c = holder.mView.getContext();
				c.startActivity(new Intent(c, GenreDisplay.class).putExtra("title", g.getGenreTitle()));
			}
		});
	}
	
	@Override public int getItemCount() {
		return mGenres.size();
	}
}
