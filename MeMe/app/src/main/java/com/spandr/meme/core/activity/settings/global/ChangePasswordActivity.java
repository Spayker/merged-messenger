package com.spandr.meme.core.activity.settings.global;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.R;
import com.spandr.meme.core.common.util.ActivityUtils;

import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

/**
 * A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();

    private SharedPreferences sharedPreferences;
    private EditText mOldPasswordView;
    private EditText mNewPasswordView;
    private EditText mNewPasswordConfirmView;

    private static final String PREF_NAME = "prefs";
    private static final String KEY_OLD_CHANGE_PASS = "oldChangePassword";

    /**
     * Perform initialization of all fragments of current activity.
     *
     * @param savedInstanceState an instance of Bundle instance
     *                           (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle(R.string.change_password_title);

        mOldPasswordView = findViewById(R.id.old_password);
        mNewPasswordView = findViewById(R.id.new_password);
        mNewPasswordConfirmView = findViewById(R.id.confirm_password);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.save_password_button) {
            updatePassword();
        }
    }

    private void updatePassword() {
        String password = mNewPasswordView.getText().toString().trim();
        if (!isPasswordValid(password)) {
            Log.d(TAG, getString(R.string.change_password_log_new_password_can_not_be_set));
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_OLD_CHANGE_PASS, mNewPasswordView.getText().toString().trim());
            editor.apply();
            editor.commit();
            reauthoriseUser();
        }
    }

    private void reauthoriseUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String newPassword = mNewPasswordView.getText().toString().trim();
            String oldPassword = mOldPasswordView.getText().toString().trim();
            String email = user.getEmail();

            if (email != null) {
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email, oldPassword);

                // Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d(TAG, getString(R.string.change_password_log_password_updated));
                                        ActivityUtils.invokeOkAlertMessage(this, getString(R.string.change_password_updated));
                                        finishChangePasswordActivity();
                                    } else {
                                        Log.d(TAG, getString(R.string.change_password_log_error_password_not_updated));
                                    }
                                });
                            } else {
                                Log.d(TAG, getString(R.string.change_password_log_error_auth_failed));
                            }
                        });
            } else {
                Log.d(TAG, getString(R.string.change_password_log_email_is_null));
            }
        } else {
            Log.d(TAG, getString(R.string.change_password_log_user_is_null));
        }
    }

    /**
     * Returns true or false if a password is valid or not.
     *
     * @param newPassword a String object which must be validated
     * @return a boolean value. Depends on validation result
     **/
    private boolean isPasswordValid(String newPassword) {
        String actualSavedPassword = sharedPreferences.getString(KEY_OLD_CHANGE_PASS, EMPTY_STRING);
        String oldPassword = mOldPasswordView.getText().toString().trim();
        String newConfirmPassword = mNewPasswordConfirmView.getText().toString().trim();

        if (oldPassword.isEmpty()) {
            String message = getString(R.string.change_password_old_password_empty);
            mOldPasswordView.setError(message);
            return false;
        }

        if (!actualSavedPassword.equals(oldPassword)) {
            String message = getString(R.string.change_password_old_password_is_different);
            ActivityUtils.invokeOkAlertMessage(this, message);
            return false;
        }

        if (newPassword.isEmpty()) {
            String message = getString(R.string.change_password_new_password_empty);
            mNewPasswordView.setError(message);
            return false;
        }

        if (newConfirmPassword.isEmpty()) {
            String message = getString(R.string.change_password_new_confirm_password_empty);
            mNewPasswordConfirmView.setError(message);
            return false;
        }

        if (!newPassword.equals(newConfirmPassword)) {
            String message = getString(R.string.change_password_new_and_confirm_passwords_are_different);
            ActivityUtils.invokeOkAlertMessage(this, message);
            return false;
        }

        if (oldPassword.equals(newPassword)) {
            String message = getString(R.string.change_password_old_and_new_passwords_are_same);
            ActivityUtils.invokeOkAlertMessage(this, message);
            return false;
        }
        Log.d(TAG, getString(R.string.change_password_log_password_has_been_validated));
        return true;
    }

    /**
     * Return to settings activity of the application.
     **/
    public void finishChangePasswordActivity() {
        Intent intent = new Intent(this, GlobalSettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, GlobalSettingsActivity.class));
        finish();
    }
}
