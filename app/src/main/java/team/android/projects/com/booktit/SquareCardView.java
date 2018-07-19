package team.android.projects.com.booktit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

public class SquareCardView extends CardView {
	public SquareCardView (@NonNull Context context) {
		super(context);
	}
	
	public SquareCardView (@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SquareCardView (@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@Override protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
	}
}
