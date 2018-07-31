package team.android.projects.com.bookit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import team.android.projects.com.bookit.utils.database.FirebaseOperations;
import team.android.projects.com.bookit.utils.database.IFirebaseOperations;

import static team.android.projects.com.bookit.utils.ui.UIUtils.displayExitConfirmDialog;
import static team.android.projects.com.bookit.utils.ui.UIUtils.find;

public class RecoveryEmailSentSuccess extends AppCompatActivity {
	private TextView mStatusMessage;
	private TextView mResendEmail;
	private Button mSignInBtn;
	private ImageView mBackBtn;
	
	private String mEmailAddress;
	
	private IFirebaseOperations mFirebaseOperations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recovery_email_sent_success);
		
		init();
		connectListeners();
	}
	
	@Override public void onBackPressed() {
		displayExitConfirmDialog(this);
	}
	
	private void init() {
		mFirebaseOperations = new FirebaseOperations(this);
		
		mStatusMessage = find(this, R.id.statusMessage);
		mResendEmail = find(this, R.id.resendEmail);
		mSignInBtn = find(this, R.id.signInBtn);
		mBackBtn = find(this, R.id.backBtn);
		
		Bundle extras = getIntent().getExtras();
		mEmailAddress = extras != null ? extras.getString("email") : "blank@gmail.com";
		mStatusMessage.setText(String.format("An email has been sent to %s", mEmailAddress));
	}
	
	private void connectListeners() {
		mSignInBtn.setOnClickListener(view -> {
			startActivity(new Intent(this, SignIn.class));
			finish();
		});
		
		mBackBtn.setOnClickListener(view -> {
			startActivity(new Intent(this, ForgotPassword.class));
			finish();
		});
		
		mResendEmail.setOnClickListener(view -> {
			mFirebaseOperations.sendRecoveryEmail(mEmailAddress, false);
		});
	}
}
