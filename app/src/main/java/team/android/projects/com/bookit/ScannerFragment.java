package team.android.projects.com.bookit;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class ScannerFragment extends Fragment {
	private Bitmap mCameraPreviewImage;
	private View mView;
	private ImageView mCameraPreview;
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_scanner, container, false);
		
		Bundle extras = getArguments();
		
		if (extras != null) {
			Bundle imageExtras = extras.getBundle("image");
			if (imageExtras != null) {
				mCameraPreviewImage = (Bitmap) imageExtras.get("data");
			}
		}
		
		init();
		
		return mView;
	}
	
	private void init() {
		mCameraPreview = find(mView, R.id.imagePreview);
		if (mCameraPreviewImage != null) {
			mCameraPreview.setImageBitmap(mCameraPreviewImage);
		}
	}
}
