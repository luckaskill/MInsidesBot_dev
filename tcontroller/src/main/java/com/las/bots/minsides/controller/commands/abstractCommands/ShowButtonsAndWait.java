package com.las.bots.minsides.controller.commands.abstractCommands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.entity.ButtonKeyboardData;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ButtonUtil;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.controller.tools.ClientBeanService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@AllArgsConstructor
public class ShowButtonsAndWait implements Command {
    private List<ButtonKeyboardData> keyboardData;
    private String message;
    private Command command;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        MInsidesBot source = ClientBeanService.getBean(MInsidesBot.class);
        InlineKeyboardMarkup markup = ButtonUtil.configureKeyboard(keyboardData);
        SendMessage sendMarkup = ChatUtil.createSendMarkup(message, update, markup);
        source.execute(sendMarkup);
        update.setNextCommand(command);
    }
}
