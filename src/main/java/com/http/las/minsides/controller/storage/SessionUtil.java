package com.http.las.minsides.controller.storage;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.CreateUserSession;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.commands.abstractCommands.CreateSessionCommand;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.tools.ClientBeanService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

import static com.http.las.minsides.controller.entity.Messages.TIMEOUT;

public class SessionUtil {
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

    private static UserSessionInfo getSession(Update update) {
        long chatId = ChatUtil.getChatId(update);
        UserSessionInfo sessionInfo = getOrPutSession(chatId);
        return sessionInfo;
    }

    private static UserSessionInfo getSession(Long chatId) {
        UserSessionInfo sessionInfo = getOrPutSession(chatId);
        return sessionInfo;
    }

    public static void invalidate(Update update) {
        Long chatId = ChatUtil.getChatId(update);
        SESSIONS.remove(chatId);
    }

    private static void refreshSessionKey(UserSessionInfo session, Update update) throws TelegramApiException {
        Command nextCommand = session.getNextCommand();
        if (nextCommand instanceof CreateSessionCommand) {
            nextCommand.execute(update);
        } else {
            Command command = ClientBeanService.getBean(CreateUserSession.class);
            MInsidesBot source = ClientBeanService.getBean(MInsidesBot.class);
            ChatUtil.sendMsg(TIMEOUT, update, source);
            session.setNextCommand(command);
        }

    }

}
