package team.android.projects.com.bookit.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

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
import team.android.projects.com.bookit.R;
import team.android.projects.com.bookit.RecoveryEmailSentSuccess;
import team.android.projects.com.bookit.SignUp;
import team.android.projects.com.bookit.dataclasses.User;

import static team.android.projects.com.bookit.logging.ApplicationCodes.Error;
import static team.android.projects.com.bookit.logging.ApplicationCodes.Success;
import static team.android.projects.com.bookit.logging.Logging.shortToast;
import static team.android.projects.com.bookit.ui.ButtonStates.Enabled;
import static team.android.projects.com.bookit.util.UIUtils.find;
import static team.android.projects.com.bookit.util.UIUtils.modifyRedButton;

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
		
		mFirebaseAuth
				.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						configureUser(email, username, genres);
						if (getCurrentUser() != null) {
							UsersList.setCurrentUser(getCurrentUser().getUid());
						}
						mContext.startActivity(new Intent(mContext, Container.class));
						((Activity) mContext).finish();
					}
				})
				.addOnFailureListener(exception -> {
					if (exception instanceof FirebaseAuthUserCollisionException) {
						shortToast(mContext, String.format(mContext.getString(R.string.email_used_warning), email));
					} else if (exception instanceof FirebaseAuthWeakPasswordException) {
						shortToast(mContext, mContext.getString(R.string.password_short_warning));
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
						UsersList.setCurrentUser(getCurrentUser().getUid());
						mContext.startActivity(new Intent(mContext, Container.class));
						((Activity) mContext).finish();
					}
				})
				.addOnFailureListener(exception -> {
					Button signInButton = find((AppCompatActivity) mContext, R.id.signInBtn);
					modifyRedButton(mContext, signInButton, Enabled);
					if (exception instanceof FirebaseAuthInvalidUserException) {
						shortToast(mContext, "Email account does not exist");
					} else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
						shortToast(mContext, "Invalid password");
					}
				});
	}
	
	@Override public void sendRecoveryEmail(String email, boolean redirect) {
		mFirebaseAuth
				.sendPasswordResetEmail(email)
				.addOnCompleteListener(task -> {
					if (!task.isSuccessful()) {
						shortToast(mContext, mContext.getString(R.string.send_recovery_warning));
					} else {
						if (!redirect) {
							shortToast(mContext, mContext.getString(R.string.resend_confirmation));
						} else {
							Intent i = new Intent(mContext, RecoveryEmailSentSuccess.class);
							i.putExtra("email", email);
							mContext.startActivity(i);
							((Activity) mContext).finish();
						}
					}
				});
	}
	
	@Override public void signOut() {
		mFirebaseAuth.signOut();
	}
	
	@Override public FirebaseUser getCurrentUser() {
		return mFirebaseAuth.getCurrentUser();
	}
	
	@Override public String getUsername() {
		return UsersList.getCurrentUser().username;
	}
	
	@Override public String getEmail() {
		return UsersList.getCurrentUser().email;
	}
	
	@Override public List<String> getGenres() {
		return UsersList.getCurrentUser().genres;
	}
	
	@Override public void editGenres(String[] newGenres) {
		DatabaseReference userRef = mFirebaseDatabase
				.getRef()
				.child("users")
				.child(getCurrentUser().getUid())
				.child("genres");
		userRef
				.setValue(Arrays.asList(newGenres))
				.addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						UsersList.updateGenres(newGenres);
						((Activity) mContext).finish();
					}
				});
	}
	
	@Override public void addFavourite(String isbn) {
		User currentUser = UsersList.getCurrentUser();
		if (currentUser != null) {
			UsersList.addFavourite(isbn);
			setFavourites(currentUser.uid, currentUser.favourites);
		}
	}

	@Override public void removeFavourite(String isbn) {
		User currentUser = UsersList.getCurrentUser();
		if (currentUser != null) {
			UsersList.removeFavourite(isbn);
			setFavourites(currentUser.uid, currentUser.favourites);
		}
	}

	private void setFavourites(String uid, List<String> favourites) {
		DatabaseReference fav = mFirebaseDatabase
				.child("users")
				.child(uid)
				.child("favourites");
		fav
				.setValue(favourites)
				.addOnCompleteListener(task -> {
					if (!task.isSuccessful()) {
						Log.e(Error.name(), "Unable to set favourites");
					}
				});
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
			UsersList.addUser(user);
		}
	}
}
