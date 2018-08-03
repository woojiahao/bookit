package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import team.android.projects.com.bookit.database.UsersList;
import team.android.projects.com.bookit.ui.custom.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.dataclasses.UserKeys.Email;
import static team.android.projects.com.bookit.dataclasses.UserKeys.Username;
import static team.android.projects.com.bookit.logging.Logging.shortToast;
import static team.android.projects.com.bookit.util.UIUtils.displayExitConfirmDialog;
import static team.android.projects.com.bookit.util.UIUtils.find;
import static team.android.projects.com.bookit.util.UIUtils.isEmail;
import static team.android.projects.com.bookit.util.UIUtils.isFilled;

public class SignUp extends AppCompatActivity {
	private Button mSignUpBtn;
	private Button mSignInBtn;
	private ClearableEditText mEmailField;
	private ClearableEditText mUsernameField;
	private ClearableEditText mPasswordField;
	
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		init();
		connectListeners();
	}
	
	@Override public void onBackPressed() {
		displayExitConfirmDialog(this);
	}
	
	private void init() {
		mSignUpBtn = find(this, R.id.signUpBtn);
		mSignInBtn = find(this, R.id.switchSignInBtn);
		
		mEmailField = find(this, R.id.signupEmailField);
		mUsernameField = find(this, R.id.signupUsernameField);
		mPasswordField = find(this, R.id.signupPasswordField);
	}
	
	private void connectListeners() {
		mSignUpBtn.setOnClickListener(v -> attemptSignUp());
		
		mSignInBtn.setOnClickListener(v -> {
			startActivity(new Intent(this, SignIn.class));
			finish();
		});
	}
	
	private void attemptSignUp() {
		if (!isFilled(mEmailField, mUsernameField, mPasswordField)) {
			shortToast(this, getString(R.string.empty_inputs_warning));
		} else {
			String username = mUsernameField.getText();
			String email = mEmailField.getText();
			String password = mPasswordField.getText();
			
			if (!isEmail(email)) {
				shortToast(this, getString(R.string.invalid_email_warning));
			} else {
				if (UsersList.findUser(email, Email) != null) {
					shortToast(this, String.format(getString(R.string.email_used_warning), email));
				} else {
					if (password.length() < 6) {
						shortToast(this, getString(R.string.password_short_warning));
					} else {
						if (UsersList.findUser(username, Username) != null) {
							shortToast(this, String.format(getString(R.string.username_used), username));
						} else {
							startActivity(
									new Intent(this, SignUpGenreSelection.class)
											.putExtra("email", email)
											.putExtra("username", username)
											.putExtra("password", password));
						}
					}
				}
				
			}
		}
	}
}
