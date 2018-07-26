package team.android.projects.com.bookit.utils.ui.custom_views.settings_row;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import team.android.projects.com.bookit.R;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class SettingsRow extends LinearLayout {
	private TextView mSettingsTitle;
	private TextView mSettingsDescription;
	
	private String mSettingsTitleText;
	private String mSettingsDescriptionText;
	private int mSettingsTitleColor;
	private boolean mHasDescription;
	
	public SettingsRow(Context context) {
		super(context);
		setup(null);
	}
	
	public SettingsRow(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		setup(attrs);
	}
	
	public SettingsRow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setup(attrs);
	}
	
	private void setup(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.SettingsRow, 0, 0);
			mHasDescription = arr.getBoolean(R.styleable.SettingsRow_hasDescription, true);
			mSettingsTitleColor = arr.getColor(R.styleable.SettingsRow_titleColor, getContext().getResources().getColor(R.color.primaryTextColor));
			mSettingsDescriptionText = arr.hasValue(R.styleable.SettingsRow_description) ? arr.getString(R.styleable.SettingsRow_description) : "Placeholder";
			mSettingsTitleText = arr.hasValue(R.styleable.SettingsRow_title) ? arr.getString(R.styleable.SettingsRow_title) : "Placeholder";
			
			arr.recycle();
		}
		
		init();
		connectListeners();
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.settings_row, this, true);
		
		mSettingsTitle = find(this, R.id.settingsTitle);
		mSettingsTitle.setText(mSettingsTitleText);
		mSettingsTitle.setTextColor(mSettingsTitleColor);
		
		mSettingsDescription = find(this, R.id.settingsDescription);
		mSettingsDescription.setText(mSettingsDescriptionText);
		if (!mHasDescription) {
			mSettingsDescription.setVisibility(GONE);
		}
	}
	
	private void connectListeners() {
	
	}
}
