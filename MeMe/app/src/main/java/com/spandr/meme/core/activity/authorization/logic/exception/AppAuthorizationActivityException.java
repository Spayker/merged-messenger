package com.spandr.meme.core.activity.authorization.logic.exception;

public class AppAuthorizationActivityException extends RuntimeException {

    public AppAuthorizationActivityException() {
        super();
    }

    AppAuthorizationActivityException(String message) {
        super(message);
    }

    public AppAuthorizationActivityException(String message, Throwable cause) {
        super(message, cause);
    }

    AppAuthorizationActivityException(Throwable cause) {
        super(cause);
    }

}
