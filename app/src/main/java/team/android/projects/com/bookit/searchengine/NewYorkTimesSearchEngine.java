package team.android.projects.com.bookit.searchengine;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.App;
import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.logging.ApplicationCodes;

// todo: best sellers might be outdated
public class NewYorkTimesSearchEngine implements ISearchEngine {
	private static String mKey;
	
	public NewYorkTimesSearchEngine(String apiKey) {
		mKey = apiKey;
	}
	
	@Override public List<Book> groupSearch(SearchType searchType, String group, long querySize)
			throws ExecutionException, InterruptedException {
		String endpoint = "";
		
		switch (searchType) {
			case BestSellers:
				endpoint = "/best-sellers/history.json";
				break;
		}
		
		List<String> bestSellingTitles = new BestSellerSearchTask().execute(endpoint).get();
		return App
				.searchEngines
				.get(Engines.GoogleBooks.mapKey)
				.batchSearch(
						SearchType.Title,
						bestSellingTitles.toArray(new String[bestSellingTitles.size()]));
	}
	
	@Override public List<Book> bookSearch(SearchType searchType, String search) {
		return null;
	}
	
	@Override public List<Book> batchSearch(SearchType searchType, String[] searchTerms)
			throws ExecutionException, InterruptedException {
		return null;
	}
	
	private static class BestSellerSearchTask extends AsyncTask<String, Void, List<String>> {
		@Override protected List<String> doInBackground(String... strings) {
			List<String> titles = new ArrayList<String>();
			
			String endpoint = strings[0];
			String base = "https://api.nytimes.com/svc/books/v3/lists";
			String query = String.format("%s%s?api-key=%s", base, endpoint, mKey);
			
			StringBuilder returnedJSON = new StringBuilder();
			try {
				URL url = new URL(query);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
					String line;
					while ((line = reader.readLine()) != null)
						returnedJSON.append(line).append("\n");
				}
				
				conn.disconnect();
			} catch (java.io.IOException e) {
				Log.e(ApplicationCodes.Error.name(), "Unable to read from API Endpoint: " + query);
				e.printStackTrace();
			}
			
			if (returnedJSON.toString().trim().equals("")) return null;
			
			try {
				JSONObject root = new JSONObject(returnedJSON.toString());
				JSONArray results = root.getJSONArray("results");
				for (int i = 0; i < results.length(); i++) {
					JSONObject book = results.getJSONObject(i);
					
					String title = book.getString("title");
					titles.add(title.toLowerCase());
				}
			} catch (JSONException e) {
				Log.e(ApplicationCodes.Error.name(), "Unable to process JSON");
				e.printStackTrace();
			}
			
			return titles;
		}
	}
}
