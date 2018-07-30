package team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;

import team.android.projects.com.bookit.R;

import static team.android.projects.com.bookit.utils.ui.UIUtils.clearInputs;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

// todo: use normal inputType for passwords but replace each character with * each time they type it
public class ClearableEditText extends CardView {
	private EditText mEditText;
	private Button mClearBtn;
	
	private boolean mIsClearable;
	private String mHint;
	private int mInputType;
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
			
			if (arr.hasValue(R.styleable.ClearableEditText_hint)) {
				mHint = arr.getString(R.styleable.ClearableEditText_hint);
			}
			mHint = arr.hasValue(R.styleable.ClearableEditText_hint) ? arr.getString(R.styleable.ClearableEditText_hint) : "Placeholder";
			mIcon = arr.hasValue(R.styleable.ClearableEditText_sideIcon) ? arr.getDrawable(R.styleable.ClearableEditText_sideIcon) : getResources().getDrawable(R.drawable.ic_search_grey_24dp);
			mIsClearable = arr.getBoolean(R.styleable.ClearableEditText_isClearable, true);
			mInputType = arr.getInt(R.styleable.ClearableEditText_inputType, 0);
			Log.d("Clearable", "Input type: " + String.valueOf(mInputType));
			
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
		switch (mInputType) {
			case 0:
				mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
				break;
			case 1:
				mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
				break;
			case 2:
				mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				break;
		}
		
		mClearBtn = find(this, R.id.clearBtn);
		mClearBtn.setVisibility(GONE);
	}
	
	private void connectListeners() {
		setOnTypingListener(
				(s, start, count, after) ->
						mClearBtn.setVisibility(s.toString().equals("") ? GONE : mIsClearable ? VISIBLE : GONE));
		mClearBtn.setOnClickListener(view -> clearInputs(mEditText));
	}
	
	public void clearInput() {
		mEditText.setText("");
	}
	
	public String getText() {
		return mEditText.getText().toString().trim();
	}
	
	public void setOnTypingListener(IEditTextChanging listener) {
		mEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override public void afterTextChanged(Editable s) {
			
			}
			
			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {
				listener.textChanging(s, start, before, count);
			}
		});
	}
}
