package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.utils.ui.UIUtils.checkFilledInput;
import static team.android.projects.com.bookit.utils.ui.UIUtils.clearInputs;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;
import static team.android.projects.com.bookit.utils.logging.Logging.shortToast;

public class SignUp extends AppCompatActivity {
	private Button mSignUpBtn;
	private Button mSignInBtn;
	private ClearableEditText mEmailField;
	private ClearableEditText mUsernameField;
	private ClearableEditText mPasswordField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		init();
		connectListeners();
	}
	
	private void init() {
		mSignUpBtn = find(this, R.id.signUpBtn);
		mSignInBtn = find(this, R.id.switchSignInBtn);
		
		mEmailField = find(this, R.id.signupEmailField);
		mUsernameField = find(this, R.id.signupUsernameField);
		mPasswordField = find(this, R.id.signupPasswordField);
	}
	
	private void connectListeners() {
		mSignUpBtn.setOnClickListener(ev -> {
			if (!checkFilledInput(mEmailField, mUsernameField, mPasswordField)) {
				shortToast(this, "Please fill in all of the fields before proceeding");
			} else {
				startActivity(
						new Intent(this, SignUpGenreSelection.class)
								.putExtra("email", mEmailField.getText())
								.putExtra("username", mUsernameField.getText())
								.putExtra("password", mPasswordField.getText()));
			}
		});
		
		mSignInBtn.setOnClickListener(ev -> {
			clearInputs(mEmailField, mUsernameField, mPasswordField);
			startActivity(new Intent(this, SignIn.class));
		});
	}
}
