package team.android.projects.com.bookit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
	AlertDialog alertDialog1;
	CharSequence[] values = {" English "," Chinese "," Malay "};
	
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
		mChangeLanguage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CreateAlertDialogWithRadioButtonGroup();
			}
		});
		mClearHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					builder = new AlertDialog.Builder(getActivity());
				} else {
					builder = new AlertDialog.Builder(getActivity());
				}
				builder.setTitle("Clear History?")
						.setMessage("This will affect the recommendations that you will received from now on")
						.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// continue with delete
							}
						})
						.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// do nothing
							}
						})
						.show();
			}
		});
		mEditGenres.setOnClickListener(v -> Toast.makeText(getContext(), "Editing Genres", Toast.LENGTH_SHORT).show());
		mSignOut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					builder = new AlertDialog.Builder(getActivity());
				} else {
					builder = new AlertDialog.Builder(getActivity());
				}
				builder.setTitle("Sign out")
						.setMessage("Are you sure?")
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// continue with delete
								if (getActivity() != null) {
									getActivity().finish();
								}
							}
						})
						.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// do nothing
							}
						})
						.show();
			}
		});
	}
	public void CreateAlertDialogWithRadioButtonGroup(){


		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle("Select Language");

		builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {

				switch(item)
				{
					case 0:

						Toast.makeText(getActivity(), "English", Toast.LENGTH_LONG).show();
						break;
					case 1:

						Toast.makeText(getActivity(), "Chinese", Toast.LENGTH_LONG).show();
						break;
					case 3:

						Toast.makeText(getActivity(), "Malay", Toast.LENGTH_LONG).show();
						break;
				}
				alertDialog1.dismiss();
			}
		});
		alertDialog1 = builder.create();
		alertDialog1.show();

	}


}
