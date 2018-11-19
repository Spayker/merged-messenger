package com.spandr.meme.core.activity.authorization.logic.firebase.email;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;

public class FirebaseEmailAuthorizer {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = FirebaseEmailAuthorizer.class.getSimpleName();

    private FirebaseAuth mAuth;

    public FirebaseEmailAuthorizer() {
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

    public Task<AuthResult> signInWithEmailAndPasswordAuthorize(String emailAddress, String password) throws AppFireBaseAuthException {

        if(emailAddress == null || emailAddress.isEmpty()){
            Log.d(TAG, "signInWithEmailAndPasswordAuthorize: emailAddress is null or empty");
            throw new AppFireBaseAuthException();
        }

        if(password == null || password.isEmpty()){
            Log.d(TAG, "signInWithEmailAndPasswordAuthorize: password is null or empty");
            throw new AppFireBaseAuthException();
        }

        return mAuth.signInWithEmailAndPassword(emailAddress, password);
    }

    public void signOut(){
        mAuth.signOut();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

}