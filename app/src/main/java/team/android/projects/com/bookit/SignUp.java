package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static team.android.projects.com.bookit.utils.ui.UIUtils.clearInputs;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class SignUp extends AppCompatActivity {
	private Button mSignUpBtn;
	private Button mSignInBtn;
	private ClearableEditText mEmailField;
	private ClearableEditText mUsernameField;
	private ClearableEditText mPasswordField;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		init();
		connectListeners();
	}
	
	private void init () {
		mSignUpBtn = find(this, R.id.signUpBtn);
		mSignInBtn = find(this, R.id.switchSignInBtn);
		
		mEmailField = find(this, R.id.signupEmailField);
		mUsernameField = find(this, R.id.signupUsernameField);
		mPasswordField = find(this, R.id.signupPasswordField);
	}
	
	private void connectListeners () {
		mSignUpBtn.setOnClickListener(ev -> {
			String enteredEmail = mEmailField.getText();
			String enteredUsername = mUsernameField.getText();
			String enteredPassword = mPasswordField.getText();

			Toast.makeText(this, String.format("Email: %s, Username: %s, Password: %s", enteredEmail, enteredUsername, enteredPassword), Toast.LENGTH_SHORT).show();

			startActivity(new Intent(this, SignUpGenreSelection.class));
		});

		mSignInBtn.setOnClickListener(ev -> {
			clearInputs(mEmailField, mUsernameField, mPasswordField);
			startActivity(new Intent(this, SignIn.class));
		});
	}
}
