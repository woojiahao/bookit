package team.android.projects.com.bookit.utils.database;

import android.text.TextUtils;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class DbOperations implements IDbOperations {
	private Connection mConnection;
	private final String DATABASE_LOG_STRING = "Database Operation";
	
	public DbOperations () {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mConnection = DriverManager.getConnection("jdbc:mysql://localhost/bookit_db?user=root&password=12345");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Log.e(DATABASE_LOG_STRING, "Unable to load driver");
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(DATABASE_LOG_STRING, "Unable to get a connection");
		}
	}
	
	/**
	 * Inserts the specified values into the table, fitting the columns.
	 *
	 * To use: make a String array of the column names, and make a HashMap with the key as the
	 * value to input and the value being the datatype of that value.
	 *
	 * Eg.
	 * Map<String, Class> map = new HashMap<>();
	 * map.put("something", String.class);
	 * map.put(1, Integer.class);
	 * @param table Table name
	 * @param cols Columns to insert
	 * @param values Values to be inserted
	 * @throws DbOperationError Thrown when the lengths of the columns and the values are uneven
	 */
	@Override public void insert (String table, String[] cols, Map<Object, Class> values) throws DbOperationError {
		if (cols.length != values.size()) {
			throw new DbOperationError(
					String.format("Values to fill should equal the columns to target (%s)",
							cols.length));
		}
		
		String pre = "INSERT INTO %s (%s) VALUES (%s);";
		String query = String.format(pre, table, TextUtils.join(", ", cols), genValues(values));
		Log.d(DATABASE_LOG_STRING, "query: " + query);
	}
	
	private String genValues (Map<Object, Class> map) {
		StringBuilder values = new StringBuilder();
		for (Map.Entry<Object, Class> entry : map.entrySet()) {
			Object key = entry.getKey();
			Class classVal = entry.getValue();
			
			if (classVal.equals(String.class)) {
				values.append("'").append((String) key).append("'");
			} else if (classVal.equals(Integer.class)) {
				values.append(Integer.parseInt((String) key));
			}
			
			values.append(",");
		}
		return values.toString();
	}
}
