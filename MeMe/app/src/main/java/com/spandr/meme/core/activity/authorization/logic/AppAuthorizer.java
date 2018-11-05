package com.spandr.meme.core.activity.authorization.logic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.LoginActivity;
import com.spandr.meme.core.activity.authorization.logic.data.User;
import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;
import com.spandr.meme.core.activity.authorization.logic.firebase.email.EmailAuthorizer;
import com.spandr.meme.core.activity.authorization.logic.firebase.listener.FireBaseAuthorizerListenerStorage;
import com.spandr.meme.core.activity.authorization.logic.listener.AppAuthorizerListenerStorage;
import com.spandr.meme.core.activity.main.MainActivity;

import java.util.Objects;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_OLD_CHANGE_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_NAME;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;

public class AppAuthorizer implements ActionAuthorizer {

    private static final String TAG = AppAuthorizer.class.getSimpleName();

    private AppCompatActivity currentActivity;
    private EmailAuthorizer emailAuthorizer;
    private FireBaseAuthorizerListenerStorage fireBaseAuthorizerListenerStorage;
    private AppAuthorizerListenerStorage appAuthorizerListenerStorage;
    private FirebaseAuth mAuth;
    private User user;

    private AppAuthorizer() { }

    public AppAuthorizer(AppCompatActivity currentActivity, User user) {
        this.currentActivity = currentActivity;
        this.user = user;
        emailAuthorizer = new EmailAuthorizer();
        mAuth = FirebaseAuth.getInstance();
        fireBaseAuthorizerListenerStorage = new FireBaseAuthorizerListenerStorage(currentActivity, emailAuthorizer);
        appAuthorizerListenerStorage = new AppAuthorizerListenerStorage(currentActivity, emailAuthorizer);
    }

    @Override
    public void signUp() throws AppFireBaseAuthException {
        emailAuthorizer.createUserWithEmailAndPassword(user.getEmailAddress(), user.getPassword())
                .addOnCompleteListener(fireBaseAuthorizerListenerStorage.getSignUpWithEmailListener());
    }

    @Override
    public void signIn() throws AppFireBaseAuthException {
        Task<AuthResult> task = emailAuthorizer.signInWithEmailAndPasswordhorize(user.getEmailAddress(), user.getPassword());

        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, currentActivity.getString(R.string.login_log_sign_in_with_email_success));
            Objects.requireNonNull(mAuth.getCurrentUser()).reload();
            FirebaseUser user = mAuth.getCurrentUser();
            if(user.isEmailVerified()) {
                finishSingInActivity();
            } else {
                AlertDialog.Builder builder = createVerificationDialogBox();
                builder.show();
            }
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, currentActivity.getString(R.string.login_log_sign_in_with_email_failure), task.getException());
            Toast.makeText(currentActivity, currentActivity.getString(R.string.login_error_auth_failed),
                    Toast.LENGTH_SHORT).show();
        }
        ((LoginActivity) currentActivity).hideProgressDialog();
    }

    @Override
    public void logout() {
        emailAuthorizer.signOut();
    }

    @Override
    public void sendVerification() {
        Objects.requireNonNull(mAuth.getCurrentUser()).reload();
        FirebaseUser user = mAuth.getCurrentUser();
        emailAuthorizer.sendEmailVerification();
        if (user.isEmailVerified()) {
            finishSingInActivity();
        } else {
            AlertDialog.Builder builder = createVerificationDialogBox();
            builder.show();
        }
    }

    private AlertDialog.Builder createVerificationDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        builder.setTitle(currentActivity.getResources().getString(R.string.login_info_email_not_verified));
        builder.setPositiveButton(currentActivity.getResources().getString(R.string.main_menu_yes),
                appAuthorizerListenerStorage.getSendVerificationEmailListener());

        builder.setNegativeButton(currentActivity.getResources().getString(R.string.main_menu_no),
                (dialog, which) -> dialog.dismiss());
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.logo);
        return builder;
    }

    /**
     * Starts main activity of the application.
     **/
    private void finishSingInActivity() {
        boolean isSharedPreferencesNotInitialized = checkAuthPreferences();
        if (isSharedPreferencesNotInitialized) {
            managePrefs(currentActivity, user.getUserName(), user.getEmailAddress(), user.getPassword());
        }
        Intent intent = new Intent(currentActivity, MainActivity.class);
        currentActivity.startActivity(intent);
    }

    private boolean checkAuthPreferences() {
        return false;
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

    public User getUser() {
        return user;
    }

}
