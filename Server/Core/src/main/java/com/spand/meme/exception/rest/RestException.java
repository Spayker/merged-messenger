package com.spand.meme.exception.rest;

import com.spand.meme.exception.MeMeException;

public class RestException extends MeMeException {

    public RestException() {
        super();
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestException(Throwable cause) {
        super(cause);
    }

}
