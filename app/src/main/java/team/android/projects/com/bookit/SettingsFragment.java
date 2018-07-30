package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import team.android.projects.com.bookit.utils.database.FirebaseOperations;
import team.android.projects.com.bookit.utils.database.IFirebaseOperations;
import team.android.projects.com.bookit.utils.ui.custom_views.settings_row.SettingsRow;

import static team.android.projects.com.bookit.utils.logging.ApplicationCodes.Debug;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

// todo: convert the settings row into a recyclerview and use the dividers item decoration instead to show the dividers
// todo: add a sharedpreference for the current language selected and load then on run at the splashscreen
public class SettingsFragment extends Fragment {
	private View mView;
	private SettingsRow mChangeLanguage;
	private SettingsRow mClearHistory;
	private SettingsRow mEditGenres;
	private SettingsRow mSignOut;
	
	private TextView mUsername;
	
	private IFirebaseOperations mFirebaseOperations;
	
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
		mFirebaseOperations = new FirebaseOperations(getContext());
		
		mChangeLanguage = find(mView, R.id.settingsChangeLanguage);
		mClearHistory = find(mView, R.id.settingsClearHistory);
		mEditGenres = find(mView, R.id.settingsEditGenres);
		mSignOut = find(mView, R.id.settingsSignOut);
		
		mUsername = find(mView, R.id.settingsUsername);
//		mUsername.setText(mFirebaseOperations.getUsername());
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
							.setPositiveButton(
									android.R.string.ok,
									(dialog, which) -> {
									})
							.setNegativeButton(android.R.string.cancel, null)
							.show());
			mSignOut.setOnClickListener(v ->
					new AlertDialog.Builder(getContext())
							.setTitle(R.string.sign_out)
							.setMessage(R.string.sign_out_warning)
							.setPositiveButton(android.R.string.yes, (dialog, which) -> {
								if (getActivity() != null) {
									mFirebaseOperations.signOut();
									startActivity(new Intent(getContext(), SignIn.class));
									getActivity().finish();
								}
							})
							.setNegativeButton(android.R.string.no, null)
							.show());
			mEditGenres.setOnClickListener(v -> startActivity(new Intent(getContext(), EditGenres.class)));
		}
	}
}
