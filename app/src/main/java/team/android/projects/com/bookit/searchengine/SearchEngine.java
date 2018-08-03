package team.android.projects.com.bookit.searchengine;

import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;

import static team.android.projects.com.bookit.logging.Logging.shortToast;

public class SearchEngine implements ISearchEngine {
	private Books mBooks;
	private Context mContext;
	private String mKey;
	
	public SearchEngine(Context c, String key) {
		mContext = c;
		mKey = key;
		init();
	}
	
	@Override public void genreSearch(String genre) {
		if (mBooks == null) return;
		
		try {
			Books.Volumes.List result = mBooks.volumes().list("subject:" + genre);
			result.setMaxResults(1L);
			Volumes volumes = null;
			volumes = result.execute();
			for (Volume v : volumes.getItems()) {
				Volume.VolumeInfo info = v.getVolumeInfo();
				Volume.VolumeInfo.ImageLinks imageLinks = info.getImageLinks();
				shortToast(mContext, "First book: " + info.getTitle());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override public void bookSearch(String search) {
	
	}
	
	private void init() {
		JsonFactory factory = AndroidJsonFactory.getDefaultInstance();
		mBooks = new Books
				.Builder(AndroidHttp.newCompatibleTransport(), factory, null)
				.setGoogleClientRequestInitializer(
						new BooksRequestInitializer(mKey)
				)
				.build();
	}
}
