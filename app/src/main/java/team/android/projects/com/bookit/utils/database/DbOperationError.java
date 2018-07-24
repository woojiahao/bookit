package team.android.projects.com.bookit.utils.database;

import android.util.Log;

public class DbOperationError extends Exception {
	public DbOperationError (String message) {
		super(message);
		Log.e("DATABASE ERROR", message);
	}
}
