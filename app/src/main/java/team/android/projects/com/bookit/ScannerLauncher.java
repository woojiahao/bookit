package team.android.projects.com.bookit;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.logging.ApplicationCodes;
import team.android.projects.com.bookit.ocr.TessOCR;
import team.android.projects.com.bookit.searchengine.Engines;
import team.android.projects.com.bookit.searchengine.SearchType;

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
		String result = null;
		try (TessOCR ocr = new TessOCR.Builder().setManager(getAssets()).build()) {
			if (ocr != null) result = ocr.scanImage(BitmapFactory.decodeFile(mImageFilePath));
		} catch (Exception e) {
			Log.e(ApplicationCodes.Error.name(), e.getMessage());
		}
		
		if (result != null) {
			Book b = null;
			try {
				List<Book> temp = App
						.searchEngines
						.get(Engines.GoogleBooks.mapKey)
						.groupSearch(SearchType.Title, result, 1L);
				if (temp != null) b = temp.get(0);
			} catch (ExecutionException | InterruptedException e) {
				Log.e(ApplicationCodes.Error.name(), "Failed to load the book with title: " + result);
				e.printStackTrace();
			}
			Intent i = new Intent();
			i.putExtra("hasMatch", b != null);
			i.putExtra("extractedText", result);
			if (b != null) {
				i.putExtra("book", b);
			}
			setResult(RESULT_OK, i);
			finish();
		}
	}
}
