package com.http.las.minsides.controller.exception;

import com.http.las.minsides.controller.entity.Messages;

public class WrongInputException extends UserFriendlyException {
    public WrongInputException() {
        super(Messages.WRONG_INPUT);
    }

    public WrongInputException(String s) {
        super(s);
    }
}
