package team.android.projects.com.bookit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import team.android.projects.com.bookit.utils.backstack.FragmentID;
import team.android.projects.com.bookit.utils.ui.camera.CameraStates;
import team.android.projects.com.bookit.utils.ui.helper.BottomNavigationHelper;

import static team.android.projects.com.bookit.utils.backstack.FragmentID.Discover;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;
import static team.android.projects.com.bookit.utils.ui.camera.CameraStates.Cancelled;
import static team.android.projects.com.bookit.utils.ui.camera.CameraStates.Taken;
import static team.android.projects.com.bookit.utils.ui.camera.CameraStates.Taking;

// todo: implement a proper backstack
// todo: move the camera shit into it's own activity
// todo: customize the camera
// todo: put back the fragment addToBackStack method
// todo: change the camera operations into an enum
public class Container
		extends AppCompatActivity
		implements BottomNavigationView.OnNavigationItemSelectedListener {
	private final int CAMERA_PERM_REQUEST = 3033;
	private final int LAUNCH_CAMERA = 2034;
	
	private BottomNavigationView mBottomBar;
	private Bundle mCameraActivityBundle;
	
	private CameraStates mCameraState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container);
		
		init();
		connectListeners();
	}
	
	@Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
		if (requestCode == LAUNCH_CAMERA) {
			switch (resultCode) {
				case RESULT_OK:
					Bundle extras = data.getExtras();
					if (extras != null) {
						mCameraState = Taken;
						mCameraActivityBundle = extras;
					}
					break;
				case RESULT_CANCELED:
					mCameraState = Cancelled;
					break;
			}
		}
	}
	
	@Override protected void onResume() {
		super.onResume();
		if (mCameraState != null) {
			switch (mCameraState) {
				case Taken:
					Fragment f;
					Bundle b = new Bundle();
					boolean hasMatch = mCameraActivityBundle.getBoolean("hasMatch");
					String extractedText = mCameraActivityBundle.getString("extractedText");
					
					if (hasMatch) {
						// if there is a successful scan, just print out the book details
//						f = new ScannerFragment();
//						b.putBundle("cameraActivity", mCameraActivityBundle);
					} else {
						f = new StatusFragment();
						b.putBoolean("status", false);
						b.putString("message", "Cannot find " + extractedText + ".");
						f.setArguments(b);
						getSupportFragmentManager()
								.beginTransaction()
								.replace(R.id.contentArea, f, FragmentID.Scanner.name())
								.commit();
					}
					break;
				case Cancelled:
					setBottomBarSelectedItem(Discover);
					mCameraState = null;
					break;
			}
		}
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
		mCameraState = Taking;
		startActivityForResult(new Intent(this, ScannerLauncher.class), LAUNCH_CAMERA);
	}
}
