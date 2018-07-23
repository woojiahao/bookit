package team.android.projects.com.bookit.utils.ui.helper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class SpacingDecoration extends RecyclerView.ItemDecoration {
	
	private final int mLeftSpacing;
	private final int mRightSpacing;
	private final int mTopSpacing;
	private final int mBottomSpacing;
	private final int mColumnCount;
	
	public SpacingDecoration(int left, int right, int top, int bottom, int columnCount) {
		mLeftSpacing = left;
		mRightSpacing = right;
		mTopSpacing = top;
		mBottomSpacing = bottom;
		mColumnCount = columnCount;
	}
	
	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
							   RecyclerView.State state) {
		
		int viewPos = parent.getChildAdapterPosition(view);
		int left = viewPos % mColumnCount == 0 ? 0 : mLeftSpacing;
		int right = viewPos % mColumnCount == mColumnCount - 1 ? 0 : mRightSpacing;
		int top = viewPos < mColumnCount ? 0 : mTopSpacing;
//		int bottom = viewPos >= (parent.getAdapter().getItemCount() - mColumnCount) ? 0 : mBottomSpacing;
		
		outRect.set(left, top, right, mBottomSpacing);
	}
}