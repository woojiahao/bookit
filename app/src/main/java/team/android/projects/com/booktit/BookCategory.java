package team.android.projects.com.booktit;

import java.util.ArrayList;
import java.util.List;

public class BookCategory {
    private String title;
    private List<Book> books;

    public BookCategory(String title, List<Book> books) {
        this.title = title;
        this.books = books;
    }

    public String getTitle() {
        return title;
    }

    public List<Book> getBooks() {
        return books;
    }
}
