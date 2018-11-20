package com.spandr.meme.core.activity.authorization.logic.firebase.listener;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.LoginActivity;
import com.spandr.meme.core.activity.authorization.RegisterActivity;
import com.spandr.meme.core.activity.authorization.logic.AppAuthorizer;
import com.spandr.meme.core.activity.authorization.logic.firebase.email.FirebaseEmailAuthorizer;
import com.spandr.meme.core.common.util.ActivityUtils;

import java.util.Objects;

public class FireBaseAuthorizerListenerStorage {

    private AppCompatActivity currentActivity;
    private Class<?> redirectActivity;
    private FirebaseEmailAuthorizer firebaseEmailAuthorizer;
    private FirebaseAuth mAuth;
    private AppAuthorizer appAuthorizer;

    private FireBaseAuthorizerListenerStorage() { }

    public FireBaseAuthorizerListenerStorage(AppCompatActivity currentActivity,
                                             Class<?> redirectActivity,
                                             AppAuthorizer appAuthorizer) {
        this.currentActivity = currentActivity;
        this.redirectActivity = redirectActivity;
        this.appAuthorizer = appAuthorizer;
        this.firebaseEmailAuthorizer = appAuthorizer.getFirebaseEmailAuthorizer();
        mAuth = FirebaseAuth.getInstance();
    }

    private OnCompleteListener<AuthResult> signUpWithEmailListener = task -> {
        if (task.isSuccessful()) {
            Log.d(task.toString(), currentActivity.getString(R.string.register_log_create_user_with_email_success));
            FirebaseUser user = mAuth.getCurrentUser();
            firebaseEmailAuthorizer.sendEmailVerification(user);
            Intent intent = new Intent(currentActivity, redirectActivity);
            currentActivity.startActivity(intent);
        } else {
            ((RegisterActivity) currentActivity).hideProgressDialog();
            Log.w(task.toString(), currentActivity.getString(R.string.register_log_create_user_with_email_failure), task.getException());
            Toast.makeText(currentActivity, currentActivity.getString(R.string.register_error_auth_failed),
                    Toast.LENGTH_SHORT).show();
        }
    };

    private OnCompleteListener<AuthResult> singInCompleteListener = task -> {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(task.toString(), currentActivity.getString(R.string.login_log_sign_in_with_email_success));
            Objects.requireNonNull(mAuth.getCurrentUser()).reload();
            FirebaseUser user = mAuth.getCurrentUser();
            if (user.isEmailVerified()) {
                appAuthorizer.finishSingInActivity();
            } else {
                AlertDialog.Builder builder = ActivityUtils.
                        createVerificationDialogBox(currentActivity,
                                appAuthorizer.getAppAuthorizerListenerStorage());
                builder.show();
            }
        } else {
            // If sign in fails, display a message to the user.
            Log.w(task.toString(), currentActivity.getString(R.string.login_log_sign_in_with_email_failure), task.getException());
            Toast.makeText(currentActivity, currentActivity.getString(R.string.login_error_auth_failed),
                    Toast.LENGTH_SHORT).show();
        }
        ((LoginActivity) currentActivity).hideProgressDialog();
    };

    public OnCompleteListener<AuthResult> getSignUpWithEmailListener() {
        return signUpWithEmailListener;
    }

    public OnCompleteListener<AuthResult> getSingInCompleteListener() {
        return singInCompleteListener;
    }
}
