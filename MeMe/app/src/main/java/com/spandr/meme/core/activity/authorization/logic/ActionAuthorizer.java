package com.spandr.meme.core.activity.authorization.logic;

import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;

interface ActionAuthorizer {

    void signUp() throws AppFireBaseAuthException;

    void signIn() throws AppFireBaseAuthException;

    void logout();

    void sendVerification();

}
