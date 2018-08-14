package team.android.projects.com.bookit.loading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.App;
import team.android.projects.com.bookit.dataclasses.Book;

import static team.android.projects.com.bookit.searchengine.Engines.NewYorkTimes;
import static team.android.projects.com.bookit.searchengine.SearchType.BestSellers;

public class PreloadedData {
	private static List<Book> mBestSellers;

	public static void loadBestSellers() throws ExecutionException, InterruptedException {
		mBestSellers = new ArrayList<Book>();

		long querySize = 1L;

		mBestSellers.addAll(
				App.searchEngines.get(NewYorkTimes.mapKey)
												 .groupSearch(BestSellers, null, querySize));

	}

	public static List<Book> getBestSellers() {
		return mBestSellers;
	}
}
