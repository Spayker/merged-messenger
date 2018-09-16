package com.spand.meme.core.logic.authorization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.spand.meme.R;
import com.spand.meme.core.data.database.FireBaseDBInitializer;
import com.spand.meme.core.ui.activity.main.MainActivity;
import com.spand.meme.core.ui.activity.main.WelcomeActivity;

import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_OLD_CHANGE_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spand.meme.core.logic.starter.SettingsConstants.PREF_NAME;
import static com.spand.meme.core.logic.starter.Starter.REGISTRATOR;
import static com.spand.meme.core.logic.starter.Starter.START_TYPE;
import static com.spand.meme.core.logic.starter.Starter.USERNAME;

abstract class Authorizer {

    // Firebase related fields
    FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    public abstract void registerUser();

    /**
     * Starts main activity of the application.
     **/
    void finishSingUpActivity(Activity currentActivity, String name, String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Toast.makeText(currentActivity, currentActivity.getString(R.string.register_error_auth_passed),
                Toast.LENGTH_SHORT).show();

        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name).build();
            user.updateProfile(profileUpdates);
        }

        // db init
        FireBaseDBInitializer.create().init();

        sharedPreferences = currentActivity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        managePrefs(email, password);

        Intent intent = new Intent(currentActivity, MainActivity.class);
        intent.putExtra(START_TYPE, REGISTRATOR);
        intent.putExtra(USERNAME, name);
        currentActivity.startActivity(intent);
    }

    private void managePrefs(String emailPhone, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_EMAIL_OR_PHONE, emailPhone.trim());
        editor.putString(KEY_OLD_CHANGE_PASS, password.trim());
        editor.putString(KEY_PASS, password.trim());
        editor.apply();
        editor.commit();
    }

}
