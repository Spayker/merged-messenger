package com.spandr.meme.core.logic.authorization;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.R;
import com.spandr.meme.core.ui.activity.main.LoginActivity;
import com.spandr.meme.core.ui.activity.main.MainActivity;
import com.spandr.meme.core.ui.activity.main.RegisterActivity;

import java.util.Objects;

public class EmailAuthorizer extends Authorizer {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = EmailAuthorizer.class.getSimpleName();

    private Activity currentActivity;
    private String userName;
    private String emailAddress;
    private String password;

    private EmailAuthorizer(Activity currentActivity, String name, String email, String password) {
        this.currentActivity = currentActivity;
        this.userName = name;
        this.emailAddress = email;
        this.password = password;

        // auth init
        mAuth = FirebaseAuth.getInstance();
    }

    public static EmailAuthorizer init(Activity activity, String name, String email, String password) {
        return new EmailAuthorizer(activity, name, email, password);
    }

    @Override
    public void registerUser() {
        mAuth.createUserWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(currentActivity, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, currentActivity.getString(R.string.register_log_create_user_with_email_success));
                        sendEmailVerification();
                    } else {
                        // If sign in fails, display a message to the user.
                        ((RegisterActivity)currentActivity).hideProgressDialog();
                        Log.w(TAG, currentActivity.getString(R.string.register_log_create_user_with_email_failure), task.getException());
                        Toast.makeText(currentActivity, currentActivity.getString(R.string.register_error_auth_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Intent intent = new Intent(currentActivity, LoginActivity.class);
                            currentActivity.startActivity(intent);

                            Toast.makeText(currentActivity, currentActivity.getString(R.string.register_check_email),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void authorize() {
        mAuth.signInWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(currentActivity, task -> {
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
                    ((LoginActivity)currentActivity).hideProgressDialog();
                });
    }

    private AlertDialog.Builder createVerificationDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        builder.setTitle(currentActivity.getResources().getString(R.string.login_info_email_not_verified));
        builder.setPositiveButton(currentActivity.getResources().getString(R.string.main_menu_yes),
                (dialog, which) -> {
                    sendEmailVerification();
                    dialog.dismiss();
                    Toast.makeText(currentActivity, currentActivity.getString(R.string.register_check_email),
                            Toast.LENGTH_SHORT).show();
                });

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
        Intent intent = new Intent(currentActivity, MainActivity.class);
        currentActivity.startActivity(intent);
    }

}
