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

import java.util.ArrayList;
import java.util.List;

import team.android.projects.com.bookit.backstack.FragmentID;
import team.android.projects.com.bookit.ocr.CameraStates;
import team.android.projects.com.bookit.searchengine.SearchEngine;
import team.android.projects.com.bookit.ui.helper.BottomNavigationHelper;

import static team.android.projects.com.bookit.backstack.FragmentID.Discover;
import static team.android.projects.com.bookit.backstack.FragmentID.Favourites;
import static team.android.projects.com.bookit.backstack.FragmentID.Search;
import static team.android.projects.com.bookit.backstack.FragmentID.Setting;
import static team.android.projects.com.bookit.util.UIUtils.displayExitConfirmDialog;
import static team.android.projects.com.bookit.util.UIUtils.find;
import static team.android.projects.com.bookit.ocr.CameraStates.Cancelled;
import static team.android.projects.com.bookit.ocr.CameraStates.Taken;
import static team.android.projects.com.bookit.ocr.CameraStates.Taking;

// todo: implement a proper backstack
// todo: customize the camera
// todo: put back the fragment addToBackStack method
// todo: launch the book details for the book when successful
public class Container
		extends AppCompatActivity
		implements BottomNavigationView.OnNavigationItemSelectedListener {
	private final int PERM_REQUEST = 3033;
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
	
	@Override public void onBackPressed() {
		if (mBottomBar.getSelectedItemId() == R.id.navigationDiscover) {
			displayExitConfirmDialog(this);
		} else {
			setBottomBarSelectedItem(Discover);
		}
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
				fragmentID = Search;
				break;
			case R.id.navigationScanner:
				startScanner();
				break;
			case R.id.navigationFavourites:
				toInflate = new FavouritesFragment();
				fragmentID = Favourites;
				break;
			case R.id.navigationSettings:
				toInflate = new SettingsFragment();
				fragmentID = Setting;
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
			case PERM_REQUEST:
				boolean hasCameraPerms = ContextCompat
						.checkSelfPermission(this,
								Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
				boolean hasExternalStoragePerms = ContextCompat
						.checkSelfPermission(this,
								Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					if (hasCameraPerms && hasExternalStoragePerms) launchCamera();
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
		boolean hasCameraPerms = ContextCompat
				.checkSelfPermission(this,
						Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
		boolean hasExternalStoragePerms = ContextCompat
				.checkSelfPermission(this,
						Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
		if (hasCameraPerms && hasExternalStoragePerms) {
			launchCamera();
			return;
		}
		
		List<String> perms = new ArrayList<String>() {{
			if (!hasCameraPerms) add(Manifest.permission.CAMERA);
			if (!hasExternalStoragePerms) add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
		}};
		ActivityCompat.requestPermissions(this, perms.toArray(new String[perms.size()]), PERM_REQUEST);
	}
	
	private void launchCamera() {
		mCameraState = Taking;
		startActivityForResult(new Intent(this, ScannerLauncher.class), LAUNCH_CAMERA);
	}
}
