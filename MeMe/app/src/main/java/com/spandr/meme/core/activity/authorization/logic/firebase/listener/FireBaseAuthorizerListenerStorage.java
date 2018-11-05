package com.spandr.meme.core.activity.authorization.logic.firebase.listener;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.RegisterActivity;
import com.spandr.meme.core.activity.authorization.logic.firebase.email.EmailAuthorizer;

import java.util.Objects;

public class FireBaseAuthorizerListenerStorage {

    private AppCompatActivity currentActivity;
    private EmailAuthorizer emailAuthorizer;

    private FireBaseAuthorizerListenerStorage() { }

    public FireBaseAuthorizerListenerStorage(AppCompatActivity currentActivity, EmailAuthorizer emailAuthorizer) {
        this.currentActivity = currentActivity;
        this.emailAuthorizer = emailAuthorizer;
    }

    private OnCompleteListener<AuthResult> signUpWithEmailListener = task -> {
        if (task.isSuccessful()) {
            Log.d(task.toString(), currentActivity.getString(R.string.register_log_create_user_with_email_success));
            emailAuthorizer.sendEmailVerification();
        } else {
            ((RegisterActivity) currentActivity).hideProgressDialog();
            Log.w(task.toString(), currentActivity.getString(R.string.register_log_create_user_with_email_failure), task.getException());
            Toast.makeText(currentActivity, currentActivity.getString(R.string.register_error_auth_failed),
                    Toast.LENGTH_SHORT).show();
        }
    };

    private OnCompleteListener<Void> sendEmailVerificationListener = task -> {

    };

    public OnCompleteListener<AuthResult> getSignUpWithEmailListener() {
        return signUpWithEmailListener;
    }

    /*public OnCompleteListener<Void> getSendEmailVerificationListener() {
        return sendEmailVerificationListener;
    }*/
}
