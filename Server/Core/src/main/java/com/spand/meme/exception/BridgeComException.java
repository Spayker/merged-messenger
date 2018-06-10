package com.spand.meme.exception;

public class BridgeComException extends RuntimeException {

    public BridgeComException() {
        super();
    }

    public BridgeComException(String message) {
        super(message);
    }

    public BridgeComException(String message, Throwable cause) {
        super(message, cause);
    }

    public BridgeComException(Throwable cause) {
        super(cause);
    }

}
