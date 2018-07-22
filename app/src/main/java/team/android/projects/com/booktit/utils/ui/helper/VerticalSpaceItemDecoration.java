package team.android.projects.com.booktit.utils.ui.helper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
	
	private final int mVerticalSpaceHeight;
	private final boolean mEffectLast;
	
	public VerticalSpaceItemDecoration(int verticalSpaceHeight, boolean effectLast) {
		mVerticalSpaceHeight = verticalSpaceHeight;
		mEffectLast = effectLast;
	}
	
	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
							   RecyclerView.State state) {
		if (!mEffectLast) {
			if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
				outRect.bottom = mVerticalSpaceHeight;
			}
		} else {
			outRect.bottom = mVerticalSpaceHeight;
		}
	}
}