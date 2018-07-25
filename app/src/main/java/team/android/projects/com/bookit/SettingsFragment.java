package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import team.android.projects.com.bookit.utils.ui.custom_views.settings_row.SettingsRow;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class SettingsFragment extends Fragment {
	private View mView;
	private SettingsRow mChangeLanguage;
	private SettingsRow mClearHistory;
	private SettingsRow mEditGenres;
	private SettingsRow mSignOut;
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_settings, container, false);
		init();
		connectListeners();
		return mView;
	}
	
	private void init() {
		mChangeLanguage = find(mView, R.id.settingsChangeLanguage);
		mClearHistory = find(mView, R.id.settingsClearHistory);
		mEditGenres = find(mView, R.id.settingsEditGenres);
		mSignOut = find(mView, R.id.settingsSignOut);
	}
	
	private void connectListeners() {
		mChangeLanguage.setOnClickListener(v -> Toast.makeText(getContext(), "Changing language", Toast.LENGTH_SHORT).show());
		mClearHistory.setOnClickListener(v -> Toast.makeText(getContext(), "Clearing History", Toast.LENGTH_SHORT).show());
		mEditGenres.setOnClickListener(v -> Toast.makeText(getContext(), "Editing Genres", Toast.LENGTH_SHORT).show());
		mSignOut.setOnClickListener(v -> {
			if (getActivity() != null) {
				getActivity().finish();
			}
		});
	}
}
