package team.android.projects.com.booktit;

import java.util.ArrayList;

public class BookCategory {
    private String title;
    private ArrayList<Book> books;

    public BookCategory(String title, ArrayList<Book> books) {
        this.title = title;
        this.books = books;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
