package com.spandr.meme.core.activity.webview.logic.exception;

public class AppWebViewException extends RuntimeException{

    public AppWebViewException() {
        super();
    }

    public AppWebViewException(String message) {
        super(message);
    }

    public AppWebViewException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppWebViewException(Throwable cause) {
        super(cause);
    }


}
