package team.android.projects.com.bookit.util;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static team.android.projects.com.bookit.logging.ApplicationCodes.Error;

public class Utils {
	public static String loadJSON(AssetManager manager, String fileName) {
		StringBuilder json = new StringBuilder();
		try (BufferedReader reader
					 = new BufferedReader(
				new InputStreamReader(manager.open(fileName)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				json.append(line).append("\n");
			}
			
			return json.toString();
		} catch (IOException e) {
			Log.e(Error.name(), String.format("Unable to load %s", fileName));
			e.printStackTrace();
		}
		return null;
	}
}
