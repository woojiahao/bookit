package team.android.projects.com.bookit.searchengine;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ISearchEngine {
	List<String> genreSearch(String genre) throws ExecutionException, InterruptedException;
	void bookSearch(String search);
}
