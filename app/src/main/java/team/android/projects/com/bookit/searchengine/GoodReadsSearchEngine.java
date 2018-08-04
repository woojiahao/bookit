package team.android.projects.com.bookit.searchengine;

import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.dataclasses.Book;

public class GoodReadsSearchEngine implements ISearchEngine {
	private String mKey;
	
	public GoodReadsSearchEngine(String key) {
		mKey = key;
	}

	@Override public List<Book> groupSearch(SearchType searchType, String group, long querySize)
			throws ExecutionException, InterruptedException {
		return null;
	}
	
	@Override public List<Book> bookSearch(SearchType searchType, String search)
			throws ExecutionException, InterruptedException {
		return null;
	}
	
	@Override public List<Book> batchSearch(SearchType searchType, String[] searchTerms)
			throws ExecutionException, InterruptedException {
		return null;
	}
}
