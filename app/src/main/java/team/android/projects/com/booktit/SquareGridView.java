package team.android.projects.com.booktit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

public class SquareGridView extends GridView {
	private boolean mIsExpanded = false;
	
	public SquareGridView (Context context) {
		super(context);
	}
	
	public SquareGridView (Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SquareGridView (Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	public boolean isExpanded () {
		return mIsExpanded;
	}
	
	@Override
	public void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		if (isExpanded()) {
			int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
					MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
			
			ViewGroup.LayoutParams params = getLayoutParams();
			params.height = getMeasuredHeight();
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	
	public void setExpanded (boolean expanded) {
		mIsExpanded = expanded;
	}
}
