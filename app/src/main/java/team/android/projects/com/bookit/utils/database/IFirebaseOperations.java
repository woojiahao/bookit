package team.android.projects.com.bookit.utils.database;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import team.android.projects.com.bookit.dataclasses.User;

public interface IFirebaseOperations {
	void registerUser(String email, String password, String username, String[] genres);
	void signIn(String email, String password);
	void sendRecoveryEmail(String email, boolean redirect);
	void signOut();
	
	FirebaseUser getCurrentUser();
	
	String getUsername();
	String getEmail();
	List<String> getGenres();
	
	void editGenres(String[] newGenres);
}
