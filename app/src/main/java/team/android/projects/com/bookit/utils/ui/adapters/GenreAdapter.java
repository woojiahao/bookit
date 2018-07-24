package team.android.projects.com.bookit.utils.ui.adapters;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.dataclasses.Genre;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

// todo: make this backward compatible
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
	private List<Genre> mGenres;
	private int mColumnCount;
	private boolean mMultiSelection;
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		private View mView;
		private TextView mGenreTitle;
		private ImageView mGenreBackground;
		
		ViewHolder(View itemView) {
			super(itemView);
			
			mView = itemView;
			
			mGenreTitle = find(mView, R.id.genreTitle);
			mGenreBackground = find(mView, R.id.genreBackground);
		}
		
		private void setTitle(String title) {
			mGenreTitle.setText(title);
		}
		
		private void setGenreBackground(int id) {
			mGenreBackground.setImageResource(id);
		}
		
		private void setSelected(boolean isSelected) {
			if (isSelected) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					mGenreBackground.setForeground(mView.getContext().getDrawable(R.drawable.white_tint));
					mGenreTitle.setTextColor(Color.parseColor("#212121"));
				} else {
					Toast.makeText(
							mView.getContext(),
							String.format("%s has been selected!", mGenreTitle.getText().toString()),
							Toast.LENGTH_SHORT).show();
				}
			} else {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					mGenreBackground.setForeground(mView.getContext().getDrawable(R.drawable.black_tint));
					mGenreTitle.setTextColor(Color.parseColor("#FFFFFF"));
				} else {
					Toast.makeText(
							mView.getContext(),
							String.format("%s has been deselected!", mGenreTitle.getText().toString()),
							Toast.LENGTH_SHORT).show();
				}
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
		holder.setGenreBackground(g.getGenreBackground());
		if (mMultiSelection) {
			holder.setSelected(g.getIsSelected());
		}
		
		holder.mView.setOnClickListener(v -> {
			if (mMultiSelection) {
				g.setIsSelected(!g.getIsSelected());
				holder.setSelected(g.getIsSelected());
			} else {
				Toast.makeText(v.getContext(), "Finishing Selection", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override public int getItemCount() {
		return mGenres.size();
	}
}
