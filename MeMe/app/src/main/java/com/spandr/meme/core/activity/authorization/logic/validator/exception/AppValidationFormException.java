package com.spandr.meme.core.activity.authorization.logic.validator.exception;

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
