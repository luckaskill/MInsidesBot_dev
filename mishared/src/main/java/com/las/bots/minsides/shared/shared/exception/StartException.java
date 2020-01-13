package com.las.bots.minsides.shared.shared.exception;

public class StartException extends Exception {
    public StartException() {
    }

    public StartException(String s) {
        super(s);
    }

    public StartException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public StartException(Throwable throwable) {
        super(throwable);
    }
}
