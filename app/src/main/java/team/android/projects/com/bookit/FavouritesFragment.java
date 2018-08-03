package team.android.projects.com.bookit;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import team.android.projects.com.bookit.database.UsersList;
import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.ui.adapters.BookRowAdapter;
import team.android.projects.com.bookit.ui.custom.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.util.UIUtils.find;

// todo: generify the searchFor
// todo: add dynamic loading of the favourites instead of hardcoding it
public class FavouritesFragment extends Fragment {
	private View mView;
	private ClearableEditText mFavouritesSearch;
	private RecyclerView mFavouritesList;
	private BookRowAdapter mAdapter;
	
	private List<Book> mDisplayedBooks;
	private List<Book> mBooks;
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_favourites, container, false);
		
		init();
		connectListeners();
		
		return mView;
	}
	
	private void init() {
		loadBooks();
		mFavouritesSearch = find(mView, R.id.favouritesSearch);
		
		mFavouritesList = find(mView, R.id.favouritesList);
		mAdapter = new BookRowAdapter(getContext(), mDisplayedBooks);
		mFavouritesList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
		DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		mFavouritesList.addItemDecoration(divider);
		mFavouritesList.setAdapter(mAdapter);
	}
	
	private void connectListeners() {
		mFavouritesSearch.setOnTypingListener((sequence, start, before, count) ->
				searchFor(sequence.toString()));
	}
	
	private void searchFor(String s) {
		List<Book> temp = new ArrayList<>();
		if (s.trim().equals("")) {
			if (!mDisplayedBooks.containsAll(mBooks)) {
				temp = mBooks;
			}
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				temp = mBooks.stream()
						.filter(book -> book.getTitle().toLowerCase().startsWith(s.toLowerCase()))
						.collect(Collectors.toList());
			} else {
				for (Book b : mBooks) {
					if (b.getTitle().toLowerCase().startsWith(s.toLowerCase())) temp.add(b);
				}
			}
		}
		mDisplayedBooks.clear();
		mDisplayedBooks.addAll(temp);
		mAdapter.notifyDataSetChanged();
	}
	
	private void loadBooks() {
		List<Book> books = new ArrayList<Book>() {{
			add(new Book.Builder()
					.setTitle("Artemis")
					.setISBN("12jdsfhsdfkj")
					.setGenres(new String[] { "Action" })
					.setAuthors(new String[] { "John Doe" })
					.setPrices(new HashMap<String, Double>() {{
						put("Amazon", 13.99);
					}})
					.setRating(5)
					.setThumbnail(R.drawable.artemis)
					.build());
			add(new Book.Builder()
					.setTitle("Artemis")
					.setISBN("dfdsjghdsf")
					.setGenres(new String[] { "Action" })
					.setAuthors(new String[] { "John Doe", "Mary Anne" })
					.setPrices(new HashMap<String, Double>() {{
						put("Amazon", 13.99);
					}})
					.setRating(5)
					.setThumbnail(R.drawable.artemis)
					.build());
			add(new Book.Builder()
					.setTitle("Into the waters")
					.setISBN("lfdskfhksdjfh")
					.setGenres(new String[] { "Action", "Adventure" })
					.setAuthors(new String[] { "John Doe" })
					.setPrices(new HashMap<String, Double>() {{
						put("Amazon", 13.99);
					}})
					.setRating(5)
					.setThumbnail(R.drawable.into_the_water)
					.build());
			add(new Book.Builder()
					.setTitle("Before we were yours")
					.setISBN("skufsidhfsid")
					.setGenres(new String[] { "Action" })
					.setAuthors(new String[] { "John Doe" })
					.setPrices(new HashMap<String, Double>() {{
						put("Amazon", 13.99);
					}})
					.setRating(5)
					.setThumbnail(R.drawable.before_we_were_yours)
					.build());
		}};
		List<Book> temp = new ArrayList<Book>();
		if (UsersList.getCurrentUser().favourites == null || UsersList.getCurrentUser().favourites.size() == 0) {
			mDisplayedBooks = new ArrayList<Book>();
			return;
		}
		for (Book b : books) {
			if (UsersList.getCurrentUser().favourites.contains(b.getISBN())) {
				temp.add(b);
			}
		}
		mBooks = new ArrayList<Book>(temp);
		mDisplayedBooks = new ArrayList<Book>(temp);
	}
}
