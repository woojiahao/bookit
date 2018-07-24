package team.android.projects.com.bookit.utils.database;

import java.util.Map;

public interface IDbOperations {
	void insert (String table, String[] cols, Map<Object, Class> values) throws DbOperationError;
}
