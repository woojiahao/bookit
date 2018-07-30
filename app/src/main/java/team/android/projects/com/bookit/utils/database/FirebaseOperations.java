package team.android.projects.com.bookit.utils.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.android.projects.com.bookit.Container;
import team.android.projects.com.bookit.Preloading;
import team.android.projects.com.bookit.SignUp;
import team.android.projects.com.bookit.dataclasses.User;

import static team.android.projects.com.bookit.utils.logging.ApplicationCodes.Debug;
import static team.android.projects.com.bookit.utils.logging.ApplicationCodes.Error;
import static team.android.projects.com.bookit.utils.logging.ApplicationCodes.Success;
import static team.android.projects.com.bookit.utils.logging.Logging.shortToast;

// todo: add email verification for newly created account
public class FirebaseOperations implements IFirebaseOperations {
	private FirebaseAuth mFirebaseAuth;
	private DatabaseReference mFirebaseDatabase;
	private Context mContext;
	
	public FirebaseOperations(Context c) {
		mContext = c;
		mFirebaseAuth = FirebaseAuth.getInstance();
		mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
	}
	
	@Override
	public void registerUser(String email, String password, String username, String[] genres) {
		if (mFirebaseAuth.getCurrentUser() != null) {
			mFirebaseAuth.signOut();
		}
		
		if (Preloading.findUser(username, "username") != null) {
			shortToast(mContext, String.format("Username: %s is used already, try again", username));
			return;
		}
		
		mFirebaseAuth
				.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						configureUser(email, username, genres);
						if (getCurrentUser() != null) {
							Preloading.setCurrentUser(getCurrentUser().getUid());
						}
						mContext.startActivity(new Intent(mContext, Container.class));
						((Activity) mContext).finish();
					}
				})
				.addOnFailureListener(exception -> {
					if (exception instanceof FirebaseAuthUserCollisionException) {
						shortToast(mContext, String.format("%s is used by another user, try again", email));
					} else if (exception instanceof FirebaseAuthWeakPasswordException) {
						shortToast(mContext, "Password is too weak");
					}
					
					mContext.startActivity(new Intent(mContext, SignUp.class));
					((Activity) mContext).finish();
				});
	}
	
	@Override public void signIn(String email, String password) {
		if (mFirebaseAuth.getCurrentUser() != null) {
			mFirebaseAuth.signOut();
		}
		
		mFirebaseAuth
				.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						Preloading.setCurrentUser(getCurrentUser().getUid());
						mContext.startActivity(new Intent(mContext, Container.class));
						((Activity) mContext).finish();
					}
				})
				.addOnFailureListener(exception -> {
					if (exception instanceof FirebaseAuthInvalidUserException) {
						shortToast(mContext, "Email account does not exist");
					} else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
						shortToast(mContext, "Invalid password");
					}
				});
	}
	
	@Override public FirebaseUser getCurrentUser() {
		return mFirebaseAuth.getCurrentUser();
	}
	
	@Override public void signOut() {
		mFirebaseAuth.signOut();
		Log.d(Debug.name(), String.format("isLoggedIn: %s", getCurrentUser() != null));
	}
	
	@Override public String getUsername() {
		return Preloading.getCurrentUser().username;
	}
	
	@Override public String getEmail() {
		return Preloading.getCurrentUser().email;
	}
	
	@Override public List<String> getGenres() {
		return Preloading.getCurrentUser().genres;
	}
	
	private void configureUser(final String email, final String username, final String[] genres) {
		String uid = "";
		if (mFirebaseAuth.getCurrentUser() != null) {
			uid = mFirebaseAuth.getCurrentUser().getUid();
		}
		
		User user = new User(uid, email, username,
				Arrays.asList(genres),
				new ArrayList<String>(),
				new ArrayList<String>());
		if (uid.equals("")) {
			Log.e(Error.name(), "Invalid user id generated");
		} else {
			DatabaseReference child = mFirebaseDatabase.child("users")
					.child(uid);
			child.setValue(user)
					.addOnCompleteListener(task -> {
						if (task.isSuccessful()) {
							Log.d(Success.name(), "Added user: " + username);
						} else {
							Log.d(Error.name(), "Unable to add user: " + username);
						}
					});
			Preloading.addUser(user);
		}
	}
}
