package com.spandr.meme.core.activity.authorization.logic.firebase.exception;

/**
 *  Defines app exception that happens only during auth process with Firebase infrastructure
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/6/2019
 */
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
