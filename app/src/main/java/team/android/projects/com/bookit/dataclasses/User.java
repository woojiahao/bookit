package team.android.projects.com.bookit.dataclasses;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class User {
	public String email;
	public String username;
	public List<String> genres;
	
	public User() {
	
	}
	
	public User(String email, String username, List<String> genres) {
		this.email = email;
		this.username = username;
		this.genres = genres;
	}
}
