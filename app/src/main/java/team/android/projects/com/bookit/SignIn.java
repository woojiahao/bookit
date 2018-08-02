package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import team.android.projects.com.bookit.dataclasses.User;
import team.android.projects.com.bookit.dataclasses.UserKeys;
import team.android.projects.com.bookit.utils.database.FirebaseOperations;
import team.android.projects.com.bookit.utils.database.IFirebaseOperations;
import team.android.projects.com.bookit.utils.database.UsersList;
import team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.dataclasses.UserKeys.Username;
import static team.android.projects.com.bookit.utils.logging.Logging.shortToast;
import static team.android.projects.com.bookit.utils.ui.UIUtils.displayExitConfirmDialog;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;
import static team.android.projects.com.bookit.utils.ui.UIUtils.isEmail;
import static team.android.projects.com.bookit.utils.ui.UIUtils.isFilled;

public class SignIn extends AppCompatActivity {
	private Button mSignInBtn;
	private Button mSignUpBtn;
	private TextView mForgotPassword;
	private ClearableEditText mUsernameEmailField;
	private ClearableEditText mPasswordField;
	
	private IFirebaseOperations mFirebaseOperations;
	
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		init();
		connectListeners();
	}
	
	@Override public void onBackPressed() {
		displayExitConfirmDialog(this);
	}
	
	private void init() {
		mFirebaseOperations = new FirebaseOperations(this);
		
		mSignInBtn = find(this, R.id.signInBtn);
		mSignUpBtn = find(this, R.id.switchSignUpBtn);
		
		mForgotPassword = find(this, R.id.forgotPasswordTxt);
		
		mUsernameEmailField = find(this, R.id.loginUsernameField);
		mPasswordField = find(this, R.id.loginPasswordField);
	}
	
	private void connectListeners() {
		mForgotPassword.setOnClickListener(ev -> {
			startActivity(new Intent(this, ForgotPassword.class));
			finish();
		});
		
		mSignInBtn.setOnClickListener(ev -> {
			String email = mUsernameEmailField.getText();
			String password = mPasswordField.getText();
			
			if (!isFilled(mUsernameEmailField, mPasswordField)) {
				shortToast(this, getString(R.string.empty_inputs_warning));
			} else {
				if (!isEmail(email)) {
					User matchedUser = UsersList.findUser(email, Username);
					if (matchedUser != null) {
						email = matchedUser.email;
					} else {
						shortToast(this, "User account does not exist");
						return;
					}
				}
				mFirebaseOperations.signIn(email, password);
			}
		});
		
		mSignUpBtn.setOnClickListener(ev -> {
			startActivity(new Intent(this, SignUp.class));
			finish();
		});
	}
}
