package team.android.projects.com.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import team.android.projects.com.bookit.dataclasses.User;
import team.android.projects.com.bookit.utils.database.FirebaseOperations;
import team.android.projects.com.bookit.utils.database.IFirebaseOperations;
import team.android.projects.com.bookit.utils.database.UsersList;
import team.android.projects.com.bookit.utils.ui.UIUtils;
import team.android.projects.com.bookit.utils.ui.custom_views.clearable_edit_text.ClearableEditText;

import static team.android.projects.com.bookit.dataclasses.UserKeys.Email;
import static team.android.projects.com.bookit.utils.logging.Logging.shortToast;
import static team.android.projects.com.bookit.utils.ui.ButtonStates.Disabled;
import static team.android.projects.com.bookit.utils.ui.UIUtils.displayExitConfirmDialog;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;
import static team.android.projects.com.bookit.utils.ui.UIUtils.modifyRedButton;

public class ForgotPassword extends AppCompatActivity {
	private Button mSignInBtn;
	private Button mSendEmailBtn;
	
	private ClearableEditText mEmailField;
	private ClearableEditText mUsernameField;
	
	private IFirebaseOperations mFirebaseOperations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		
		init();
		connectListeners();
	}
	
	@Override public void onBackPressed() {
		displayExitConfirmDialog(this);
	}
	
	private void init() {
		mFirebaseOperations = new FirebaseOperations(this);
		
		mSignInBtn = find(this, R.id.switchSignInBtn);
		mSendEmailBtn = find(this, R.id.sendEmailBtn);
		
		mEmailField = find(this, R.id.forgotPasswordEmailField);
		mUsernameField = find(this, R.id.forgotPasswordUsernameField);
	}
	
	private void connectListeners() {
		mSignInBtn.setOnClickListener(v -> {
			startActivity(new Intent(this, SignIn.class));
			finish();
		});
		mSendEmailBtn.setOnClickListener(v -> {
			attemptReset();
		});
	}
	
	private void attemptReset() {
		if (!UIUtils.isFilled(mEmailField, mUsernameField)) {
			shortToast(this, getString(R.string.empty_inputs_warning));
		} else {
			String email = mEmailField.getText();
			String username = mUsernameField.getText();
			
			User u = UsersList.findUser(email, Email);
			if (u == null) {
				shortToast(this, getString(R.string.invalid_user));
			} else {
				if (!u.username.equals(username)) {
					shortToast(this, getString(R.string.mismatch_forgot_password));
				} else {
					modifyRedButton(this, mSendEmailBtn, Disabled);
					mFirebaseOperations.sendRecoveryEmail(email, true);
				}
			}
		}
	}
}
