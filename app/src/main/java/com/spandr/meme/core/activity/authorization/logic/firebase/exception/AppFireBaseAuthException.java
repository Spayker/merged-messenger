package com.spandr.meme.core.activity.authorization.logic.firebase.exception;

public class AppFireBaseAuthException extends RuntimeException {

    public AppFireBaseAuthException() {
        super();
    }

    public AppFireBaseAuthException(String message) {
        super(message);
    }

    public AppFireBaseAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppFireBaseAuthException(Throwable cause) {
        super(cause);
    }

}
