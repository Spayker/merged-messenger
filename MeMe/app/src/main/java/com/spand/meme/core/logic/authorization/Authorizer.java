package com.spand.meme.core.logic.authorization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.spand.meme.core.data.database.FireBaseDBInitializer;
import com.spand.meme.core.ui.activity.main.MainActivity;

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
    FirebaseFirestore firestoreDB;
    String userName;
    private SharedPreferences sharedPreferences;

    public abstract void registerUser();

    /**
     * Starts main activity of the application.
     **/
    void finishSingUpActivity(Activity currentActivity, String name, String emailPhone, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name).build();
            user.updateProfile(profileUpdates);
        }

        // db init
        FireBaseDBInitializer.create().init();

        sharedPreferences = currentActivity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        managePrefs(emailPhone, password);

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
