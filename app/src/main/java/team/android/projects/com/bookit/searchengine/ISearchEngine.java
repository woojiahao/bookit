package team.android.projects.com.bookit.searchengine;

import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.dataclasses.Book;

public interface ISearchEngine {
	List<Book> groupSearch(SearchType searchType, String group, long querySize)
			throws ExecutionException, InterruptedException;
	
	Book bookSearch(SearchType searchType, String search)
			throws ExecutionException, InterruptedException;
	
	List<Book> batchTitleSearch(String[] titles)
			throws ExecutionException, InterruptedException;
}
