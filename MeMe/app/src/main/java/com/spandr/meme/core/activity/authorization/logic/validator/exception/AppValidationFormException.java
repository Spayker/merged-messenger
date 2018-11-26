package com.spandr.meme.core.activity.authorization.logic.validator.exception;

public class AppValidationFormException extends RuntimeException{

    public AppValidationFormException() {
        super();
    }

    public AppValidationFormException(String message) {
        super(message);
    }

    public AppValidationFormException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppValidationFormException(Throwable cause) {
        super(cause);
    }

}
