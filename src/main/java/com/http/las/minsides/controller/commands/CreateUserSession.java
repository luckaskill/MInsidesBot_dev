package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.AskAndWait;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.commands.abstractCommands.CreateSessionCommand;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.controller.tools.ButtonUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class CreateUserSession implements CreateSessionCommand {
    @Autowired
    private CreateUserSessionForSure forSure;
    @Autowired
    private MInsidesBot source;
    @Autowired
    private Start start;
    private Object key;

    @Override
    public void execute(Update update) throws TelegramApiException {
        key = ChatUtil.readUpdate(update);
        InlineKeyboardMarkup yesNoMarkup = ButtonUtil.createYesNoMarkup();
        SendMessage sendMarkup = ChatUtil.createSendMarkup(Messages.ARE_YOU_SURE, update, yesNoMarkup);
        source.execute(sendMarkup);
        SessionUtil.setNextCommand(update, forSure);
    }

    private CreateUserSession getCreateUserSessionCommand() {
        return this;
    }

    @Component
    private class CreateUserSessionForSure implements CreateSessionCommand {
        @Override
        public void execute(Update update) throws TelegramApiException {
            String messageText = ChatUtil.getInput(update);
            if (messageText.equals(Messages.YES)) {
                byte[] byteKey = ChatUtil.createKeyFromObject(key);
                SessionUtil.setKey(update, byteKey);
                SessionUtil.refreshTimeout(update);
                start.execute(update);
            } else if (messageText.equals(Messages.NO)) {
                ChatUtil.sendMsg(Messages.ALRIGHT_THEN, update, source);
                CreateUserSession createUserSession = getCreateUserSessionCommand();
                new AskAndWait(Messages.TRY_AGAIN, createUserSession).execute(update);
            } else {
                ChatUtil.wrongInput();
            }
        }
    }
}
