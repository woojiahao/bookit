package team.android.projects.com.booktit.database;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbOperations implements IDbOperations {
	private Connection mConnection;
	
	public DbOperations () {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mConnection = createConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Log.e("DATABASE ERROR", "Unable to load driver");
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e("DATABASE ERROR", "Unable to get a connection");
		}
	}
	
	@Override public void insert (String table, String[] cols, String[] values) throws DbOperationError {
		if (cols.length != values.length) {
			throw new DbOperationError(String.format("Values to fill should equal the columns to target (%s)",
					cols.length));
		}
	}
	
	private Connection createConnection () throws SQLException {
		String connectionString = "jdbc:mysql://localhost:3306/bookit_db";
		String username = "localhost";
		String password = "12345";
		return DriverManager.getConnection(connectionString, username, password);
	}
}
