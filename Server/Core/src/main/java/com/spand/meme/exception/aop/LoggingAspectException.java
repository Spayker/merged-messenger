package com.spand.meme.exception.aop;

import com.spand.meme.exception.MeMeException;

public class LoggingAspectException extends MeMeException {

    public LoggingAspectException() {
        super();
    }

    public LoggingAspectException(String message) {
        super(message);
    }

    public LoggingAspectException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoggingAspectException(Throwable cause) {
        super(cause);
    }

}
