package team.android.projects.com.bookit.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.dataclasses.User;
import team.android.projects.com.bookit.dataclasses.UserKeys;

// todo: move the user specific methods into another class
public class UsersList {
	private static List<User> mUsers = new ArrayList<User>();
	private static User mCurrentUser = null;
	
	public static void setUsers(List<User> users) {
		mUsers = new ArrayList<User>(users);
	}
	
	public static void addUser(User u) {
		mUsers.add(u);
	}
	
	public static void removeUser(String uid) {
		User toRemove = null;
		for (User user : mUsers) {
			if (user.uid.equals(uid)) toRemove = user;
		}
		
		if (toRemove != null) {
			mUsers.remove(toRemove);
		}
	}
	
	public static void editUser(User u) {
		String uid = u.uid;
		for (User user : mUsers) {
			if (user.uid.equals(uid)) {
				user.uid = u.uid;
				user.genres = u.genres;
				user.email = u.email;
				user.favourites = u.favourites;
				user.username = u.username;
			}
		}
	}
	
	public static void updateGenres(String[] genres) {
		getCurrentUser().genres = Arrays.asList(genres);
	}

	public static void addFavourite(String isbn) {
		if (mCurrentUser.favourites == null) {
			mCurrentUser.favourites = new ArrayList<String>();
		}
		
		mCurrentUser.favourites.add(isbn);
	}
	
	public static void removeFavourite(String isbn) {
		if (mCurrentUser.favourites != null) {
			mCurrentUser.favourites.remove(isbn);
		}
	}
	
	public static boolean hasFavourite(String isbn) {
		return mCurrentUser.favourites != null && mCurrentUser.favourites.size() != 0 && mCurrentUser.favourites.contains(isbn);
	}
	
	public static void setCurrentUser(String uid) {
		if (uid == null) {
			mCurrentUser = null;
			return;
		}
		
		for (User user : mUsers) {
			if (user.uid.equals(uid)) {
				mCurrentUser = user;
				return;
			}
		}
	}
	
	public static User getCurrentUser() {
		return mCurrentUser;
	}
	
	public static User findUser(String check, UserKeys checkType) {
		for (User u : mUsers) {
			switch (checkType) {
				case Uid:
					if (u.uid.equals(check)) return u;
					break;
				case Email:
					if (u.email.equals(check)) return u;
					break;
				case Username:
					if (u.username.equals(check)) return u;
					break;
			}
		}
		return null;
	}
}
