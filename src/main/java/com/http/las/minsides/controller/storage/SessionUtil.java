package com.http.las.minsides.controller.storage;

import java.util.HashMap;

class SessionUtil {
    private static final HashMap<Long, UserSessionInfo> SESSIONS = new HashMap<>();

    static UserSessionInfo getOrPutSession(long chatId) {
        UserSessionInfo session;
        if (!SESSIONS.containsKey(chatId)) {
            session = new UserSessionInfo(chatId);
            SESSIONS.put(chatId, session);
        } else {
            session = SESSIONS.get(chatId);
        }
        return session;
    }

    static UserSessionInfo getSession(Long chatId) {
        UserSessionInfo sessionInfo = getOrPutSession(chatId);
        return sessionInfo;
    }

    static void invalidate(long chatId) {
        SESSIONS.remove(chatId);
    }
}
