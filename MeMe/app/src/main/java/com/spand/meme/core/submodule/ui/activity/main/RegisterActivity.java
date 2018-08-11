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
 * A Register screen that offers a registration procedure via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    // UI references.
    private EditText mNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = RegisterActivity.class.getSimpleName();

    // Firebase related fields
    private FirebaseAuth mAuth;

    @VisibleForTesting
    private ProgressDialog mProgressDialog;

    /**
     *  Perform initialization of all fragments of current activity.
     *  @param savedInstanceState an instance of Bundle instance
     *                            (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Views
        mNameView = findViewById(R.id.register_form_name);
        mEmailView = findViewById(R.id.register_form_email);
        mPasswordView = findViewById(R.id.register_form_password);
        mPasswordConfirmView = findViewById(R.id.register_form_password_confirm);

        // Buttons
        findViewById(R.id.email_sign_up_button).setOnClickListener(this);

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
        updateUI();
    }

    /**
     *  Updates ui according to registration result.
     **/
    private void updateUI() {
        hideProgressDialog();
    }

    /**
     *  Starts main activity of the application.
     **/
    public void finishSingUpActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     *  Performs a Sign Up procedure with FireBase module.
     *  @param email a String object which will be used by FireBase module during SignUp
     *  @param password a String object which will be used by FireBase module during SignUp
     *  @param confirmPassword a String object which will be used during validation form
     **/
    private void signUp(String email, String password, String confirmPassword) {
        Log.d(TAG, getString(R.string.sign_up) + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        // Check for a valid password confirmation, if the user entered one.
        if (TextUtils.isEmpty(confirmPassword)) {
            mPasswordConfirmView.setError(getString(R.string.error_empty_password));
        } else {
            if (!isPasswordValid(confirmPassword)) {
                mPasswordConfirmView.setError(getString(R.string.error_invalid_password));
            }
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, getString(R.string.create_user_with_email_success));
                        updateUI();
                        finishSingUpActivity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, getString(R.string.create_user_with_email_failure), task.getException());
                        Toast.makeText(RegisterActivity.this, getString(R.string.authentication_failed),
                                Toast.LENGTH_SHORT).show();
                        updateUI();
                    }
                    hideProgressDialog();
                });
    }

    /**
     *  A callback method to be invoked when a view is clicked.
     *  @param view an instance of View class
     *              ( class represents the basic building block for user interface components )
     **/
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.email_sign_up_button) {
            signUp(mEmailView.getText().toString()
                    , mPasswordView.getText().toString()
                    , mPasswordConfirmView.getText().toString());
        }
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
     *  Returns true or false if a password is valid or not.
     *  @param password a String object which must be validated
     *  @return a boolean value. Depends on validation result
     **/
    private boolean isPasswordValid(String password) {
        return (password.length() > 4 && !password.isEmpty());
    }

    /**
     *  Validates filled values in all fields of current activity on screen.
     **/
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.field_required));
            valid = false;
        } else {
            mEmailView.setError(null);
        }

        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.field_required));
            valid = false;
        } else {
            mPasswordView.setError(null);
        }

        String confirmPassword = mPasswordConfirmView.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            mPasswordView.setError(getString(R.string.field_required));
            valid = false;
        } else {
            mPasswordView.setError(null);
        }

        return valid;
    }
}