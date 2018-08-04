package team.android.projects.com.bookit.searchengine;

import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.dataclasses.Book;

public interface ISearchEngine {
	List<Book> groupSearch(SearchType searchType, String group, long querySize)
			throws ExecutionException, InterruptedException;
	
	List<Book> bookSearch(SearchType searchType, String search)
			throws ExecutionException, InterruptedException;
	
	List<Book> batchSearch(SearchType searchType, String[] searchTerms)
			throws ExecutionException, InterruptedException;
}
