package com.http.las.minsides.controller.exception;

public class UserFriendlyException extends RuntimeException {

    public UserFriendlyException() {
    }

    public UserFriendlyException(String s) {
        super(s);
    }

    public UserFriendlyException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UserFriendlyException(Throwable throwable) {
        super(throwable);
    }

    public UserFriendlyException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
