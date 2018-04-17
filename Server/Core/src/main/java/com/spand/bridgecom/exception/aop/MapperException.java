package com.spand.bridgecom.exception.aop;

import com.spand.bridgecom.exception.BridgeComException;

public class MapperException extends BridgeComException {

    public MapperException() {
        super();
    }

    public MapperException(String message) {
        super(message);
    }

    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }



}
