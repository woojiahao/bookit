package team.android.projects.com.bookit.searchengine;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.BuildConfig;
import team.android.projects.com.bookit.dataclasses.Book;

public class SearchEngine implements ISearchEngine {
	private static Books mBooks;
	private Context mContext;
	private static String mKey;
	
	public SearchEngine(Context c, String key) {
		mContext = c;
		mKey = key;
		init();
	}
	
	@Override
	public List<Book> genreSearch(String genre) throws ExecutionException, InterruptedException {
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
	
	private static class SearchTask extends AsyncTask<String, Void, List<Book>> {
		@Override protected List<Book> doInBackground(String... strings) {
			String query = strings[0];
			List<Book> titles = new ArrayList<Book>();
			int isbn = 1230;
			try {
				Books.Volumes.List result = mBooks.volumes().list(query).setMaxResults(5L);
				Volumes volumes = result.execute();
				for (Volume v : volumes.getItems()) {
					Volume.VolumeInfo info = v.getVolumeInfo();
					Book.Builder builder = new Book.Builder();
					Log.d("SEARCHING", String.valueOf(info.getAverageRating()));
					
					Book b = builder
							.setRating(info.getAverageRating() == null ? 4.3 : info.getAverageRating())
							.setTitle(info.getTitle())
							.setThumbnail(info.getImageLinks().getThumbnail())
							.setAuthors(info.getAuthors().toArray(new String[info.getAuthors().size()]))
							.setSummary(info.getDescription())
							.setGenres(info.getCategories().toArray(new String[info.getCategories().size()]))
							.setISBN(String.valueOf(isbn++))
							.setPrices(new HashMap<String, Double>() {{ put("Amazon", 39.90); }})
							.build();
					titles.add(b);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return titles;
		}
	}
}
