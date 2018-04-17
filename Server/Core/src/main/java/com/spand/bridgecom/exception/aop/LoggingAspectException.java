package com.spand.bridgecom.exception.aop;

import com.spand.bridgecom.exception.BridgeComException;

public class LoggingAspectException extends BridgeComException {

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
