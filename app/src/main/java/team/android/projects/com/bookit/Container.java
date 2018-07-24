package team.android.projects.com.bookit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import team.android.projects.com.bookit.utils.ui.helper.BottomNavigationHelper;

import static team.android.projects.com.bookit.FragmentID.Discover;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

// todo: implement a proper backstack
// todo: move the camera shit into it's own activity
// todo: customize the camera
// todo: put back the fragment addToBackStack method
public class Container
		extends AppCompatActivity
		implements BottomNavigationView.OnNavigationItemSelectedListener {
	private final int CAMERA_PERM_REQUEST = 3033;
	private final int LAUNCH_CAMERA = 2034;
	
	private BottomNavigationView mBottomBar;
	private boolean mCameraActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container);
		
		init();
		connectListeners();
	}
	
	@Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		mCameraActivity = false;
		FragmentManager manager = getSupportFragmentManager();
		Fragment toInflate = null;
		FragmentID fragmentID = null;
		
		switch (item.getItemId()) {
			case R.id.navigationDiscover:
				toInflate = new DiscoverFragment();
				fragmentID = Discover;
				break;
			case R.id.navigationSearch:
				toInflate = new SearchFragment();
				fragmentID = FragmentID.Search;
				break;
			case R.id.navigationScanner:
				startScanner();
				break;
			case R.id.navigationFavourites:
				break;
			case R.id.navigationSettings:
				break;
		}
		
		if (toInflate != null) {
			manager.beginTransaction()
					.replace(R.id.contentArea, toInflate, fragmentID.name())
					.commit();
		}
		
		return true;
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case CAMERA_PERM_REQUEST:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					launchCamera();
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Lack of permissions")
							.setMessage("Unable to use scanner feature")
							.setCancelable(false)
							.setPositiveButton("OK", (dialog, which) -> {
								setBottomBarSelectedItem(Discover);
							});
					AlertDialog dialog = builder.create();
					dialog.show();
				}
		}
	}
	
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// when the launcher finished processing the information
		// it will return a boolean that indicates if the book could be found or not
		if (requestCode == LAUNCH_CAMERA && resultCode == RESULT_OK) {
			// when the scanner launcher returns
			Bundle extras = data.getExtras();
			if (extras != null) {
				boolean processingStatus = extras.getBoolean("hasMatch");
				String extractedText = extras.getString("extractedText");
				
				Toast.makeText(this, String.format("hasMatch: %s\nextractedText: %s", processingStatus, extractedText), Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override protected void onResume() {
		super.onResume();
//		Toast.makeText(this, "Resumed", Toast.LENGTH_SHORT).show();
//		if (mCameraActivity) {
//			Fragment scanner = new ScannerFragment();
//			Bundle b = new Bundle();
//			b.putBundle("image", mCameraImageBundle);
//			scanner.setArguments(b);
//			getSupportFragmentManager()
//					.beginTransaction()
//					.replace(R.id.contentArea, scanner, FragmentID.Scanner.name())
//					.commit();
//		}
	}
	
	private void init() {
		mBottomBar = find(this, R.id.navigationDrawer);
		BottomNavigationHelper.removeShiftMode(mBottomBar);
		mBottomBar.setSelectedItemId(R.id.navigationDiscover);
		loadInitialFragment();
		
		mCameraActivity = false;
	}
	
	private void loadInitialFragment() {
		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.contentArea, new DiscoverFragment(), Discover.name())
				.commit();
	}
	
	private void connectListeners() {
		mBottomBar.setOnNavigationItemSelectedListener(this);
	}
	
	private void setBottomBarSelectedItem(FragmentID id) {
		int toSwitch = 0;
		switch (id) {
			case Discover:
				toSwitch = R.id.navigationDiscover;
				break;
			case Search:
				toSwitch = R.id.navigationSearch;
				break;
			case Scanner:
				toSwitch = R.id.navigationScanner;
				break;
			case Favourites:
				toSwitch = R.id.navigationFavourites;
				break;
			case Setting:
				toSwitch = R.id.navigationSettings;
				break;
		}
		
		mBottomBar.setSelectedItemId(toSwitch);
	}
	
	private void startScanner() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, CAMERA_PERM_REQUEST);
		} else {
			launchCamera();
		}
	}
	
	private void launchCamera() {
		Toast.makeText(this, "Launching the camera now!", Toast.LENGTH_SHORT).show();
		mCameraActivity = true;
		startActivityForResult(new Intent(this, ScannerLauncher.class), LAUNCH_CAMERA);
	}
}
