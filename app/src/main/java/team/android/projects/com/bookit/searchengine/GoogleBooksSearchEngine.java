package team.android.projects.com.bookit.searchengine;

import android.content.Context;
import android.os.AsyncTask;

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

// todo: remove the loading of the config file
public class GoogleBooksSearchEngine implements ISearchEngine {
	private static Books mBooks;
	private Context mContext;
	private static String mKey;
	
	public GoogleBooksSearchEngine(Context c, String key) {
		mContext = c;
		mKey = key;
		init();
	}
	
	@Override
	public List<Book> genreSearch(String genre, long querySize)
			throws ExecutionException, InterruptedException {
		if (mBooks == null) return null;
		String query = "subject:" + genre;
		
		return new SearchTask().execute(query, String.valueOf(querySize)).get();
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
			long querySize = Long.parseLong(strings[1]);
			List<Book> books = new ArrayList<Book>();
			try {
				Books.Volumes.List result = mBooks.volumes().list(query).setMaxResults(querySize);
				Volumes volumes = result.execute();
				for (Volume v : volumes.getItems()) {
					Volume.VolumeInfo info = v.getVolumeInfo();
					
					double ratings = info.getAverageRating() == null ? 0.0 : info.getAverageRating();
					
					String title = info.getTitle();
					String thumbnail = info.getImageLinks() == null ? "https://cdn.pixabay.com/photo/2018/01/17/18/43/book-3088777_960_720.png" : info.getImageLinks().getThumbnail() ;
					
					String[] authors = info.getAuthors().toArray(new String[info.getAuthors().size()]);
					String[] genres = info.getCategories().toArray(new String[info.getCategories().size()]);
					String summary = info.getDescription();
					
					HashMap<String, Double> prices = new HashMap<String, Double>() {{
						if (v.getSaleInfo().getRetailPrice() == null) {
							put("Google Books", 0.00);
						} else {
							Double retailPrice = v.getSaleInfo().getRetailPrice().getAmount();
							put("Google Books", retailPrice == null ? 0.00 : retailPrice);
						}
					}};
					HashMap<String, String> isbn = new HashMap<String, String>();
					for (Volume.VolumeInfo.IndustryIdentifiers i : info.getIndustryIdentifiers()) {
						isbn.put(i.getType(), i.getIdentifier());
					}
					
					books.add(new Book.Builder()
							.setRating(ratings)
							.setTitle(title)
							.setThumbnail(thumbnail)
							.setAuthors(authors)
							.setSummary(summary)
							.setGenres(genres)
							.setISBN(isbn)
							.setPrices(prices)
							.build());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return books;
		}
	}
}
