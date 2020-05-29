package com.spandr.meme.core.activity.authorization.logic.exception;

/**
 *  Represents custom exception that can happen only during authorization process
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/6/2019
 */
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
