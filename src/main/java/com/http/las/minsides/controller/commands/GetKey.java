package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.Command;
import com.http.las.minsides.controller.tools.ChatUtil;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class GetKey implements Command {
    @Override
    public void execute(Update update) throws TelegramApiException {
        Long chatId = ChatUtil.getChatId(update);
        //put key to session and call Start
    }
}
