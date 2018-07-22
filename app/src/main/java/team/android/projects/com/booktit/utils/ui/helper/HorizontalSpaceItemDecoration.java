package team.android.projects.com.booktit.utils.ui.helper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {
	private final int horizontalSpaceHeight;
	
	public HorizontalSpaceItemDecoration (int horizontalSpaceHeight) {
		this.horizontalSpaceHeight = horizontalSpaceHeight;
	}
	
	@Override
	public void getItemOffsets (Rect outRect, View view, RecyclerView parent,
								RecyclerView.State state) {
		outRect.set(0, 0, horizontalSpaceHeight, 0);
	}
}
