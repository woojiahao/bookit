package team.android.projects.com.bookit.utils.database;

public interface IFirebaseOperations {
	void registerUser(String email, String password, String username, String[] genres);
	String getUsername();
}
