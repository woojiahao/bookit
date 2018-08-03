package team.android.projects.com.bookit;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import team.android.projects.com.bookit.logging.ApplicationCodes;
import team.android.projects.com.bookit.searchengine.ISearchEngine;
import team.android.projects.com.bookit.searchengine.SearchEngine;

public class App extends MultiDexApplication {
	private String mGoogleBooksKey;
	
	public static ISearchEngine searchEngine;
	
	@Override public void onCreate() {
		super.onCreate();
		init();
	}
	
	private void init() {
		loadConfig();
		if (mGoogleBooksKey != null) {
			loadEngine();
		}
	}
	
	private void loadEngine() {
		searchEngine = new SearchEngine(this, mGoogleBooksKey);
	}
	
	private void loadConfig() {
		try {
			StringBuilder json = new StringBuilder();
			try (BufferedReader reader
						 = new BufferedReader(
					new InputStreamReader(getAssets().open("config.json")))) {
				String line;
				while ((line = reader.readLine()) != null) {
					json.append(line).append("\n");
				}
			}
			JSONObject object = new JSONObject(json.toString());
			String googleBooksKey = object.getString("google-books-api");
			if (googleBooksKey.equals("default")) {
				Log.e(ApplicationCodes.Error.name(), "Set the api key for the Google Books API");
			} else {
				mGoogleBooksKey = googleBooksKey;
			}
		} catch (IOException | JSONException e) {
			Log.e(ApplicationCodes.Error.name(), "Unable to read config.json");
			e.printStackTrace();
		}
	}
}
