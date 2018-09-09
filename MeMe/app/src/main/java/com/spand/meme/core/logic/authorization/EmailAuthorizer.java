package com.spand.meme.core.logic.authorization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.spand.meme.R;
import com.spand.meme.core.ui.activity.main.LoginActivity;
import com.spand.meme.core.ui.activity.main.MainActivity;

public class EmailAuthorizer extends Authorizer {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = EmailAuthorizer.class.getSimpleName();

    private Activity currentActivity;
    private String userName;
    private String phone;
    private String emailAddress;
    private String password;

    @SuppressLint("StaticFieldLeak")
    private static EmailAuthorizer instance;

    private EmailAuthorizer(Activity currentActivity, String name, String email, String phone, String password) {
        this.currentActivity = currentActivity;
        this.userName = name;
        this.phone = phone;
        this.emailAddress = email;
        this.password = password;
        userName = name;

        // auth init
        mAuth = FirebaseAuth.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();
    }

    public static EmailAuthorizer init(Activity activity, String name, String email, String phoneNumber, String password) {
        if (instance == null) {
            instance = new EmailAuthorizer(activity, name, email, phoneNumber, password);
        }
        return instance;
    }

    @Override
    public void registerUser() {
        mAuth.createUserWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(currentActivity, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, currentActivity.getString(R.string.register_log_create_user_with_email_success));
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, currentActivity.getString(R.string.register_log_create_user_with_email_failure), task.getException());
                        Toast.makeText(currentActivity, currentActivity.getString(R.string.register_error_auth_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void authorize() {
        mAuth.signInWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(currentActivity, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, currentActivity.getString(R.string.login_log_sign_in_with_email_success));
                        ((LoginActivity)currentActivity).updateUI();
                        finishSingInActivity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, currentActivity.getString(R.string.login_log_sign_in_with_email_failure), task.getException());
                        Toast.makeText(currentActivity, currentActivity.getString(R.string.login_error_auth_failed),
                                Toast.LENGTH_SHORT).show();
                        ((LoginActivity)currentActivity).updateUI();
                    }
                    ((LoginActivity)currentActivity).hideProgressDialog();
                });
    }


    /**
     * Starts main activity of the application.
     **/
    private void finishSingInActivity() {
        Intent intent = new Intent(currentActivity, MainActivity.class);
        currentActivity.startActivity(intent);
    }

}
