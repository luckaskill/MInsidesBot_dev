package com.las.bots.minsides.shared.shared.exception;


import com.las.bots.minsides.shared.shared.Messages;

public class UnknownErrorException extends UserFriendlyException {
    public UnknownErrorException() {
        super(Messages.ERROR_MESSAGE);
    }

    public UnknownErrorException(String s) {
        super(s);
    }
}
