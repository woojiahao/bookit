package team.android.projects.com.bookit.ui.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import static team.android.projects.com.bookit.ui.decorators.Direction.Bottom;
import static team.android.projects.com.bookit.ui.decorators.Direction.Left;
import static team.android.projects.com.bookit.ui.decorators.Direction.Right;
import static team.android.projects.com.bookit.ui.decorators.Direction.Top;

/**
 * Auto determines the spacing needed for elements inside of a RecyclerView
 * User specifies the horizontal and vertical spacing between each column and row, and the
 * class will auto assign the padding to the elements
 */
// todo: refactor and explain this crap
public class SpacingDecoration extends RecyclerView.ItemDecoration {
	private int mHorizontalSpacing;
	private int mVerticalSpacing;
	private int mColumnCount;
	
	public SpacingDecoration(int horizontalSpacing, int verticalSpacing, int columnCount)
			throws SpacingDecorationError {
		mHorizontalSpacing = horizontalSpacing;
		mVerticalSpacing = verticalSpacing;
		
		if (columnCount <= 0) {
			throw new SpacingDecorationError("Cannot have a column count of 0 or less");
		}
		
		mColumnCount = columnCount;
	}
	
	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
							   RecyclerView.State state) {
		
		int viewPos = parent.getChildAdapterPosition(view);
		int itemCount = parent.getAdapter().getItemCount();
		int rowCount = (int) Math.ceil((double) itemCount / mColumnCount);
		Map<Direction, Integer> map = generateMap(viewPos, itemCount, rowCount, mColumnCount);
		
		outRect.set(map.get(Left), map.get(Top), map.get(Right), map.get(Bottom));
	}
	
	private Map<Direction, Integer> generateMap(int viewPos, int itemCount, int rowCount, int columnCount) {
		// detecting if item is along a side
		boolean alongLeft = viewPos % columnCount == 0;
		boolean alongRight = viewPos % columnCount == columnCount - 1;
		boolean alongTop = viewPos < columnCount;
		boolean alongBottom = viewPos >= (itemCount - columnCount);
		
		// detecting if item is at a corner
		boolean topLeft = alongLeft && alongTop;
		boolean topRight = alongRight && alongTop;
		boolean bottomLeft = alongLeft && alongBottom;
		boolean bottomRight = alongRight && alongBottom;
		
		int td = mVerticalSpacing / 2;
		int lr = mHorizontalSpacing / 2;
		
		int left, right, top, bottom;
		left = right = top = bottom = 0;
		
		if (rowCount == 1 && columnCount != 1) {
			if (alongLeft) {
				right = lr;
			} else if (alongRight) {
				left = lr;
			} else {
				left = right = lr;
			}
		} else if (columnCount == 1 && rowCount != 1) {
			if (alongTop) {
				bottom = td;
			} else if (alongBottom) {
				top = td;
			} else {
				top = bottom = td;
			}
		} else if (rowCount > 1 && columnCount > 1) {
			int curRow = (int) Math.floor((double) viewPos / columnCount) + 1;
			if (alongBottom) {
				bottom = 4;
			}
			
			if (curRow != rowCount && bottomRight) {
				bottom = mVerticalSpacing;
				top = td;
				left = lr;
			} else if (curRow == rowCount - 1 && !bottomRight) {
				bottom = mVerticalSpacing;
				top = td;
				right = lr;
			} else if ((viewPos + 1 == itemCount) && !bottomRight) {
				top = 0;
				right = lr;
			} else if (topLeft || topRight || bottomLeft || bottomRight) {
				bottom = topLeft || topRight ? td : 0;
				top = bottomLeft || bottomRight ? td : 0;
				right = topLeft || bottomLeft ? lr : 0;
				left = topRight || bottomRight ? lr : 0;
			} else {
				if (alongLeft || alongRight || alongTop || alongBottom) {
					top = bottom = alongLeft || alongRight ? td : 0;
					left = right = alongTop || alongBottom ? lr : 0;
					
					if (alongLeft) {
						right = lr;
					} else if (alongRight) {
						left = lr;
					} else if (alongTop) {
						bottom = td;
					} else if (alongBottom) {
						top = td;
						bottom = 4;
					}
				} else {
					top = bottom = td;
					left = right = lr;
				}
			}
		}
		
		final int fleft = left;
		final int fright = right;
		final int ftop = top;
		final int fbottom = bottom;
		
		return new HashMap<Direction, Integer>() {{
			put(Left, fleft);
			put(Right, fright);
			put(Top, ftop);
			put(Bottom, fbottom);
		}};
	}
}