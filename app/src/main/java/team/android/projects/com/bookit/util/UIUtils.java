package team.android.projects.com.bookit.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import team.android.projects.com.bookit.GenreSelectionFragment;
import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.database.IFirebaseOperations;
import team.android.projects.com.bookit.database.UsersList;
import team.android.projects.com.bookit.ui.ButtonStates;
import team.android.projects.com.bookit.ui.custom.clearable_edit_text.ClearableEditText;

public class UIUtils {
	public static void clearInputs(ClearableEditText... clearableEditTexts) {
		for (ClearableEditText e : clearableEditTexts) e.clearInput();
	}
	
	public static boolean isFilled(ClearableEditText... editTexts) {
		boolean allFilled = true;
		for (ClearableEditText e : editTexts) allFilled &= !e.getText().equals("");
		return allFilled;
	}
	
	public static boolean isEmail(String email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends View> T find(View parent, int targetID) {
		return (T) parent.findViewById(targetID);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends View> T find(AppCompatActivity parent, int targetID) {
		return (T) parent.findViewById(targetID);
	}
	
	public static GenreSelectionFragment loadGenreSelection(FragmentManager manager, String title,
															String tag, int columnCount,
															boolean multiSelection,
															String[] previousSelection) {
		Bundle b = new Bundle();
		b.putString("title", title);
		b.putInt("columns", columnCount);
		b.putBoolean("multiSelection", multiSelection);
		b.putStringArray("previousSelection", previousSelection);
		GenreSelectionFragment f = new GenreSelectionFragment();
		f.setArguments(b);
		if (manager != null) {
			manager.beginTransaction().add(R.id.genreSelection, f, tag).commit();
		}
		return f;
	}
	
	public static void displayExitConfirmDialog(Context c) {
		new AlertDialog.Builder(c)
				.setTitle(R.string.exit_warning)
				.setPositiveButton(
						"Yes",
						(dialog, which) -> {
							((Activity) c).finish();
						})
				.setNegativeButton("No", null)
				.setCancelable(false)
				.show();
		
	}
	
	public static void modifyRedButton(Context c, Button b, ButtonStates state) {
		boolean isEnabled = false;
		int backgroundRes = 0;
		switch (state) {
			case Enabled:
				isEnabled = true;
				backgroundRes = R.drawable.red_button_background;
				break;
			case Disabled:
				isEnabled = false;
				backgroundRes = R.drawable.red_button_background_disabled;
				break;
		}
		
		b.setEnabled(isEnabled);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			b.setBackground(c.getDrawable(backgroundRes));
		} else {
			b.setBackground(c.getResources().getDrawable(backgroundRes));
		}
	}
	
	public static void displayPopupMenu(Context c, View toAttach,
										String isbn,
										IFirebaseOperations firebaseOperations) {
		PopupMenu menu = new PopupMenu(c, toAttach);
		menu.inflate(R.menu.popup_menu_book);
		
		if (UsersList.hasFavourite(isbn)) {
			menu.getMenu().findItem(R.id.popupAdd).setVisible(false);
			menu.getMenu().findItem(R.id.popupRemove).setVisible(true);
		}
		
		menu.setOnMenuItemClickListener(item -> {
			switch (item.getItemId()) {
				case R.id.popupAdd:
					firebaseOperations.addFavourite(isbn);
					return true;
				case R.id.popupRemove:
					firebaseOperations.removeFavourite(isbn);
					return true;
			}
			
			return false;
		});
		menu.show();
	}
}
