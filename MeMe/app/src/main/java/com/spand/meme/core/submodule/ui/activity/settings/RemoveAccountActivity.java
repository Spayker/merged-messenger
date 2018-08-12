package com.spand.meme.core.submodule.ui.activity.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spand.meme.R;
import com.spand.meme.core.submodule.ui.activity.ActivityUtils;

import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_OLD_CHANGE_PASS;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_PASS;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_REMEMBER;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_USERNAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.PREF_NAME;

/**
 * A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class RemoveAccountActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = ResetSettingsActivity.class.getSimpleName();

    private EditText mPasswordView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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
        editor = sharedPreferences.edit();
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
        Log.d(TAG, getString(R.string.log_remove_account) + mAuth.getCurrentUser().getEmail());
        if (!validateForm()) {
            return;
        }
        FirebaseUser user = mAuth.getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), currentPassword);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(task -> user.delete()
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Log.d(TAG, getString(R.string.log_remove_account_successful));
                                dropPrefs();
                            }
                        }));
    }

    /**
     * Performs validation procedure before Sign In operation.
     **/
    private boolean validateForm() {

        String actualSavedPassword = sharedPreferences.getString(KEY_OLD_CHANGE_PASS, "");
        String password = mPasswordView.getText().toString();

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.field_required));
            return false;
        }

        if (actualSavedPassword.equals(password)) {
            return true;
        } else {
            String message = getString(R.string.old_password_is_different);
            ActivityUtils.invokeOkAlertMessage(this, message);
        }

        return false;
    }

    private void dropPrefs() {
        editor.putString(KEY_OLD_CHANGE_PASS, null);
        editor.putString(KEY_USERNAME, null);
        editor.putString(KEY_PASS, null);
        editor.putBoolean(KEY_REMEMBER, false);
        editor.apply();
    }
}