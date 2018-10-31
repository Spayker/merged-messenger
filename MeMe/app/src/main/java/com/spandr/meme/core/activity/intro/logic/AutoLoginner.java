package com.spandr.meme.core.activity.intro.logic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.MainActivity;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

public class AutoLoginner {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = AutoLoginner.class.getSimpleName();

    public void performAutoLogin(AppCompatActivity activity, SharedPreferences sharedPreferences, ProgressBar progressBar, LinearLayout buttonLayer) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && user.isEmailVerified()) {
            String email = sharedPreferences.getString(KEY_USER_EMAIL_OR_PHONE, EMPTY_STRING);
            String password = sharedPreferences.getString(KEY_PASS, EMPTY_STRING);
            if (!email.isEmpty() && !password.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, activity.getString(R.string.login_log_sign_in_with_email_success));
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, activity.getString(R.string.login_log_sign_in_with_email_failure), task.getException());
                                Toast.makeText(activity, activity.getString(R.string.welcome_error_auth_failed),
                                        Toast.LENGTH_SHORT).show();
                                buttonLayer.setVisibility(View.VISIBLE);
                            }
                        });
            }
        }
    }

}
