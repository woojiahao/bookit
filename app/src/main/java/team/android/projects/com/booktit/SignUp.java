package team.android.projects.com.booktit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static team.android.projects.com.booktit.utils.ui.UIUtils.clearInputs;

public class SignUp extends AppCompatActivity {
	private Button mSignUpBtn;
	private Button mSignInBtn;
	private EditText mEmailField;
	private EditText mUsernameField;
	private EditText mPasswordField;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		init();
		connectListeners();
	}
	
	private void init () {
		mSignUpBtn = findViewById(R.id.signUpBtn);
		mSignInBtn = findViewById(R.id.switchSignInBtn);
		
		mEmailField = findViewById(R.id.signupEmailField);
		mUsernameField = findViewById(R.id.signupUsernameField);
		mPasswordField = findViewById(R.id.signupPasswordField);
	}
	
	private void connectListeners () {
		mSignUpBtn.setOnClickListener(ev -> {
			String enteredEmail = mEmailField.getText().toString();
			String enteredUsername = mUsernameField.getText().toString();
			String enteredPassword = mPasswordField.getText().toString();

			Toast.makeText(this, String.format("Email: %s, Username: %s, Password: %s", enteredEmail, enteredUsername, enteredPassword), Toast.LENGTH_SHORT).show();

			startActivity(new Intent(this, SignUpGenreSelection.class));
		});

		mSignInBtn.setOnClickListener(ev -> {
			clearInputs(mEmailField, mUsernameField, mPasswordField);
			startActivity(new Intent(this, SignIn.class));
		});
	}
}
