package team.android.projects.com.bookit.dataclasses;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class User {
	public String email;
	public String username;
	public String uid;
	
	public List<String> genres;
	
	public User() {
	
	}
	
	public User(String uid, String email, String username, List<String> genres) {
		this.uid = uid;
		this.email = email;
		this.username = username;
		this.genres = genres;
	}
}
