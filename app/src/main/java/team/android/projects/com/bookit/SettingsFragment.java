package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import team.android.projects.com.bookit.utils.ui.custom_views.settings_row.SettingsRow;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

// todo: convert the settings row into a recyclerview and use the dividers item decoration instead to show the dividers
public class SettingsFragment extends Fragment {
	private View mView;
	private SettingsRow mChangeLanguage;
	private SettingsRow mClearHistory;
	private SettingsRow mEditGenres;
	private SettingsRow mSignOut;
	
	private final String[] values = { "English", "Chinese", "Malay" };
	
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
		if (getContext() != null) {
			mChangeLanguage.setOnClickListener(v ->
					new AlertDialog.Builder(getContext())
							.setTitle(R.string.select_language)
							.setSingleChoiceItems(values, -1, (dialog, item) -> {
								switch (item) {
									case 0:
										Toast.makeText(getContext(), "English", Toast.LENGTH_LONG).show();
										break;
									case 1:
										Toast.makeText(getContext(), "Chinese", Toast.LENGTH_LONG).show();
										break;
									case 3:
										Toast.makeText(getContext(), "Malay", Toast.LENGTH_LONG).show();
										break;
								}
								dialog.dismiss();
							}).show());
			mClearHistory.setOnClickListener(v ->
					new AlertDialog.Builder(getContext())
							.setTitle(R.string.clear_history)
							.setMessage(R.string.clear_history_warning)
							.setPositiveButton(android.R.string.ok, (dialog, which) -> {
								// continue with delete
							})
							.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
								// do nothing
							})
							.show());
			mSignOut.setOnClickListener(v ->
					new AlertDialog.Builder(getContext())
							.setTitle(R.string.sign_out)
							.setMessage(R.string.sign_out_warning)
							.setPositiveButton(android.R.string.yes, (dialog, which) -> {
								// continue with delete
								if (getActivity() != null) {
									getActivity().finish();
								}
							})
							.setNegativeButton(android.R.string.no, (dialog, which) -> {
								// do nothing
							})
							.show());
			mEditGenres.setOnClickListener(v ->
					Toast.makeText(getContext(), "Editing Genres", Toast.LENGTH_SHORT).show());
		}
	}
}
