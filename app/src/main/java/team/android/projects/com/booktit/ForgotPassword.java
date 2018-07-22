package team.android.projects.com.booktit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import team.android.projects.com.booktit.utils.ui.UIUtils;

public class ForgotPassword extends AppCompatActivity {
	
	private Button mSignInBtn;
	private Button mSendEmailBtn;
	
	private EditText mEmailField;
	private EditText mUsernameField;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		
		init();
		connectListeners();
	}
	
	private void init () {
		mSignInBtn = findViewById(R.id.switchSignInBtn);
		mSendEmailBtn = findViewById(R.id.sendEmailBtn);
		
		mEmailField = findViewById(R.id.forgotPasswordEmailField);
		mUsernameField = findViewById(R.id.forgotPasswordUsernameField);
	}
	
	private void connectListeners () {
		mSignInBtn.setOnClickListener(ev -> {
			UIUtils.clearInputs(mEmailField, mUsernameField);
			startActivity(new Intent(this, SignIn.class));
		});
		mSendEmailBtn.setOnClickListener(ev -> {
			Toast.makeText(this, "Sending Email", Toast.LENGTH_SHORT).show();
		});
	}
}
