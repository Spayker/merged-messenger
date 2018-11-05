package com.spandr.meme.core.activity.authorization.logic.exception;

public class AppAuthorizationException extends RuntimeException {

    public AppAuthorizationException() {
        super();
    }

    public AppAuthorizationException(String message) {
        super(message);
    }

    public AppAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppAuthorizationException(Throwable cause) {
        super(cause);
    }

}
