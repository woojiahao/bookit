package team.android.projects.com.booktit.utils;

import android.widget.EditText;

public class UIUtils {
	public static void clearInputs (EditText... editTexts) {
		for (EditText e : editTexts) {
			e.setText("");
		}
	}
}
