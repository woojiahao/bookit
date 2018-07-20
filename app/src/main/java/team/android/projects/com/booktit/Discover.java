package team.android.projects.com.booktit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class Discover extends AppCompatActivity{

    private ArrayList<BookCategory> bookCategories;
    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        init();
    }

    private void init(){
        Genre genre = new Genre("Action");

        books = new ArrayList<>();
        books.add(new Book("Artemis", 5.0, 13.99, genre, R.drawable.artemis));
        books.add(new Book("Before We Were Yours", 4.0, 25.99, genre, R.drawable.before_we_were_yours));
        books.add(new Book("Into The Water", 4.3, 13.00, genre, R.drawable.into_the_water));
        books.add(new Book("Little Fires Everywhere", 4.7, 12.10, genre, R.drawable.little_fires_everywhere));
        books.add(new Book("Talking As Fast As I Can", 3.9, 16.50, genre, R.drawable.talking_as_fast_as_i_can));

        bookCategories = new ArrayList<>();
        bookCategories.add(new BookCategory("Recommended for you", books));
        bookCategories.add(new BookCategory("Best-sellers", books));
        bookCategories.add(new BookCategory("New Releases", books));


        DiscoverFinalAdapter adapter = new DiscoverFinalAdapter(this,bookCategories);
        ListView listView = (ListView) findViewById(R.id.discoverArea);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setAdapter(adapter);
    }
}
