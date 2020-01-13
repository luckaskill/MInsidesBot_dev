package com.las.bots.minsides.shared.shared.exception;

public class NoteValidatingFailedExceptoion extends UserFriendlyException {
    public NoteValidatingFailedExceptoion() {
        super();
    }

    public NoteValidatingFailedExceptoion(String s) {
        super(s);
    }

    public NoteValidatingFailedExceptoion(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NoteValidatingFailedExceptoion(Throwable throwable) {
        super(throwable);
    }

    public NoteValidatingFailedExceptoion(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
