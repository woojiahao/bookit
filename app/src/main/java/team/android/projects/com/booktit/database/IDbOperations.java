package team.android.projects.com.booktit.database;

public interface IDbOperations {
	void insert (String table, String[] cols, String[] values) throws DbOperationError;
}
