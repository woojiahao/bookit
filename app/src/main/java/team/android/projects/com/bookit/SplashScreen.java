package team.android.projects.com.bookit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import team.android.projects.com.bookit.dataclasses.User;
import team.android.projects.com.bookit.dataclasses.UserKeys;
import team.android.projects.com.bookit.utils.database.FirebaseOperations;
import team.android.projects.com.bookit.utils.database.IFirebaseOperations;
import team.android.projects.com.bookit.utils.database.UsersList;

import static team.android.projects.com.bookit.dataclasses.UserKeys.Uid;

public class SplashScreen extends AppCompatActivity {
	private IFirebaseOperations mFirebaseOperations;
	private final String[] mLanguages = { "English", "Chinese", "Malay" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		setupLanguage();
		init();
		startApp();
	}
	
	private void setupLanguage() {
		String deviceLanguage = Locale.getDefault().getDisplayLanguage();
		if (!Arrays.asList(mLanguages).contains(deviceLanguage)) {
			deviceLanguage = "English";
		}
		
		SharedPreferences preferences = getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);
		if (!preferences.contains(getString(R.string.default_lang_key))) {
			preferences.edit().putString(getString(R.string.default_lang_key), deviceLanguage).apply();
		}
	}
	
	private void init() {
		mFirebaseOperations = new FirebaseOperations(this);
	}
	
	private void startApp() {
		DatabaseReference users = FirebaseDatabase
				.getInstance()
				.getReference()
				.child("users");
		
		users.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				List<User> users = new ArrayList<User>();
				for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
					User user = snapshot.getValue(User.class);
					if (user != null) users.add(user);
				}
				UsersList.setUsers(users);
				
				FirebaseAuth auth = FirebaseAuth.getInstance();
				FirebaseUser user = auth.getCurrentUser();
				if (user != null) {
					User currentUser = UsersList.findUser(user.getUid(), Uid);
					if (currentUser != null) {
						UsersList.setCurrentUser(currentUser.uid);
					}
				}
				
				startActivity(
						new Intent(
								SplashScreen.this,
								user != null ? Container.class : SignUp.class
						)
				 );
				finish();
			}
			
			@Override public void onCancelled(@NonNull DatabaseError databaseError) {
			
			}
		});
	}
}
