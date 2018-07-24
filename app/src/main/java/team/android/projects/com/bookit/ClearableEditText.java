package team.android.projects.com.bookit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import static team.android.projects.com.bookit.utils.ui.UIUtils.clearInputs;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class ClearableEditText extends RelativeLayout {
	private EditText mEditText;
	private Button mClearBtn;
	
	private boolean isClearable;
	private String mHint;
	private Drawable mIcon;
	
	public ClearableEditText(Context context) {
		super(context);
		setup(null);
	}
	
	public ClearableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup(attrs);
	}
	
	public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup(attrs);
	}
	
	private void setup(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.ClearableEditText, 0, 0);
			
			mHint = arr.getString(R.styleable.ClearableEditText_hint);
			mIcon = arr.getDrawable(R.styleable.ClearableEditText_sideIcon);
			
			arr.recycle();
		}
		
		init();
		connectListeners();
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.clearable_edit_text, this, true);
		
		mEditText = find(this, R.id.textField);
		mEditText.setCompoundDrawablesWithIntrinsicBounds(mIcon, null, null, null);
		mEditText.setHint(mHint);
		mClearBtn = find(this, R.id.clearBtn);
	}
	
	private void connectListeners() {
		mClearBtn.setOnClickListener(view -> clearInputs(mEditText));
	}
}
