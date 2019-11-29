package com.http.las.minsides.controller.commands.abstractCommands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.tools.ClientBeanService;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class AskAndWait implements Command {
    private MInsidesBot source;
    private String message;
    private Command callback;

    public AskAndWait(String message, Command callback) {
        this.message = message;
        this.callback = callback;
    }

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        source = ClientBeanService.getBean(MInsidesBot.class);
        ChatUtil.sendMsg(message, update, source);
        update.setNextCommand(callback);
    }
}
