package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.utils.ui.UIUtils.clearInputs;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class SignIn extends AppCompatActivity {
	private Button mSignInBtn;
	private Button mSignUpBtn;
	private TextView mForgotPassword;
	private ClearableEditText mUsernameEmailField;
	private ClearableEditText mPasswordField;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		init();
		connectListeners();
	}
	
	private void init () {
		mSignInBtn = find(this, R.id.signInBtn);
		mSignUpBtn = find(this, R.id.switchSignUpBtn);
		
		mForgotPassword = find(this, R.id.forgotPasswordTxt);
		
		mUsernameEmailField = find(this, R.id.loginUsernameField);
		mPasswordField = find(this, R.id.loginPasswordField);
	}
	
	private void connectListeners () {
		mForgotPassword.setOnClickListener(ev -> {
			clearInputs(mUsernameEmailField, mPasswordField);
			startActivity(new Intent(this, ForgotPassword.class));
		});
		
		mSignInBtn.setOnClickListener(ev -> {
			String enteredUsernameEmail = mUsernameEmailField.getText();
			String enteredPassword = mPasswordField.getText();
			
			Toast.makeText(this, String.format("Username/Email: %s, Password: %s", enteredUsernameEmail, enteredPassword), Toast.LENGTH_SHORT).show();
			
			//startActivity(new Intent(this, Container.class));
			startActivity(new Intent(this, Details.class));
		});
		
		mSignUpBtn.setOnClickListener(ev -> {
			clearInputs(mUsernameEmailField, mPasswordField);
			startActivity(new Intent(this, SignUp.class));
		});
	}
}
