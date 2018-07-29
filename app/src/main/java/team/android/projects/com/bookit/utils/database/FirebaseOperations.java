package team.android.projects.com.bookit.utils.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import team.android.projects.com.bookit.SignIn;
import team.android.projects.com.bookit.SignUp;

import static team.android.projects.com.bookit.utils.logging.Logging.shortToast;

// todo: add email verification for newly created account!
public class FirebaseOperations implements IFirebaseOperations {
	private FirebaseAuth mFirebaseAuth;
	private Context mContext;
	
	public FirebaseOperations(Context c) {
		mFirebaseAuth = FirebaseAuth.getInstance();
		mContext = c;
	}
	
	@Override
	public void registerUser(String email, String password) {
		mFirebaseAuth
				.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(task -> {
					if (!task.isSuccessful()) {
						shortToast(mContext, "Authentication failed");
					} else {
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
}
