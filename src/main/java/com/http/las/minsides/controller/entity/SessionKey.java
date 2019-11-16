package com.http.las.minsides.controller.entity;

public class SessionKey {
    private Byte[] sessionKey;
    private long startTime;
    private long timeOut;

    public SessionKey(Byte[] sessionKey) {
        this.sessionKey = sessionKey;
        refreshStartTime();
        //default timeout - 3 hours
        timeOut = 10_800_000L;
    }

    public boolean timeIsOut() {
        boolean out = startTime - System.currentTimeMillis() > timeOut;
        return out;
    }

    public void refreshStartTime() {
        startTime = System.currentTimeMillis();
    }
}
