package team.android.projects.com.bookit.utils.database;

import com.google.firebase.auth.FirebaseUser;

import team.android.projects.com.bookit.dataclasses.User;

public interface IFirebaseOperations {
	void registerUser(String email, String password, String username, String[] genres);
	FirebaseUser getCurrentUser();
	void signOut();
	
	User getUserPreferences();
}
