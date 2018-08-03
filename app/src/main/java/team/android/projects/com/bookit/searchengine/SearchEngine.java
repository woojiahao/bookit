package team.android.projects.com.bookit.searchengine;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.BuildConfig;

import static team.android.projects.com.bookit.logging.Logging.shortToast;

public class SearchEngine implements ISearchEngine {
	private static Books mBooks;
	private Context mContext;
	private static String mKey;
	
	public SearchEngine(Context c, String key) {
		mContext = c;
		mKey = key;
		init();
	}
	
	@Override public List<String> genreSearch(String genre) throws ExecutionException, InterruptedException {
		if (mBooks == null) return null;
		String query = "subject:" + genre;
		
		return new SearchTask().execute(query).get();
	}
	
	@Override public void bookSearch(String search) {
	
	}
	
	private void init() {
		JsonFactory factory = AndroidJsonFactory.getDefaultInstance();
		mBooks = new Books
				.Builder(AndroidHttp.newCompatibleTransport(), factory, null)
				.setApplicationName(BuildConfig.APPLICATION_ID)
				.build();
	}
	
	private static class SearchTask extends AsyncTask<String, Void, List<String>> {
		@Override protected List<String> doInBackground(String... strings) {
			String query = strings[0];
			List<String> titles = new ArrayList<String>();
			try {
				Books.Volumes.List result = mBooks.volumes().list(query);
				result.setMaxResults(5L);
				Volumes volumes;
				volumes = result.execute();
				for (Volume v : volumes.getItems()) {
					Volume.VolumeInfo info = v.getVolumeInfo();
					titles.add(info.getTitle());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return titles;
		}
	}
}
