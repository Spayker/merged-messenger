package com.spandr.meme.core.activity.settings.global;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.R;
import com.spandr.meme.core.common.util.ActivityUtils;
import com.spandr.meme.core.activity.intro.WelcomeActivity;

import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_OLD_CHANGE_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_REMEMBER;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;

/**
 * A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class RemoveAccountActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = RemoveAccountActivity.class.getSimpleName();

    private EditText mPasswordView;

    private SharedPreferences sharedPreferences;

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
        setContentView(R.layout.activity_remove_account);

        mPasswordView = findViewById(R.id.confirm_password);

        // Buttons
        findViewById(R.id.confirm_remove_account_button).setOnClickListener(this);

        // auth init
        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.confirm_remove_account_button) {
            removeAccount(mPasswordView.getText().toString());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    public void removeAccount(String currentPassword) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Log.d(TAG, getString(R.string.remove_account_log_remove_account) + currentUser.getEmail());
            if (!validateForm()) {
                return;
            }

            AuthCredential credential = EmailAuthProvider
                    .getCredential(currentUser.getEmail(), currentPassword);

            // Prompt the user to re-provide their sign-in credentials
            currentUser.reauthenticate(credential)
                    .addOnCompleteListener(task -> currentUser.delete()
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Log.d(TAG, getString(R.string.remove_account_log_remove_account_successful));
                                    finishRemoveAccountActivity();
                                }
                            }));
        } else {
            Log.d(TAG, getString(R.string.remove_account_user_is_null));
        }
    }

    /**
     * Performs validation procedure before Sign In operation.
     **/
    private boolean validateForm() {

        String actualSavedPassword = sharedPreferences.getString(KEY_OLD_CHANGE_PASS, EMPTY_STRING);
        String password = mPasswordView.getText().toString();

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.remove_account_field_required));
            return false;
        }

        if (actualSavedPassword.equals(password)) {
            return true;
        } else {
            String message = getString(R.string.remove_account_old_password_is_different);
            ActivityUtils.invokeOkAlertMessage(this, message);
        }

        return false;
    }

    /**
     *  Return to settings activity of the application.
     **/
    public void finishRemoveAccountActivity() {
        dropPrefs();
        ActivityUtils.invokeOkAlertMessage(this, getString(R.string.remove_account_finished));
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    private void dropPrefs() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_OLD_CHANGE_PASS, null);
        editor.putString(KEY_USER_EMAIL_OR_PHONE, null);
        editor.putString(KEY_PASS, null);
        editor.putBoolean(KEY_REMEMBER, false);
        editor.apply();
        editor.commit();
    }
}
