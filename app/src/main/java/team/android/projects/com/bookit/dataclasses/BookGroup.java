package team.android.projects.com.bookit.dataclasses;

import java.util.List;

public class BookGroup {
    private String mTitle;
    private List<Book> mBooks;

    public BookGroup(String title, List<Book> books) {
        mTitle = title;
        mBooks = books;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<Book> getBooks() {
        return mBooks;
    }
}
