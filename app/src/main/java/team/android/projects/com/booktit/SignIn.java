package team.android.projects.com.booktit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
	private Button mSignInBtn;
	private Button mSignUpBtn;
	private TextView mForgotPassword;
	private EditText mUsernameEmailField;
	private EditText mPasswordField;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		init();
		connectListeners();
	}
	
	private void init () {
		mSignInBtn = findViewById(R.id.signInBtn);
		mSignUpBtn = findViewById(R.id.switchSignUpBtn);
		
		mForgotPassword = findViewById(R.id.forgotPasswordTxt);
		
		mUsernameEmailField = findViewById(R.id.loginUsernameField);
		mPasswordField = findViewById(R.id.loginPasswordField);
	}
	
	private void connectListeners () {
		mForgotPassword.setOnClickListener(ev ->
				Toast.makeText(this, "Forgot password pressed", Toast.LENGTH_SHORT).show());
		
		mSignInBtn.setOnClickListener(ev -> {
			String enteredUsernameEmail = mUsernameEmailField.getText().toString();
			String enteredPassword = mPasswordField.getText().toString();
			
			Toast.makeText(this, String.format("Username/Email: %s, Password: %s", enteredUsernameEmail, enteredPassword), Toast.LENGTH_SHORT).show();
		});
		
		mSignUpBtn.setOnClickListener(ev -> startActivity(new Intent(this, SignUp.class)));
	}
	
}
