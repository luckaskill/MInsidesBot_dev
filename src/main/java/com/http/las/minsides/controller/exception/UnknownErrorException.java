package com.http.las.minsides.controller.exception;

import com.http.las.minsides.controller.entity.Messages;

public class UnknownErrorException extends UserFriendlyException {
    public UnknownErrorException() {
        super(Messages.ERROR_MESSAGE);
    }

    public UnknownErrorException(String s) {
        super(s);
    }
}
