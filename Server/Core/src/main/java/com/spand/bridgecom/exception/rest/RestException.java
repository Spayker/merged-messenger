package com.spand.bridgecom.exception.rest;

import com.spand.bridgecom.exception.BridgeComException;

public class RestException extends BridgeComException {

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
