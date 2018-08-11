package com.spand.meme.core.submodule.ui.activity.channel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.spand.meme.R;

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

    @VisibleForTesting
    private ProgressDialog mProgressDialog;

    private static final String PREF_NAME = "prefs";
    private static final String KEY_PASS = "password";

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
        showProgressDialog();
        String password = mNewPasswordView.getText().toString().trim();
        if (isPasswordValid(password)) {
            editor.putString(KEY_PASS, mNewPasswordView.getText().toString().trim());
            editor.apply();
        } else {
            Log.d(TAG, "new password can not be set");
        }
        hideProgressDialog();
    }

    /**
     * Returns true or false if a password is valid or not.
     *
     * @param newPassword a String object which must be validated
     * @return a boolean value. Depends on validation result
     **/
    private boolean isPasswordValid(String newPassword) {
        String actualSavedPassword = sharedPreferences.getString(KEY_PASS, "");
        String oldPassword = mOldPasswordView.getText().toString().trim();
        String newConfirmPassword = mNewPasswordConfirmView.getText().toString().trim();

        if (oldPassword.isEmpty()) {
            mOldPasswordView.setError(Resources.getSystem().getString(R.string.old_password_empty));
            Log.i(TAG, "old password is empty");
            return false;
        }

        if (actualSavedPassword.equals(oldPassword)) {
            Log.i(TAG, "old password is set wrong");
            return false;
        }

        if (newPassword.isEmpty()) {
            mNewPasswordView.setError(Resources.getSystem().getString(R.string.old_password_empty));
            Log.i(TAG, "new password is empty");
            return false;
        }

        if (newConfirmPassword.isEmpty()) {
            mNewPasswordConfirmView.setError(Resources.getSystem().getString(R.string.old_password_empty));
            Log.i(TAG, "new confirm password is empty");
            return false;
        }

        if (!newPassword.equals(newConfirmPassword)) {
            Log.i(TAG, "new password and new confirm password are not equal");
            return false;
        }

        if (oldPassword.equals(newPassword)) {
            Log.i(TAG, "old and new password can not be equal");
            return false;
        }

        return true;
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
}
