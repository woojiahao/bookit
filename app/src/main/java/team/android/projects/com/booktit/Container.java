package team.android.projects.com.booktit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class Container extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
	private FrameLayout mContentArea;
	private BottomNavigationView mBottomBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container);
		
		init();
		connectListeners();
	}
	
	private void init() {
		mContentArea = findViewById(R.id.contentArea);
		mBottomBar = findViewById(R.id.navigationDrawer);
	}
	
	private void connectListeners() {
		mBottomBar.setOnNavigationItemSelectedListener(this);
	}
	
	@Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		FragmentManager manager = getSupportFragmentManager();
		Fragment toInflate = null;
		
		int itemSelected = item.getItemId();
		
		switch (itemSelected) {
			case R.id.navigationDiscover:
				break;
			case R.id.navigationSearch:
				toInflate = new SearchFragment();
				break;
			case R.id.navigationScanner:
				break;
			case R.id.navigationFavourites:
				break;
			case R.id.navigationSettings:
				break;
		}
		
		if (toInflate != null) {
			manager.beginTransaction().add(R.id.contentArea, toInflate, "Fragment").commit();
		}
		
		return true;
	}
}
