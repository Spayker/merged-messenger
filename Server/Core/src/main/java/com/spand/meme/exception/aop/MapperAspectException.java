package com.spand.meme.exception.aop;

import com.spand.meme.exception.MeMeException;

public class MapperAspectException extends MeMeException {

    public MapperAspectException() {
        super();
    }

    public MapperAspectException(String message) {
        super(message);
    }

    public MapperAspectException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperAspectException(Throwable cause) {
        super(cause);
    }

}
