package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class StatusFragment extends Fragment {
	private View mView;
	private String mStatusMessage;
	private boolean mStatus;
	
	private ImageView mStatusIcon;
	private TextView mStatusMessageText;
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_status, container, false);
		init();
		
		return mView;
	}
	
	private void init() {
		Bundle extras = getArguments();
		
		if (extras != null) {
			mStatusMessage = extras.getString("message");
			mStatus = extras.getBoolean("message");
		}
		
		mStatusIcon = find(mView, R.id.statusIcon);
		mStatusIcon.setImageResource(mStatus ? R.drawable.success_outline : R.drawable.error_outline);
		
		mStatusMessageText = find(mView, R.id.statusMessage);
		mStatusMessageText.setText(mStatusMessage);
	}
}
