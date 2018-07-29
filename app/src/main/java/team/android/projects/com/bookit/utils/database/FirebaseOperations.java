package team.android.projects.com.bookit.utils.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import team.android.projects.com.bookit.SignIn;
import team.android.projects.com.bookit.SignUp;
import team.android.projects.com.bookit.dataclasses.User;

import static team.android.projects.com.bookit.utils.ApplicationCodes.Debug;
import static team.android.projects.com.bookit.utils.ApplicationCodes.Success;
import static team.android.projects.com.bookit.utils.logging.Logging.shortToast;
import static team.android.projects.com.bookit.utils.ApplicationCodes.Error;

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
			Log.d(Debug.name(), "Current user email: " + mFirebaseAuth.getCurrentUser().getEmail());
			mFirebaseAuth.signOut();
		}
		
		mFirebaseAuth
				.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						configureUser(email, username, genres);
						mContext.startActivity(new Intent(mContext, SignIn.class));
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
	
	@Override public String getUsername() {
		FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
		return currentUser == null ? "" : currentUser.getDisplayName();
	}
	
	@Override public void signOut() {
		mFirebaseAuth.signOut();
	}
	
	private void configureUser(final String email, final String username, final String[] genres) {
		String uid = "";
		if (mFirebaseAuth.getCurrentUser() != null) {
			uid = mFirebaseAuth.getCurrentUser().getUid();
		}
		
		User user = new User(uid, email, username, Arrays.asList(genres));
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
		}
	}
}
