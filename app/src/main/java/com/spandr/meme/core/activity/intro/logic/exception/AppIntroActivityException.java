package com.spandr.meme.core.activity.intro.logic.exception;

public class AppIntroActivityException extends RuntimeException{

    public AppIntroActivityException() {
        super();
    }

    public AppIntroActivityException(String message) {
        super(message);
    }

    public AppIntroActivityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppIntroActivityException(Throwable cause) {
        super(cause);
    }

}
