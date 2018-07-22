package team.android.projects.com.booktit.utils;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.FrameLayout;

import team.android.projects.com.booktit.R;

public class UIUtils {
	public static void clearInputs (EditText... editTexts) {
		for (EditText e : editTexts) {
			e.setText("");
		}
	}
	
	public static void setBottomBarSelection (AppCompatActivity parentActivity, int selectedItem) {
		BottomNavigationView v = parentActivity
				.findViewById(R.id.navigationBar)
				.findViewById(R.id.navigationDrawer);
		v.setSelectedItemId(selectedItem);
	}
}
