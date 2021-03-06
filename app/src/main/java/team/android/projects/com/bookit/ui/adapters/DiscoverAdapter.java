package team.android.projects.com.bookit.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.dataclasses.BookGroup;
import team.android.projects.com.bookit.ui.decorators.SpacingDecoration;
import team.android.projects.com.bookit.ui.decorators.SpacingDecorationError;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {
	private List<BookGroup> mBookGroups;
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		private View mView;
		
		private TextView mCategoryTitle;
		private RecyclerView mBooksRow;
		
		ViewHolder(View v) {
			super(v);
			mView = v;
			
			mCategoryTitle = mView.findViewById(R.id.discoverCategoryTitle);
			mBooksRow = mView.findViewById(R.id.discoverArea);
		}
		
		void setCategoryTitle(String title) {
			mCategoryTitle.setText(title);
		}
		
		void setBooksRow(List<Book> books) {
			mBooksRow = mView.findViewById(R.id.discoverRecyclerView);
			
			BookCardAdapter adapter = new BookCardAdapter(mView.getContext(), books);
			LinearLayoutManager manager = new LinearLayoutManager(
					mView.getContext(),
					LinearLayoutManager.HORIZONTAL,
					false);
			
			mBooksRow.setLayoutManager(manager);
			try {
				mBooksRow.addItemDecoration(new SpacingDecoration(32, 32, adapter.getItemCount()));
			} catch (SpacingDecorationError e) {
				e.printStackTrace();
			}
			mBooksRow.setAdapter(adapter);
		}
	}
	
	public DiscoverAdapter(List<BookGroup> books) {
		mBookGroups = books;
	}
	
	@NonNull @Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(
				LayoutInflater.from(parent.getContext())
						.inflate(R.layout.discover_row, parent, false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		BookGroup group = mBookGroups.get(position);
		
		holder.setCategoryTitle(group.getTitle());
		holder.setBooksRow(group.getBooks());
	}
	
	@Override public int getItemCount() {
		return mBookGroups.size();
	}
}
