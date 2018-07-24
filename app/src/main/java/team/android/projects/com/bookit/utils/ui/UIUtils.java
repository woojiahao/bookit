package team.android.projects.com.bookit.utils.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text.ClearableEditText;
import team.android.projects.com.bookit.GenreSelectionFragment;
import team.android.projects.com.bookit.R;

public class UIUtils {
	public static void clearInputs(EditText... editTexts) {
		for (EditText e : editTexts) {
			e.setText("");
		}
	}
	
	public static void clearInputs(ClearableEditText... clearableEditTexts) {
		for (ClearableEditText e : clearableEditTexts) {
			e.clearInput();
		}
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
															boolean multiSelection) {
		Bundle b = new Bundle();
		b.putString("title", title);
		b.putInt("columns", columnCount);
		b.putBoolean("multiSelection", multiSelection);
		GenreSelectionFragment f = new GenreSelectionFragment();
		f.setArguments(b);
		if (manager != null) {
			manager.beginTransaction().add(R.id.genreSelection, f, tag).commit();
		}
		return f;
	}
}
