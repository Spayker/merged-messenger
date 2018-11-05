package com.spandr.meme.core.activity.authorization.logic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.core.activity.authorization.LoginActivity;
import com.spandr.meme.core.activity.authorization.logic.data.User;
import com.spandr.meme.core.activity.authorization.logic.firebase.email.EmailAuthorizer;
import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;
import com.spandr.meme.core.activity.authorization.logic.firebase.listener.FireBaseAuthorizerListenerStorage;
import com.spandr.meme.core.activity.authorization.logic.listener.AppAuthorizerListenerStorage;
import com.spandr.meme.core.activity.main.MainActivity;
import com.spandr.meme.core.common.util.ActivityUtils;

import java.util.Objects;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_OLD_CHANGE_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_NAME;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

public class AppAuthorizer implements ActionAuthorizer {

    private static final String TAG = AppAuthorizer.class.getSimpleName();

    private AppCompatActivity currentActivity;
    private EmailAuthorizer emailAuthorizer;
    private FireBaseAuthorizerListenerStorage fireBaseAuthorizerListenerStorage;

    private AppAuthorizerListenerStorage appAuthorizerListenerStorage;
    private User user;

    private AppAuthorizer() { }

    public AppAuthorizer(AppCompatActivity currentActivity, User user) {
        this.currentActivity = currentActivity;
        this.user = user;
        emailAuthorizer = new EmailAuthorizer();
        Class<LoginActivity> loginActivity = LoginActivity.class;

        fireBaseAuthorizerListenerStorage = new FireBaseAuthorizerListenerStorage(currentActivity,
                loginActivity,
                this);
        appAuthorizerListenerStorage = new AppAuthorizerListenerStorage(currentActivity, emailAuthorizer);
        Log.d(TAG, "AppAuthorizer constructor: created object with currentActivity: " + currentActivity +
        " user: " + user);
    }

    public AppAuthorizer(AppCompatActivity currentActivity) {
        this.currentActivity = currentActivity;
        this.user = User.getInstance(currentActivity);
        emailAuthorizer = new EmailAuthorizer();
        Class<LoginActivity> loginActivity = LoginActivity.class;

        fireBaseAuthorizerListenerStorage = new FireBaseAuthorizerListenerStorage(currentActivity,
                loginActivity,
                this);
        appAuthorizerListenerStorage = new AppAuthorizerListenerStorage(currentActivity, emailAuthorizer);
        Log.d(TAG, "AppAuthorizer constructor: created object with currentActivity: " + currentActivity);
    }

    @Override
    public void signUp() throws AppFireBaseAuthException {
        Log.d(TAG, "signUp: performing user registration");
        emailAuthorizer.createUserWithEmailAndPassword(user.getEmailAddress(), user.getPassword())
                .addOnCompleteListener(fireBaseAuthorizerListenerStorage.getSignUpWithEmailListener());
    }

    @Override
    public void signIn() throws AppFireBaseAuthException {
        Log.d(TAG, "signIn: performing user authorization");
        emailAuthorizer.signInWithEmailAndPasswordhorize(
                user.getEmailAddress(),
                user.getPassword())
                .addOnCompleteListener(fireBaseAuthorizerListenerStorage.getSingInCompleteListener());
    }

    @Override
    public void logout() {
        Log.d(TAG, "logout: performing user logout");
        emailAuthorizer.signOut();
    }

    @Override
    public void sendVerification() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Objects.requireNonNull(mAuth.getCurrentUser()).reload();
        FirebaseUser user = mAuth.getCurrentUser();
        emailAuthorizer.sendEmailVerification();
        if (user.isEmailVerified()) {
            Log.d(TAG, "sendVerification: user is verified. Finishing process...");
            finishSingInActivity();
        } else {
            Log.d(TAG, "sendVerification: user is not verified. Invoking dialog to proceed...");
            AlertDialog.Builder builder = ActivityUtils.createVerificationDialogBox(currentActivity, appAuthorizerListenerStorage);
            builder.show();
        }
    }

    /**
     * Starts main activity of the application.
     **/
    public void finishSingInActivity() {
        boolean isSharedPreferencesNotInitialized = checkAuthPreferences();
        if (!isSharedPreferencesNotInitialized) {
            Log.d(TAG, "finishSingInActivity: user preferences are not initialized yet");
            Log.d(TAG, "finishSingInActivity: performing user initializing...");
            managePrefs(currentActivity, user.getUserName(), user.getEmailAddress(), user.getPassword());
        }
        Intent intent = new Intent(currentActivity, MainActivity.class);
        currentActivity.startActivity(intent);
    }

    private void managePrefs(AppCompatActivity activity, String userName, String emailPhone, String password) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_EMAIL_OR_PHONE, emailPhone.trim());
        editor.putString(KEY_OLD_CHANGE_PASS, password.trim());
        editor.putString(KEY_PASS, password.trim());
        editor.apply();
        editor.commit();
    }

    private boolean checkAuthPreferences() {
        SharedPreferences sharedPreferences = currentActivity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString(KEY_USER_NAME, EMPTY_STRING);
        return !userName.isEmpty();
    }

    public AppAuthorizerListenerStorage getAppAuthorizerListenerStorage() {
        return appAuthorizerListenerStorage;
    }

    public User getUser() {
        return user;
    }

    public EmailAuthorizer getEmailAuthorizer() {
        return emailAuthorizer;
    }

}
