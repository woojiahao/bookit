package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

import team.android.projects.com.bookit.dataclasses.Store;
import team.android.projects.com.bookit.dataclasses.StorePrice;

public class Details extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Store> mStores = Arrays.asList(
            new Store("Amazon", R.drawable.amazon_icon),
            new Store("Ebay", R.drawable.ebay_icon),
            new Store("GoodReads", R.drawable.goodreads_icon)
    );

    //TODO: We can "store" the StorePrice in the Book Class
    //Temp List, create List in the Book class in the future
    private List<StorePrice> mPrices = Arrays.asList(
            new StorePrice(getStore("Amazon"), 19.10),
            new StorePrice(getStore("Ebay"), 25.20),
            new StorePrice(getStore("GoodReads"), 21.99)
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        init();

        mRecyclerView = (RecyclerView) findViewById(R.id.detailsRecyclerView);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mAdapter = new DetailsAdapter(mPrices);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

    private void init() {
        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.detailsToolbar);
        setSupportActionBar(mToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
    }

    //Wanted to override ArrayList methods, but too risky so I create a method
    //TODO: Temporary method, either remove or improve
    private Store getStore(String name) {
        for (Store store : mStores) {
            if (store.getName().equalsIgnoreCase(name)) {
                return store;
            }
        }
        throw new Error();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //A little hack from StackOverFlow
            case android.R.id.home:
                finish();
                return true;
            case R.id.details_action_favorite:
                //Todo: Add Favorites Logic
                Toast.makeText(this, "Favorites Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
