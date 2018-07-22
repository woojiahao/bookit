package team.android.projects.com.booktit;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.android.projects.com.booktit.utils.UIUtils;

// todo: refactor the discover page code!
public class Discover extends AppCompatActivity{

    private ArrayList<BookCategory> mBookCategories;
    private Genre mGenre = new Genre("Action");
    private BottomNavigationView mBottomBar;
    
    private List<Book> mBooks = Arrays.asList(
			new Book("Artemis", 5.0, 13.99, mGenre, R.drawable.artemis),
			new Book("Before We Were Yours", 4.0, 25.99, mGenre, R.drawable.before_we_were_yours),
			new Book("Into The Water", 4.3, 13.00, mGenre, R.drawable.into_the_water),
			new Book("Little Fires Everywhere", 4.7, 12.10, mGenre, R.drawable.little_fires_everywhere),
			new Book("Talking As Fast As I Can", 3.9, 16.50, mGenre, R.drawable.talking_as_fast_as_i_can)
	);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        init();
    }

    private void init(){
        mBookCategories = new ArrayList<>();
        mBookCategories.add(new BookCategory("Recommended for you", mBooks));
        mBookCategories.add(new BookCategory("Best-sellers", mBooks));
        mBookCategories.add(new BookCategory("New Releases", mBooks));

	
        UIUtils.setBottomBarSelection(this, R.id.navigationDiscover);
        
        DiscoverFinalAdapter adapter = new DiscoverFinalAdapter(this, mBookCategories);
        ListView listView = findViewById(R.id.discoverArea);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setAdapter(adapter);
    }
}
