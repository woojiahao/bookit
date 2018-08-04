package team.android.projects.com.bookit.searchengine;

import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.dataclasses.Book;

public interface ISearchEngine {
	List<Book> genreSearch(String genre, long querySize) throws ExecutionException, InterruptedException;
	void bookSearch(String search);
}
