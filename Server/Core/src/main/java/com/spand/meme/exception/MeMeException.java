package com.spand.meme.exception;

public class MeMeException extends RuntimeException {

    public MeMeException() {
        super();
    }

    public MeMeException(String message) {
        super(message);
    }

    public MeMeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MeMeException(Throwable cause) {
        super(cause);
    }

}
