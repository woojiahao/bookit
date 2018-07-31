package team.android.projects.com.bookit;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team.android.projects.com.bookit.dataclasses.User;
import team.android.projects.com.bookit.dataclasses.UserKeys;

import static team.android.projects.com.bookit.dataclasses.UserKeys.Uid;
import static team.android.projects.com.bookit.utils.logging.ApplicationCodes.Debug;

// todo: fix potential bug when loading user twice (check if the onDataChange method is called again when the user is registered
public class Preloading extends Application {
	private static List<User> mUsers = new ArrayList<User>();
	private static User mCurrentUser = null;
	
	@Override public void onCreate() {
		super.onCreate();
		init();
	}
	
	private void init() {
		DatabaseReference dbReference = FirebaseDatabase
				.getInstance()
				.getReference()
				.child("users");
		
		dbReference
				.addValueEventListener(new ValueEventListener() {
					@Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						Log.d(Debug.name(), "Loading users");
						for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
							User user = snapshot.getValue(User.class);
							if (user != null) {
								if (findUser(user.uid, Uid) == null) {
									mUsers.add(user);
								}
							}
						}
					}
					
					@Override public void onCancelled(@NonNull DatabaseError databaseError) {
					
					}
				});
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if (user != null) {
			mCurrentUser = findUser(user.getUid(), Uid);
		}
	}
	
	public static List<User> getUsers() {
		return mUsers;
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
	
	public static void addUser(User user) {
		mUsers.add(user);
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
}
