package com.spand.meme.core.ui.activity.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.spand.meme.R;
import com.spand.meme.core.data.database.FireBaseDBInitializer;
import com.spand.meme.core.logic.authorization.AUTH_WAY;
import com.spand.meme.core.logic.authorization.EmailAuthorizer;
import com.spand.meme.core.logic.authorization.PhoneAuthorizer;

import static com.spand.meme.core.logic.authorization.AUTH_WAY.EMAIL;
import static com.spand.meme.core.logic.authorization.AUTH_WAY.PHONE;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_OLD_CHANGE_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spand.meme.core.logic.starter.SettingsConstants.PREF_NAME;
import static com.spand.meme.core.logic.starter.Starter.REGISTRATOR;
import static com.spand.meme.core.logic.starter.Starter.START_TYPE;
import static com.spand.meme.core.logic.starter.Starter.USERNAME;

/**
 * A Register screen that offers a registration procedure via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("StaticFieldLeak")
    private static RegisterActivity instance;

    public static RegisterActivity getInstance(){
        return instance;
    }

    // UI references.
    private EditText mNameView;
    private AutoCompleteTextView mEmailOrPhoneView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = RegisterActivity.class.getSimpleName();

    private AUTH_WAY authWay;

    @VisibleForTesting
    private ProgressDialog mProgressDialog;


    /**
     * Perform initialization of all fragments of current activity.
     *
     * @param savedInstanceState an instance of Bundle instance
     *                           (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        instance = this;
        // Views
        mNameView = findViewById(R.id.register_form_name);
        mEmailOrPhoneView = findViewById(R.id.register_form_email_phone_number);
        mPasswordView = findViewById(R.id.register_form_password);
        mPasswordConfirmView = findViewById(R.id.register_form_password_confirm);

        mEmailOrPhoneView.setText("+48577215683");
        // Buttons
        findViewById(R.id.email_sign_up_button).setOnClickListener(this);

    }

    /**
     * Called after onCreate(Bundle) — or after onRestart() when the activity had been stopped,
     * but is now again being displayed to the user. It will be followed by onResume().
     **/
    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    /**
     * Updates ui according to registration result.
     **/
    private void updateUI() {
        hideProgressDialog();
    }

    /**
     * Performs a Sign Up procedure with FireBase module.
     *
     * @param email           a String object which will be used by FireBase module during SignUp
     * @param password        a String object which will be used by FireBase module during SignUp
     * @param confirmPassword a String object which will be used during validation form
     **/
    private void signUp(String email, String password, String confirmPassword) {
        Log.d(TAG, getString(R.string.register_log_sign_up) + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        // Check for a valid password confirmation, if the user entered one.
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            mPasswordConfirmView.setError(getString(R.string.register_error_empty_password));
            return;
        }

        if (!isPasswordValid(password)) {
            mPasswordConfirmView.setError(getString(R.string.register_error_invalid_password));
            return;
        }

        switch (authWay){
            case EMAIL:{
                EmailAuthorizer.init(this, mNameView.getText().toString(), email, password).verify();
                break;
            }
            case PHONE:{
                PhoneAuthorizer.init(this, mNameView.getText().toString(), email).verify();
            }
        }
    }

    /**
     * A callback method to be invoked when a view is clicked.
     *
     * @param view an instance of View class
     *             ( class represents the basic building block for user interface components )
     **/
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.email_sign_up_button) {
            try {
                signUp(mEmailOrPhoneView.getText().toString()
                        , mPasswordView.getText().toString()
                        , mPasswordConfirmView.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shows progress dialog while backend action is in progress.
     **/
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.register_checking));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    /**
     * Hides progress dialog from screen.
     **/
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Returns true or false if a password is valid or not.
     *
     * @param password a String object which must be validated
     * @return a boolean value. Depends on validation result
     **/
    private boolean isPasswordValid(String password) {
        return (!password.isEmpty() && password.length() > 4);
    }

    /**
     * Validates filled values in all fields of current activity on screen.
     **/
    private boolean validateForm() {
        String email = mEmailOrPhoneView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailOrPhoneView.setError(getString(R.string.register_field_required));
            return false;
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                authWay = EMAIL;
            } else if (Patterns.PHONE.matcher(email).matches()) {
                authWay = PHONE;
            }
        }

        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.register_field_required));
            return false;
        }

        String confirmPassword = mPasswordConfirmView.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            mPasswordView.setError(getString(R.string.register_field_required));
            return false;
        }
        return true;
    }
}