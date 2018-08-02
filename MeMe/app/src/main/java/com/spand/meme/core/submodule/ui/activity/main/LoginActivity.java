package com.spand.meme.core.submodule.ui.activity.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spand.meme.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = LoginActivity.class.getSimpleName();

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @VisibleForTesting
    private ProgressDialog mProgressDialog;

    // Firebase related fields
    private FirebaseAuth mAuth;

    /**
     *  Perform initialization of all fragments of current activity.
     *  @param savedInstanceState an instance of Bundle instance
     *                            (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        /*mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);*/
        mEmailView = findViewById(R.id.login_form_email);
        mPasswordView = findViewById(R.id.login_form_password);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);

        // auth init
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     *  Called after onCreate(Bundle) — or after onRestart() when the activity had been stopped,
     *  but is now again being displayed to the user. It will be followed by onResume().
     **/
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     *  Updates ui according to authorization result.
     *  @param user an instance of User class (FireBase context)
     **/
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        /*if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }*/
    }

    /**
     *  A callback method to be invoked when a view is clicked.
     *  @param view an instance of View class
     *              ( class represents the basic building block for user interface components )
     **/
    @Override
    public void onClick(View view) {
        int i = view.getId();
        /*if (i == R.id.email_create_account_button) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else */
        if (i == R.id.email_sign_in_button) {
            signIn(mEmailView.getText().toString(), mPasswordView.getText().toString());
        }/* else if (i == R.id.sign_out_button) {
            signOut();
        } else if (i == R.id.verify_email_button) {
            sendEmailVerification();
        }*/
    }

    /**
     *  Shows progress dialog while backend action is in progress.
     **/
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.checking));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    /**
     *  Hides progress dialog from screen.
     **/
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     *  Starts main activity of the application.
     **/
    public void finishSingInActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     *  Performs Sign In operation.
     *  @param email a String object which will be checked during authorization procedure
     *  @param password a String object which will be checked during authorization procedure
     **/
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        //updateUI(user);
                        finishSingInActivity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                    hideProgressDialog();
                });
    }

    /**
     *  Performs validation procedure before Sign In operation.
     **/
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Required.");
            valid = false;
        } else {
            mEmailView.setError(null);
        }

        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Required.");
            valid = false;
        } else {
            mPasswordView.setError(null);
        }
        return valid;
    }
}

