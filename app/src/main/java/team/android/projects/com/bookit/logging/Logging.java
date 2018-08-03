package team.android.projects.com.bookit.logging;

import android.content.Context;
import android.widget.Toast;

public class Logging {
	public static void shortToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
