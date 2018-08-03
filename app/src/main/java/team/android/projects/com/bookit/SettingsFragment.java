package team.android.projects.com.bookit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

import team.android.projects.com.bookit.dataclasses.User;
import team.android.projects.com.bookit.database.FirebaseOperations;
import team.android.projects.com.bookit.database.IFirebaseOperations;
import team.android.projects.com.bookit.database.UsersList;
import team.android.projects.com.bookit.ui.custom.settings_row.SettingsRow;

import static team.android.projects.com.bookit.logging.Logging.shortToast;
import static team.android.projects.com.bookit.util.UIUtils.find;

// todo: convert the settings row into a recyclerview and use the dividers item decoration instead to show the dividers
public class SettingsFragment extends Fragment {
	private View mView;
	private SettingsRow mChangeLanguage;
	private SettingsRow mEditGenres;
	private SettingsRow mSignOut;
	
	private TextView mUsername;
	private TextView mEmail;
	
	private IFirebaseOperations mFirebaseOperations;
	
	private final String[] mLanguages = { "English", "Chinese", "Malay" };
	
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
		mEditGenres = find(mView, R.id.settingsEditGenres);
		mSignOut = find(mView, R.id.settingsSignOut);
		
		mUsername = find(mView, R.id.settingsUsername);
		mEmail = find(mView, R.id.settingsEmail);
		
		User u = UsersList.getCurrentUser();
		if (u != null) {
			mUsername.setText(u.username);
			mEmail.setText(u.email);
		}
	}
	
	private void connectListeners() {
		if (getContext() != null) {
			mChangeLanguage.setOnClickListener(v -> {
				SharedPreferences sharedPreferences = getContext()
						.getSharedPreferences(
								getString(R.string.preference_key),
								Context.MODE_PRIVATE);
				String deviceLanguage = sharedPreferences
						.getString(getString(R.string.default_lang_key),
								"English");
				StringBuilder selection = new StringBuilder();
				
				new AlertDialog.Builder(getContext())
						.setTitle(R.string.select_language)
						.setSingleChoiceItems(
								mLanguages,
								Arrays.asList(mLanguages).indexOf(deviceLanguage),
								(dialog, item) -> {
									switch (item) {
										case 0:
											selection.append("English");
											break;
										case 1:
											selection.append("Chinese");
											break;
										case 2:
											selection.append("Malay");
											break;
									}
									shortToast(getContext(), selection.toString());
									sharedPreferences
											.edit()
											.remove(getString(R.string.default_lang_key))
											.putString(getString(R.string.default_lang_key),
													selection.toString()).apply();
									dialog.dismiss();
								}).show();
			});
			mSignOut.setOnClickListener(v ->
					new AlertDialog.Builder(getContext())
							.setTitle(R.string.sign_out)
							.setMessage(R.string.sign_out_warning)
							.setPositiveButton(android.R.string.yes, (dialog, which) -> {
								if (getActivity() != null) {
									mFirebaseOperations.signOut();
									UsersList.setCurrentUser(null);
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
