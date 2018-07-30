package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import team.android.projects.com.bookit.utils.ui.UIUtils;
import team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.utils.logging.Logging.shortToast;
import static team.android.projects.com.bookit.utils.ui.UIUtils.isFilled;
import static team.android.projects.com.bookit.utils.ui.UIUtils.displayExitConfirmDialog;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;
import static team.android.projects.com.bookit.utils.ui.UIUtils.isEmail;

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
		mSignUpBtn.setOnClickListener(ev -> {
			if (!isFilled(mEmailField, mUsernameField, mPasswordField)) {
				shortToast(this, getString(R.string.empty_inputs_warning));
			} else {
				if (!isEmail(mEmailField.getText())) {
					shortToast(this, getString(R.string.invalid_email_warning));
				} else {
					startActivity(
							new Intent(this, SignUpGenreSelection.class)
									.putExtra("email", mEmailField.getText())
									.putExtra("username", mUsernameField.getText())
									.putExtra("password", mPasswordField.getText()));
				}
			}
		});
		
		mSignInBtn.setOnClickListener(ev -> {
			startActivity(new Intent(this, SignIn.class));
			finish();
		});
	}
}
