package com.spandr.meme.core.activity.authorization.logic.firebase.exception;

public class AppFireBaseAuthException extends RuntimeException {

    public AppFireBaseAuthException() {
        super();
    }

    AppFireBaseAuthException(String message) {
        super(message);
    }

    AppFireBaseAuthException(Throwable cause) {
        super(cause);
    }

}
