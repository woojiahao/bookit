package team.android.projects.com.bookit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

// todo: replace the processing method with an actual processing method
// todo: make the book object parcelable so as to be able to pass it into the bundle as a key-value pair
// todo: pass the book object in the bundle instead of just the title
public class ScannerLauncher extends AppCompatActivity {
	private final int REQUEST_IMAGE_CAPTURE = 2033;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanner_launcher);
		
		init();
	}
	
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE) {
			switch (resultCode) {
				case RESULT_OK:
					Bundle extras = data.getExtras();
					if (extras != null) {
						processImage((Bitmap) extras.get("data"));
					} else {
						Toast.makeText(this, "Unable to load a preview of the image!", Toast.LENGTH_SHORT).show();
					}
					break;
				case RESULT_CANCELED:
					finish();
					break;
			}
		}
	}
	
	private void init() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
		}
	}
	
	/**
	 * Process the image and returns an appropriate result back to the calling activity
	 *
	 * @param target Bitmap to check
	 */
	private void processImage(Bitmap target) {
		Intent i = new Intent();
		i.putExtra("hasMatch", false); // placeholder of true
		i.putExtra("extractedText", "Hello world"); // placeholder of Hello world
		setResult(Activity.RESULT_OK, i);
		finish();
	}
}
