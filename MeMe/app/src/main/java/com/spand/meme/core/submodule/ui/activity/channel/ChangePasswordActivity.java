package com.spand.meme.core.submodule.ui.activity.channel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.spand.meme.R;
import com.spand.meme.core.submodule.ui.activity.ActivityUtils;

/**
 * A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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

        mOldPasswordView = findViewById(R.id.old_password);
        mNewPasswordView = findViewById(R.id.new_password);
        mNewPasswordConfirmView = findViewById(R.id.confirm_password);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
            Log.d(TAG, "new password can not be set");
            return;
        } else {
            editor.putString(KEY_OLD_CHANGE_PASS, mNewPasswordView.getText().toString().trim());
            editor.apply();
        }
    }

    /**
     * Returns true or false if a password is valid or not.
     *
     * @param newPassword a String object which must be validated
     * @return a boolean value. Depends on validation result
     **/
    private boolean isPasswordValid(String newPassword) {
        String actualSavedPassword = sharedPreferences.getString(KEY_OLD_CHANGE_PASS, "");
        String oldPassword = mOldPasswordView.getText().toString().trim();
        String newConfirmPassword = mNewPasswordConfirmView.getText().toString().trim();

        if (oldPassword.isEmpty()) {
            String message = getString(R.string.old_password_empty);
            mOldPasswordView.setError(message);
            Log.i(TAG, message);
            return false;
        }

        if (!actualSavedPassword.equals(oldPassword)) {
            String message = getString(R.string.old_password_is_different);
            Log.i(TAG, message);
            ActivityUtils.invokeOkAlertMessage(this, message);
            return false;
        }

        if (newPassword.isEmpty()) {
            String message = getString(R.string.new_password_empty);
            mNewPasswordView.setError(message);
            Log.i(TAG, message);
            return false;
        }

        if (newConfirmPassword.isEmpty()) {
            String message = getString(R.string.new_confirm_password_empty);
            mNewPasswordConfirmView.setError(message);
            Log.i(TAG, message);
            return false;
        }

        if (!newPassword.equals(newConfirmPassword)) {
            String message = getString(R.string.new_and_confirm_passwords_are_different);
            Log.i(TAG, message);
            ActivityUtils.invokeOkAlertMessage(this, message);
            return false;
        }

        if (oldPassword.equals(newPassword)) {
            String message = getString(R.string.old_and_new_passwords_are_same);
            Log.i(TAG, message);
            ActivityUtils.invokeOkAlertMessage(this, message);
            return false;
        }

        return true;
    }
}
