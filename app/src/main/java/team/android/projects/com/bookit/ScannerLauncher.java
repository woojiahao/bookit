package team.android.projects.com.bookit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

// todo: replace the processing method with an actual processing method
public class ScannerLauncher extends AppCompatActivity {
	// launched when perms go through
	private final int REQUEST_IMAGE_CAPTURE = 2033;
	private Bundle mCameraImageBundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanner_launcher);
		
		init();
	}
	
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// getting the image from the camera
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				mCameraImageBundle = extras;
				processImage((Bitmap) extras.get("data"));
			} else {
				Toast.makeText(this, "Unable to load a preview of the image!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void init() {
		Toast.makeText(this, "Launching camera", Toast.LENGTH_SHORT).show();
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
		}
	}
	
	/**
	 * Process the image and returns an appropriate result back to the calling activity
	 * @param target Bitmap to check
	 */
	private void processImage(Bitmap target) {
		Intent i = new Intent();
		i.putExtra("hasMatch", true); // placeholder of true
		i.putExtra("extractedText", "Hello world"); // placeholder of Hello world
		setResult(Activity.RESULT_OK, i);
		finish();
	}
}