package com.spandr.meme.core.activity.authorization.logic.listener;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.logic.firebase.email.FirebaseEmailAuthorizer;

public class AppAuthorizerListenerStorage {

    private AppCompatActivity currentActivity;
    private FirebaseEmailAuthorizer firebaseEmailAuthorizer;

    private AppAuthorizerListenerStorage() { }

    public AppAuthorizerListenerStorage(AppCompatActivity currentActivity, FirebaseEmailAuthorizer firebaseEmailAuthorizer) {
        this.currentActivity = currentActivity;
        this.firebaseEmailAuthorizer = firebaseEmailAuthorizer;
    }

    private DialogInterface.OnClickListener sendVerificationEmailListener = (dialog, which) -> {
        firebaseEmailAuthorizer.sendEmailVerification();
        dialog.dismiss();
        Toast.makeText(currentActivity, currentActivity.getString(R.string.register_check_email),
                Toast.LENGTH_SHORT).show();
    };

    public DialogInterface.OnClickListener getSendVerificationEmailListener() {
        return sendVerificationEmailListener;
    }
}
