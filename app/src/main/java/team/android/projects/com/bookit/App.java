package team.android.projects.com.bookit;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import team.android.projects.com.bookit.logging.ApplicationCodes;
import team.android.projects.com.bookit.searchengine.Engines;
import team.android.projects.com.bookit.searchengine.GoodReadsSearchEngine;
import team.android.projects.com.bookit.searchengine.GoogleBooksSearchEngine;
import team.android.projects.com.bookit.searchengine.ISearchEngine;
import team.android.projects.com.bookit.searchengine.NewYorkTimesSearchEngine;

import static team.android.projects.com.bookit.logging.ApplicationCodes.Error;
import static team.android.projects.com.bookit.searchengine.Engines.GoodReads;
import static team.android.projects.com.bookit.searchengine.Engines.GoogleBooks;
import static team.android.projects.com.bookit.searchengine.Engines.NewYorkTimes;
import static team.android.projects.com.bookit.util.Utils.loadJSON;

public class App extends MultiDexApplication {
	private Map<String, String> mKeys;
	public static Map<String, ISearchEngine> searchEngines;
	
	@Override public void onCreate() {
		super.onCreate();
		init();
	}
	
	private void init() {
		loadConfig();
		loadEngine();
	}
	
	private void loadEngine() {
		searchEngines = new HashMap<String, ISearchEngine>() {{
			put(GoogleBooks.mapKey, new GoogleBooksSearchEngine());
			put(NewYorkTimes.mapKey, new NewYorkTimesSearchEngine(mKeys.get(NewYorkTimes.mapKey)));
			put(GoodReads.mapKey, new GoodReadsSearchEngine(mKeys.get(GoodReads.mapKey)));
		}};
	}
	
	private void loadConfig() {
		mKeys = new HashMap<String, String>();
		
		try {
			JSONObject root = new JSONObject(loadJSON(getAssets(), "config.json"));
			
			for (Engines engineType : Engines.values()) {
				String key = root.getString(engineType.jsonKey);
				if (key.equals("default")) {
					Log.e(Error.name(), String.format("Set the api key for the %s API", engineType.mapKey));
				} else {
					mKeys.put(engineType.mapKey, key);
				}
			}
		} catch (JSONException e) {
			Log.e(ApplicationCodes.Error.name(), "Error whilst working on config.json");
			e.printStackTrace();
		}
	}
	
	
}
