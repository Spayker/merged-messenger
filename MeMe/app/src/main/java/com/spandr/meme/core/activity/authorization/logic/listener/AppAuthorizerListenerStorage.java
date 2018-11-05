package com.spandr.meme.core.activity.authorization.logic.listener;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.logic.firebase.email.EmailAuthorizer;

public class AppAuthorizerListenerStorage {

    private AppCompatActivity currentActivity;
    private EmailAuthorizer emailAuthorizer;

    private AppAuthorizerListenerStorage() { }

    public AppAuthorizerListenerStorage(AppCompatActivity currentActivity, EmailAuthorizer emailAuthorizer) {
        this.currentActivity = currentActivity;
        this.emailAuthorizer = emailAuthorizer;
    }

    private DialogInterface.OnClickListener sendVerificationEmailListener = (dialog, which) -> {
        emailAuthorizer.sendEmailVerification();
        dialog.dismiss();
        Toast.makeText(currentActivity, currentActivity.getString(R.string.register_check_email),
                Toast.LENGTH_SHORT).show();
    };

    public DialogInterface.OnClickListener getSendVerificationEmailListener() {
        return sendVerificationEmailListener;
    }
}
