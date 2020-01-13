package com.las.bots.minsides.shared.shared.exception;


import com.las.bots.minsides.shared.shared.Messages;

public class WrongInputException extends UserFriendlyException {
    public WrongInputException() {
        super(Messages.WRONG_INPUT);
    }

    public WrongInputException(String s) {
        super(s);
    }
}
