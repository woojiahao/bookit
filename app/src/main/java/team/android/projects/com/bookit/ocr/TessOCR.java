package team.android.projects.com.bookit.ocr;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import team.android.projects.com.bookit.logging.ApplicationCodes;

import static team.android.projects.com.bookit.logging.ApplicationCodes.Error;
import static team.android.projects.com.bookit.logging.ApplicationCodes.Success;

// todo: documentation
// todo: create custom exceptions
public class TessOCR implements Closeable {
	private static final String DATA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AndroidOCR/";
	
	private TessBaseAPI mTess;
	
	public static class Builder {
		private AssetManager manager;
		private TessBaseAPI tess;
		
		public TessOCR build() throws Exception {
			if (manager == null) {
				throw new Exception("Set the manager for the TessOCR before building.");
			} else {
				if (loadTrainingData()) {
					tess = new TessBaseAPI();
					tess.setDebug(true);
					tess.init(DATA_PATH, "eng");
					return new TessOCR(tess);
				} else {
					return null;
				}
			}
		}
		
		public Builder setManager(AssetManager manager) {
			this.manager = manager;
			return this;
		}
		
		private boolean loadTrainingData() {
			if (initTessdataFolder()) {
				if (!new File(DATA_PATH + "tessdata/eng.traineddata").exists()) {
					try (InputStream in = manager.open("tessdata/eng.traineddata");
						 OutputStream out = new FileOutputStream(
								 new File(DATA_PATH, "tessdata/eng.traineddata"))) {
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) != -1) out.write(buf, 0, len);
						Log.v(Success.name(), "Copied training data");
						return true;
					} catch (IOException e) {
						e.printStackTrace();
						Log.e(Error.name(), "Failed to copy training data to user's device");
					}
				} else {
					return true;
				}
			}
			return false;
		}
		
		private boolean initTessdataFolder() {
			Log.d(ApplicationCodes.Debug.name(), "Path name: " + DATA_PATH);
			File dir = new File(DATA_PATH + "/tessdata/");
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					Log.e(Error.name(), "Unable to create folder");
					return false;
				} else {
					Log.d(Success.name(), "Created folder");
				}
			}
			return true;
		}
	}
	
	private TessOCR(TessBaseAPI tess) {
		mTess = tess;
	}
	
	public String scanImage(Bitmap bitmap) {
		mTess.setImage(bitmap);
		return mTess.getUTF8Text();
	}
	
	@Override public void close() {
		if (mTess != null) {
			mTess.end();
		}
	}
}
