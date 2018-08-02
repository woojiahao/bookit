package team.android.projects.com.bookit;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import team.android.projects.com.bookit.utils.logging.ApplicationCodes;

// todo: replace the processing method with an actual processing method
// todo: make the book object parcelable so as to be able to pass it into the bundle as a key-value pair
// todo: pass the book object in the bundle instead of just the title
public class ScannerLauncher extends AppCompatActivity {
	private final int REQUEST_IMAGE_CAPTURE = 2033;
	private String mImageFilePath;
	private Uri mPhotoURI;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanner_launcher);
		
		init();
	}
	
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && mImageFilePath != null) {
			switch (resultCode) {
				case RESULT_OK:
					processImage();
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
			File photo = null;
			try {
				photo = createImageFile();
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(ApplicationCodes.Error.name(), "Error when creating image file");
				// todo: exit the camera space
			}
			
			if (photo != null) {
				mPhotoURI = FileProvider.getUriForFile(this,
						"com.example.android.fileprovider",
						photo);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
				startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
			}
		}
	}
	
	private File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat(
				"yyyyMMdd_HHmmss",
				Locale.getDefault())
				.format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, ".jpg", storageDir);
		
		mImageFilePath = image.getAbsolutePath();
		return image;
	}
	
	private void processImage() {
	
	}
}
