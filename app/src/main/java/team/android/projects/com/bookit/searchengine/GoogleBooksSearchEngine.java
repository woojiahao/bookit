package team.android.projects.com.bookit.searchengine;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.BuildConfig;
import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.dataclasses.Store;

// todo: add ratelimiting toast to prevent api aboos
public class GoogleBooksSearchEngine implements ISearchEngine {
	private static Books mBooks;
	
	public GoogleBooksSearchEngine() {
		init();
	}
	
	@Override public List<Book> groupSearch(SearchType searchType, String group, long querySize)
			throws ExecutionException, InterruptedException {
		if (mBooks == null) return null;
		
		String prefix = "";
		switch (searchType) {
			case Genre:
				prefix = "subject";
				break;
			case Title:
				prefix = "title";
				break;
		}
		
		String query = getQuery(prefix, group);
		
		return new GroupSearchTask().execute(query, String.valueOf(querySize)).get();
	}
	
	@Override public List<Book> bookSearch(SearchType searchType, String search)
			throws ExecutionException, InterruptedException {
		if (mBooks == null) return null;
		
		String prefix = "";
		switch (searchType) {
			case Title:
				prefix = "title";
				break;
		}
		
		String query = getQuery(prefix, search);
		
		return new GroupSearchTask().execute(query, "1").get();
	}
	
	@Override public List<Book> batchSearch(SearchType searchType, String[] searchTerms)
			throws ExecutionException, InterruptedException {
		if (mBooks == null) return null;
		
		String prefix = "";
		switch (searchType) {
			case Title:
				prefix = "title";
				break;
			case ISBN:
				prefix = "isbn";
				break;
		}
		
		String finalPrefix = prefix;
		List<String> properties = new ArrayList<String>() {{
			add(finalPrefix);
			addAll(Arrays.asList(searchTerms));
		}};
		return new BatchSearchTask().execute(properties.toArray(new String[properties.size()])).get();
	}
	
	private void init() {
		JsonFactory factory = AndroidJsonFactory.getDefaultInstance();
		mBooks = new Books
				.Builder(AndroidHttp.newCompatibleTransport(), factory, null)
				.setApplicationName(BuildConfig.APPLICATION_ID)
				.build();
	}
	
	private static String getQuery(String prefix, String suffix) {
		return String.format("%s:%s", prefix, suffix);
	}
	
	// todo: add null checking for all aspects of the book
	private static Book extractBookInfo(Volume v) {
		Volume.VolumeInfo info = v.getVolumeInfo();
		
		double ratings = info.getAverageRating() == null ? 0.0 : info.getAverageRating();
		
		String title = info.getTitle() == null ? "N/A" : info.getTitle();
		
		String thumbnail = info.getImageLinks() == null ? "https://cdn.pixabay.com/photo/2018/01/17/18/43/book-3088777_960_720.png" : info.getImageLinks().getThumbnail();
		
		List<String> auths = info.getAuthors();
		String[] authors;
		if (auths != null) {
			authors = info.getAuthors().toArray(new String[info.getAuthors().size()]);
		} else {
			authors = new String[] {
					"No authors"
			};
		}
		
		List<String> categories = info.getCategories();
		String[] genres;
		if (categories != null) {
			genres = info.getCategories().toArray(new String[info.getCategories().size()]);
		} else {
			genres = new String[] {
					"No genres"
			};
		}
		String summary = info.getDescription() == null ? "N/A" : info.getDescription();
		
		List<Store> prices = new ArrayList<Store>() {{
			Double retailPrice;
			if (v.getSaleInfo().getRetailPrice() != null) {
				retailPrice = v.getSaleInfo().getRetailPrice().getAmount();
				if (retailPrice != null) {
					if (retailPrice > 0) {
						add(new Store("Google Books", info.getPreviewLink(), retailPrice));
					}
				}
			}
		}};
		
		HashMap<String, String> isbn = new HashMap<String, String>();
		if (info.getIndustryIdentifiers() != null) {
			for (Volume.VolumeInfo.IndustryIdentifiers i : info.getIndustryIdentifiers()) {
				isbn.put(i.getType(), i.getIdentifier());
			}
		} else {
			isbn.put("N/A", "N/A");
		}
		
		Book b = new Book.Builder()
				.setRating(ratings)
				.setTitle(title)
				.setThumbnail(thumbnail)
				.setAuthors(authors)
				.setSummary(summary)
				.setGenres(genres)
				.setISBN(isbn)
				.setPrices(prices)
				.build();
		return b;
	}
	
	private static class BatchSearchTask extends AsyncTask<String, Void, List<Book>> {
		@Override protected List<Book> doInBackground(String... properties) {
			String prefix = properties[0];
			String[] toSearch = Arrays.copyOfRange(properties, 1, properties.length);
			List<Book> books = new ArrayList<Book>();
			try {
				for (String title : toSearch) {
					Volumes volumes = mBooks
							.volumes()
							.list(getQuery(prefix, title))
							.setMaxResults(1L)
							.execute();
					Volume v = volumes.getItems().get(0);
					books.add(extractBookInfo(v));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return books;
		}
	}
	
	private static class GroupSearchTask extends AsyncTask<String, Void, List<Book>> {
		@Override protected List<Book> doInBackground(String... strings) {
			String query = strings[0];
			long querySize = Long.parseLong(strings[1]);
			List<Book> books = new ArrayList<Book>();
			try {
				Volumes volumes = mBooks.volumes().list(query).setMaxResults(querySize).execute();
				if (volumes == null || volumes.getTotalItems() == 0) return null;
				for (Volume v : volumes.getItems()) books.add(extractBookInfo(v));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return books;
		}
	}
}
