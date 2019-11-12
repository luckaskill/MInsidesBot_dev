package com.http.las.minsides.controller.exception;

public class NoteValidatingFailed extends UserFriendlyException {
    public NoteValidatingFailed() {
        super();
    }

    public NoteValidatingFailed(String s) {
        super(s);
    }

    public NoteValidatingFailed(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NoteValidatingFailed(Throwable throwable) {
        super(throwable);
    }

    public NoteValidatingFailed(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
