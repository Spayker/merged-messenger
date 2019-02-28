package com.spandr.meme.core.activity.intro.logic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.spandr.meme.R;
import com.spandr.meme.core.activity.intro.WelcomeActivity;
import com.spandr.meme.core.activity.intro.logic.exception.AppIntroActivityException;
import com.spandr.meme.core.activity.main.MainActivity;

import java.util.Objects;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

public class AutoLoginner {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = AutoLoginner.class.getSimpleName();

    private FirebaseAuth.AuthStateListener authStateListener;
    private static boolean mainActivityIsNotStarted = true;

    public void performAutoLogin(WelcomeActivity activity) {

        if (activity == null){
            throw new AppIntroActivityException("AutoLoginner, performAutoLogin: Activity can not be null");
        }

        validateActivity(activity);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        LinearLayout buttonLayer = activity.getButtonLayer();
        ProgressBar progressBar = activity.getProgressBar();
        progressBar.setVisibility(View.VISIBLE);

            SharedPreferences sharedPreferences = activity.getSharedPreferences();
            String email = sharedPreferences.getString(KEY_USER_EMAIL_OR_PHONE, EMPTY_STRING);
            String password = sharedPreferences.getString(KEY_PASS, EMPTY_STRING);
            if(!Objects.requireNonNull(email).isEmpty() &&
                    !Objects.requireNonNull(password).isEmpty()){
                initAuthStateListener(activity, progressBar, buttonLayer, email, password);
                mAuth.addAuthStateListener(authStateListener);
                performSingIn(mAuth, activity, progressBar, buttonLayer, email, password);
            } else {
                buttonLayer.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
    }

    private void initAuthStateListener(WelcomeActivity activity, ProgressBar progressBar,
                                       LinearLayout buttonLayer, String email, String password) {
        authStateListener = firebaseAuth -> performSingIn(firebaseAuth, activity,
                progressBar, buttonLayer, email, password);
    }

    private void performSingIn(FirebaseAuth mAuth, WelcomeActivity activity, ProgressBar progressBar,
                       LinearLayout buttonLayer, String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, activity.getString(R.string.login_log_sign_in_with_email_success));
                        if(mainActivityIsNotStarted){
                            mAuth.removeAuthStateListener(authStateListener);
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                            mainActivityIsNotStarted = false;
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, activity.getString(R.string.login_log_sign_in_with_email_failure), task.getException());
                        Toast.makeText(activity, activity.getString(R.string.welcome_error_auth_failed),
                                Toast.LENGTH_SHORT).show();
                        buttonLayer.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(activity, task -> {
                    buttonLayer.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                });
    }

    void validateActivity(WelcomeActivity activity) {
        if(activity == null){
            throw new AppIntroActivityException("AutoLoginner, validateActivity: activity is null");
        }

        SharedPreferences sharedPreferences = activity.getSharedPreferences();
        if(sharedPreferences == null){
            throw new AppIntroActivityException("AutoLoginner, validateActivity: sharedPreferences is null");
        }

        LinearLayout buttonLayer = activity.getButtonLayer();
        if(buttonLayer == null){
            throw new AppIntroActivityException("AutoLoginner, validateActivity: buttonLayer is null");
        }

        ProgressBar progressBar = activity.getProgressBar();
        if(progressBar == null){
            throw new AppIntroActivityException("AutoLoginner, isActivityValid: progressBar is null");
        }
    }

}
