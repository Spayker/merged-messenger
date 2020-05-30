package com.spand.meme.core.submodule.ui.activity.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.spand.meme.R;
import com.spand.meme.core.submodule.data.database.FireBaseDBInitializer;

import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.EMPTY_STRING;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_AUTO_LOGIN;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_OLD_CHANGE_PASS;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_PASS;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_REMEMBER;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_USER_EMAIL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.PREF_NAME;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher,
        CompoundButton.OnCheckedChangeListener {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = LoginActivity.class.getSimpleName();

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private CheckBox mRememberMeView;
    private CheckBox mAutoLoginView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @VisibleForTesting
    private ProgressDialog mProgressDialog;

    // Firebase related fields
    private FirebaseAuth mAuth;

    /**
     * Perform initialization of all fragments of current activity.
     *
     * @param savedInstanceState an instance of Bundle instance
     *                           (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Views
        mEmailView = findViewById(R.id.login_form_email);
        mPasswordView = findViewById(R.id.login_form_password);
        mRememberMeView = findViewById(R.id.login_form_rememberMe);
        mAutoLoginView = findViewById(R.id.login_form_autologin);

        if (sharedPreferences.getBoolean(KEY_REMEMBER, false))
            mRememberMeView.setChecked(true);
        else
            mRememberMeView.setChecked(false);

        if (sharedPreferences.getBoolean(KEY_AUTO_LOGIN, false))
            mAutoLoginView.setChecked(true);
        else
            mAutoLoginView.setChecked(false);

        mEmailView.setText(sharedPreferences.getString(KEY_USER_EMAIL, EMPTY_STRING));
        mPasswordView.setText(sharedPreferences.getString(KEY_PASS, EMPTY_STRING));

        mEmailView.addTextChangedListener(this);
        mPasswordView.addTextChangedListener(this);
        mRememberMeView.setOnCheckedChangeListener(this);
        mAutoLoginView.setOnCheckedChangeListener(this);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);

        // auth init
        mAuth = FirebaseAuth.getInstance();

        // db init
        FireBaseDBInitializer.init();
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
     * Updates ui according to authorization result.
     **/
    private void updateUI() {
        hideProgressDialog();
        managePrefs();
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
        if (i == R.id.email_sign_in_button) {
            signIn(mEmailView.getText().toString(), mPasswordView.getText().toString());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    /**
     * Shows progress dialog while backend action is in progress.
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
     * Hides progress dialog from screen.
     **/
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Starts main activity of the application.
     **/
    public void finishSingInActivity() {
        // db init
        FireBaseDBInitializer.init();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Performs Sign In operation.
     *
     * @param email    a String object which will be checked during authorization procedure
     * @param password a String object which will be checked during authorization procedure
     **/
    private void signIn(String email, String password) {
        Log.d(TAG, getString(R.string.log_sign_in) + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, getString(R.string.log_sign_in_with_email_success));
                        updateUI();
                        finishSingInActivity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, getString(R.string.log_sign_in_with_email_failure), task.getException());
                        Toast.makeText(LoginActivity.this, getString(R.string.error_auth_failed),
                                Toast.LENGTH_SHORT).show();
                        updateUI();
                    }
                    hideProgressDialog();
                });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    /**
     * Performs validation procedure before Sign In operation.
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
        return valid;
    }

    private void managePrefs() {
        editor.putString(KEY_OLD_CHANGE_PASS, mPasswordView.getText().toString().trim());
        if (mRememberMeView.isChecked()) {
            editor.putString(KEY_USER_EMAIL, mEmailView.getText().toString().trim());
            editor.putString(KEY_PASS, mPasswordView.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);
            editor.remove(KEY_USER_EMAIL);
        }

        if (mAutoLoginView.isChecked()) {
            editor.putBoolean(KEY_AUTO_LOGIN, true);
        } else {
            editor.putBoolean(KEY_AUTO_LOGIN, false);
        }

        editor.apply();
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

}

