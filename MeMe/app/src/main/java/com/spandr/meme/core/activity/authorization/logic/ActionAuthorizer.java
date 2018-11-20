package com.spandr.meme.core.activity.authorization.logic;

import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;

interface ActionAuthorizer {

    void signUp() throws AppFireBaseAuthException;

    void signIn(String login, String password) throws AppFireBaseAuthException;

    void logout();

    void sendVerification();

}
