package team.android.projects.com.bookit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import team.android.projects.com.bookit.utils.ui.helper.BottomNavigationHelper;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

// todo: fix the backstack issue
public class Container
		extends AppCompatActivity
		implements BottomNavigationView.OnNavigationItemSelectedListener {
	private BottomNavigationView mBottomBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container);
		
		init();
		connectListeners();
	}
	
	private void init() {
		mBottomBar = find(this, R.id.navigationDrawer);
		BottomNavigationHelper.removeShiftMode(mBottomBar);
		mBottomBar.setSelectedItemId(R.id.navigationDiscover);
		loadInitialFragment();
	}
	
	private void loadInitialFragment() {
		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.contentArea, new DiscoverFragment(), "Fragment")
				.commit();
	}
	
	private void connectListeners() {
		mBottomBar.setOnNavigationItemSelectedListener(this);
	}
	
	@Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		FragmentManager manager = getSupportFragmentManager();
		Fragment toInflate = null;
		FragmentID fragmentID = null;
		
		int itemSelected = item.getItemId();
		
		switch (itemSelected) {
			case R.id.navigationDiscover:
				toInflate = new DiscoverFragment();
				fragmentID = FragmentID.Discover;
				break;
			case R.id.navigationSearch:
				toInflate = new SearchFragment();
				fragmentID = FragmentID.Search;
				break;
			case R.id.navigationScanner:
				break;
			case R.id.navigationFavourites:
				break;
			case R.id.navigationSettings:
				break;
		}
		
		if (toInflate != null && fragmentID != null) {
			Log.d("Container", fragmentID.name());
			manager.beginTransaction()
					.addToBackStack("Fragment")
					.replace(R.id.contentArea, toInflate, fragmentID.name())
					.commit();
		}
		
		return true;
	}
}
