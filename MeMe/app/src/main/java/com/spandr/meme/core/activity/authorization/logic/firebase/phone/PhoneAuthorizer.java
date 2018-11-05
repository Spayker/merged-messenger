package com.spandr.meme.core.activity.authorization.logic.firebase.phone;

import com.google.firebase.auth.FirebaseAuth;

public class PhoneAuthorizer {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = PhoneAuthorizer.class.getSimpleName();

    private FirebaseAuth mAuth;

    public PhoneAuthorizer() {
        mAuth = FirebaseAuth.getInstance();
    }

}
