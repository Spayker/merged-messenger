package com.spandr.meme.core.activity.authorization.logic.firebase.email;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;

import java.util.Objects;

public class EmailAuthorizer {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = EmailAuthorizer.class.getSimpleName();

    private FirebaseAuth mAuth;

    public EmailAuthorizer() {
        mAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> createUserWithEmailAndPassword(String emailAddress, String password) throws AppFireBaseAuthException {

        if(emailAddress == null || emailAddress.isEmpty()){
            Log.e(TAG, "createUserWithEmailAndPassword: email is null or empty");
            throw new AppFireBaseAuthException();
        }

        if(password == null || password.isEmpty()){
            Log.e(TAG, "createUserWithEmailAndPassword: password is null or empty");
            throw new AppFireBaseAuthException();
        }

        return mAuth.createUserWithEmailAndPassword(emailAddress, password);
    }

    public Task<Void> sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            Log.e(TAG, "sendEmailVerification: Fire base user is null. Sending is cancelled. returning null as result");
            return null;
        }

        return user.sendEmailVerification();
    }

    public Task<AuthResult> signInWithEmailAndPasswordhorize(String emailAddress, String password) throws AppFireBaseAuthException {

        if(emailAddress == null || emailAddress.isEmpty()){
            Log.d(TAG, "signInWithEmailAndPasswordhorize: emailAddress is null or empty");
            throw new AppFireBaseAuthException();
        }

        if(password == null || password.isEmpty()){
            Log.d(TAG, "signInWithEmailAndPasswordhorize: password is null or empty");
            throw new AppFireBaseAuthException();
        }

        return mAuth.signInWithEmailAndPassword(emailAddress, password);
    }

    public void signOut(){
        mAuth.signOut();
    }

}