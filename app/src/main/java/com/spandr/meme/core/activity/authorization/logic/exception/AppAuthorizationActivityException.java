package com.spandr.meme.core.activity.authorization.logic.exception;

public class AppAuthorizationActivityException extends RuntimeException {

    public AppAuthorizationActivityException() {
        super();
    }

    public AppAuthorizationActivityException(String message) {
        super(message);
    }

    public AppAuthorizationActivityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppAuthorizationActivityException(Throwable cause) {
        super(cause);
    }

}
