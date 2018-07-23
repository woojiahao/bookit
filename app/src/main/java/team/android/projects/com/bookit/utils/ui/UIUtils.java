package team.android.projects.com.bookit.utils.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import team.android.projects.com.bookit.GenreSelectionFragment;
import team.android.projects.com.bookit.R;

public class UIUtils {
	public static void clearInputs(EditText... editTexts) {
		for (EditText e : editTexts) {
			e.setText("");
		}
	}
	
	public static <T extends View> T find(View parent, int targetID) {
		return (T) parent.findViewById(targetID);
	}
	
	public static <T extends View> T find(AppCompatActivity parent, int targetID) {
		return (T) parent.findViewById(targetID);
	}
	
	public static void loadGenreSelection(FragmentManager manager, String title, int columnCount) {
		Bundle b = new Bundle();
		b.putString("title", title);
		b.putInt("columns", columnCount);
		Fragment f = new GenreSelectionFragment();
		f.setArguments(b);
		if (manager != null) {
			manager.beginTransaction().add(R.id.genreSelection, f).commit();
		}
	}
	
	public static int dpToPx(Context c, int dp) {
		DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
		return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	}
	public static int pxToDp(Context c, int px) {
		DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
		return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	}
	
}
