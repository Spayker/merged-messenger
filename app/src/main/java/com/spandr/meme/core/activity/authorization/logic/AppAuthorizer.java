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
import com.spandr.meme.core.activity.authorization.logic.firebase.email.FirebaseEmailAuthorizer;
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

/**
 *`Implements prime logic of authorization ways in system for an user
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/6/2019
 */
public class AppAuthorizer implements ActionAuthorizer {

    private static final String TAG = AppAuthorizer.class.getSimpleName();

    private AppCompatActivity currentActivity;
    private FirebaseEmailAuthorizer firebaseEmailAuthorizer;
    private FireBaseAuthorizerListenerStorage fireBaseAuthorizerListenerStorage;

    private AppAuthorizerListenerStorage appAuthorizerListenerStorage;
    private User user;
    private String login;
    private String password;
    private static boolean isRegisterScenarioRunning = false;

    @SuppressWarnings("unused")
    private AppAuthorizer() { }

    public AppAuthorizer(AppCompatActivity currentActivity, User user) {
        this.currentActivity = currentActivity;
        this.user = user;
        firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();
        Class<LoginActivity> loginActivity = LoginActivity.class;

        fireBaseAuthorizerListenerStorage = new FireBaseAuthorizerListenerStorage(currentActivity,
                loginActivity,
                this);
        appAuthorizerListenerStorage = new AppAuthorizerListenerStorage(currentActivity, firebaseEmailAuthorizer);
        Log.d(TAG, "AppAuthorizer constructor: created object with currentActivity: " + currentActivity +
        " user: " + user);
        isRegisterScenarioRunning = true;
    }

    public AppAuthorizer(AppCompatActivity currentActivity) {
        this.currentActivity = currentActivity;
        this.user = User.getInstance(currentActivity);
        firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();
        Class<LoginActivity> loginActivity = LoginActivity.class;

        fireBaseAuthorizerListenerStorage = new FireBaseAuthorizerListenerStorage(currentActivity,
                loginActivity,
                this);
        appAuthorizerListenerStorage = new AppAuthorizerListenerStorage(currentActivity, firebaseEmailAuthorizer);
        Log.d(TAG, "AppAuthorizer constructor: created object with currentActivity: " + currentActivity);
    }

    @Override
    public void signUp() throws AppFireBaseAuthException {
        Log.d(TAG, "signUp: performing user registration");
        firebaseEmailAuthorizer.createUserWithEmailAndPassword(user.getEmailAddress(), user.getPassword())
                .addOnCompleteListener(fireBaseAuthorizerListenerStorage.getSignUpWithEmailListener());
    }

    @Override
    public void signIn(String login, String password) throws AppFireBaseAuthException {
        this.login = login;
        this.password = password;
        Log.d(TAG, "signIn: performing user authorization");
        firebaseEmailAuthorizer.signInWithEmailAndPasswordAuthorize(
                login,
                password)
                .addOnCompleteListener(fireBaseAuthorizerListenerStorage.getSingInCompleteListener());
    }

    /**
     * Starts main activity of the application.
     **/
    public void finishSingInActivity() {
        boolean isSharedPreferencesNotInitialized = checkAuthPreferences();
        if (!isSharedPreferencesNotInitialized) {
            Log.d(TAG, "finishSingInActivity: user preferences are not initialized yet");
            Log.d(TAG, "finishSingInActivity: performing user initializing...");
            managePrefs(currentActivity, user.getUserName(), login, password);
        }

        Intent intent = new Intent(currentActivity, MainActivity.class);
        if(isRegisterScenarioRunning){
            intent.putExtra(IS_REGISTER_SCENARIO_RUNNING, true);
            isRegisterScenarioRunning = false;
        }

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
        return !Objects.requireNonNull(userName).isEmpty();
    }

    public AppAuthorizerListenerStorage getAppAuthorizerListenerStorage() {
        return appAuthorizerListenerStorage;
    }

    public User getUser() {
        return user;
    }

    public FirebaseEmailAuthorizer getFirebaseEmailAuthorizer() {
        return firebaseEmailAuthorizer;
    }

}
