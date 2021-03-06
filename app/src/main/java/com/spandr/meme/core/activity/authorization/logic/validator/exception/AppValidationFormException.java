package com.spandr.meme.core.activity.authorization.logic.validator.exception;

/**
 *  Represents app exception that appears during validation of sign in/up form
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/6/2019
 */
public class AppValidationFormException extends RuntimeException{

    AppValidationFormException() {
        super();
    }

    public AppValidationFormException(String message) {
        super(message);
    }

    AppValidationFormException(String message, Throwable cause) {
        super(message, cause);
    }

    AppValidationFormException(Throwable cause) {
        super(cause);
    }

}
